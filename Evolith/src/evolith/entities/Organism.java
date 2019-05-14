/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.engine.Assets;
import evolith.game.Game;
import evolith.game.Item;
import evolith.helpers.Commons;
import evolith.helpers.SwarmMovement;
import evolith.helpers.Time;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author charles
 */
public class Organism extends Item implements Commons {

    private Point point;    //Point where the organism will try to move
    private int maxVel;     //Maximum speed the organism can reach at some point
    private int absMaxVel;  //Maximum speed the organism can reach at all points
    private int acc;        //Rate at which speed increases
    private int xVel;       //Speed in the x axis
    private int yVel;       //Speed in the y axis
    private Game game;      //Game object to access other objecs

    private Time time;      //Time tracking class to simulate life
    
    private int id;         //Unique identifier

    /**
     * These are the five evolutionary traits
     */
    private int speed;
    private int strength;
    private int stealth;
    private int maxHealth;
    private int intelligence;

    private double life;           //Health points of the organism
    private int hunger;         //hunger of the organism
    private int thirst;         //thirst of the organism
    private int maturity;       //maturity level of the organsim
    private int generation;     //generation level of the organsim
    private int skin;           //Skin asset selected

    private int prevHungerRed;  //Time in seconds at which hunger was previously reduced
    private int prevThirstRed;  //Time in seconds at which hunger was previously reduced
    private int prevMatInc;     //Time in seconds at which maturity was previously increased
    private int prevPointGenerated;
    private int prevIntelResInc; //Time in seconds at which intelligence was previously increased

    private boolean needOffspring;  //Value indicating if the organisms needs to reproduce
    private boolean dead;           //Whether the organism is dead or not
    private boolean beingChased;    //Value wether it is being chased or not
    private String name;            //Name of the organism

    private Resource target;        //Its current resource target

    private boolean searchFood;     //Value if it is actively looking for food
    private boolean searchWater;    //Value if it is actively lokking for water
    private boolean aggressive;     //value if it will actively fight when a predator attacks

    private boolean eating;         //If it is currently eating
    private boolean drinking;       //If it is currently drinking

    private MutationManager orgMutations;  //Manager of the mutations of this organism

    private boolean selected;       //If the organism is currently selected by the player
    private boolean godCommand;     //If the organism has received an explicit movement command from the player
    
    private double damage;          //Amount of damage the organism deals to predators
    private int stealthRange;       //Range at which the organism can be detected
    
    private int currentMaxHealth;   //Max health of the organism
    private int currentSize;        //Size of the organism
    private int hatchTime;          //Time to hatch
    
    private boolean egg;            //State if organism is an egg
    private boolean born;           //State if organism is born
    private boolean needMutation;   //State if organism needs to be mutated
    private boolean other;          //State if organism belongs to opponent in multiplayer
    private boolean visible;        //State if the organism is rederable in multiplayer
    
    private int alfa;               //Transparency value
    private boolean animationDone;  //Check if animation is done
    
    private boolean inCamp;
    private Item pred;              //Predator chasing the organism

    /**
     * Constructor of the organism
     *
     * @param x starting x value
     * @param y starting y value
     * @param width current width
     * @param height current height
     * @param game the game object where the organism resides
     * @param skin the id of the selected skin
     * @param id the unique identifier
     * @param other state if the organism belongs to the opponent in mutliplayer
     */
    public Organism(int x, int y, int width, int height, Game game, int skin, int id, boolean other) {
        super(x, y, width, height);
        this.game = game;
        this.skin = skin;
        this.id = id;

        this.other = other;

        point = new Point(x, y);
        maxVel = 2;
        absMaxVel = 2;
        xVel = 0;
        yVel = 0;
        acc = 1;
        
        //Initialize stats
        speed = 0;
        strength = 0;
        stealth = 0;
        maxHealth = 0;
        intelligence = 0;
                
        updateStats(false);
        
        hunger = 50;
        thirst = 50;

        maturity = 0;
        generation = 1;
        prevHungerRed = 0;
        prevThirstRed = 0;
        prevMatInc = 0;
        prevPointGenerated = 0;
        prevIntelResInc = 0;

        needOffspring = false;
        dead = false;

        searchFood = false;
        searchWater = false;
        aggressive = false;

        eating = false;
        drinking = false;
        selected = false;
        godCommand = false;
        inCamp = false;
        
        damage = 0.05;

        time = new Time();
        name = "";

        orgMutations = new MutationManager(this, game);
        
        egg = true;
        born = false;
        needMutation = false;
        visible = !other;

        pred = null;
        target = null;
        
        hatchTime = 0;
        
        alfa = 0;
        animationDone = false;
    }
    
        
    /**
     * To tick the organism
     */
    @Override
    public void tick() {
        //to determine the lifespan of the organism
        time.tick();
        
        //If dead, play animation
        if (dead) {
            alfa -= 1;
            if (alfa <= 0) {
                animationDone = true;
            }

            return;
        }
        
        //If egg, check vitals and do nothing
        if (egg) {
            checkVitals();
            return;
        }
        
        //If other, check movement and do nothing
        if (other) {
            checkMovement();
            return;
        }
        
        //If not other, check predators and others
        if (!other) {
            checkPredators();   
            if (game.getOtherOrganisms() != null) {
                checkOthers();
            }
        }
        
        checkCamps();
        handleTarget();
        checkMovement();
        checkVitals(); 
        
        if (beingChased) {
            escapeOrFight();
        } else {
            checkTargetStatus();
            checkArrivalOnTarget();
        }
        
        
        if (!beingChased && !isConsuming() && target == null) {
            autoLookTarget();
        }
    }

    /**
     * Update the position of the organism according to the point
     */
    private void checkMovement() {
        if (point.x > BACKGROUND_WIDTH) {
            point.x = BACKGROUND_WIDTH - 20;
        }
        
        if (point.y > BACKGROUND_HEIGHT) {
            point.y = BACKGROUND_HEIGHT - 20;
        }
        
        if (point.x < 0) {
            point.x = 20;
        }
        
        if (point.y < 0) {
            point.y = 20;
        }
        // if the organism is less than 25 units reduce velocity
        if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 25) {
            // if the organism is less than 15 units reduce velocity
            if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 15) {
                // if the organism is less than 5 units reduce velocity
                if (Math.abs((int) point.getX() - x) < 5 && Math.abs((int) point.getY() - y) < 5) {
                    maxVel = 0;
                    if (godCommand) {
                        godCommand = false;
                    }
                } else {
                    maxVel = 1;
                }
            } else {
                maxVel = absMaxVel / 2;
            }
        } else {
            maxVel = absMaxVel;
        }

        //move in the x to the point
        if ((int) point.getX() > x) {
            xVel += acc;
        } else {
            xVel -= acc;
        }
        //move in the y to the point
        if ((int) point.getY() > y) {
            yVel += acc;
        } else {
            yVel -= acc;
        }
        //limits the x velocity
        if (xVel > maxVel) {
            xVel = maxVel;
        }

        if (xVel < maxVel * -1) {
            xVel = maxVel * -1;
        }
        //limits the y velocity
        if (yVel > maxVel) {
            yVel = maxVel;
        }

        if (yVel < maxVel * -1) {
            yVel = maxVel * -1;
        }
        //increments the velocity
        x += xVel;
        y += yVel;
    }

    /**
     * To check the update and react to the vital stats of the organism
     */
    private void checkVitals() {
        //Iif egg, check hatch time and life
        if (egg) {
            hatchTime = (int) time.getSeconds();
            if (time.getSeconds() >= BORN_TIME && !born) {
                if (!other) {
                    born();
                }
                
                hatchTime = BORN_TIME;
            }
            
            if (other) {
                return;
            }
            
            if (life <= 0) {
                kill();
            }
            
            return;
        }
        
        //Reduce hunger every x seconds
        if (time.getSeconds() >= prevHungerRed + SECONDS_PER_HUNGER && !eating) {
            hunger--;
            prevHungerRed = (int) time.getSeconds();
        }

        //Reduce thirst every x seconds defined in the commmons class
        if (time.getSeconds() >= prevThirstRed + SECONDS_PER_THIRST && !drinking) {
            thirst--;
            prevThirstRed = (int) time.getSeconds();
        }
        
        //If vitals are over 90, increase intelligence accordingly to reward the player
        if ((hunger >= 90 || thirst >= 90) && time.getSeconds() >= prevIntelResInc + SECONDS_PER_FULL_RES_INTEL) {
            if (hunger >= 90) {
                intelligence++;
            }
            
            if (thirst >= 90) {
                intelligence++;
            }
            /*
            if (hunger >= 90 && thirst >= 90) {
                intelligence++;
            }
            */
            prevIntelResInc = (int) time.getSeconds();
        }
        
        //If hunger is less than 0, decrease life
        if (hunger <= 0) {
            hunger = 0;
            life -= 0.05;
        }
        
        //If hunger is less than 0, decrease life
        if (thirst <= 0) {
            thirst = 0;
            life -= 0.05;
        }
        /**
         * Limit hunger and thirst to 100
         */
        if (hunger > 100) {
            hunger = 100;
        }
        
        if (thirst > 100) {
            thirst = 100;
        }

        //Increase maturity every x seconds defined in the commmons class
        if (time.getSeconds() >= prevMatInc + SECONDS_PER_MATURITY) {
            maturity++;
            prevMatInc = (int) time.getSeconds();

            //Reproduction happen at these two points in maturity
            if (maturity == 110) {
                needOffspring = true;
            }

            if (maturity == 140) {
                needOffspring = true;
            }
        }

        //Once the organisms reaches max maturity, kill it
        /*if (maturity >= MAX_MATURITY) {
            kill();
        }*/
        
        //If life reaches 0, kill the organism
     /*   if (life <= 0) {
            kill();
        }*/
    }
    
    /**
     * Decide whether to fight or flight in presence of an enemy
     */
    private void escapeOrFight() {
        //If not aggresive, then escape
        if (!aggressive) {
            //Escape

            //If god command is active, organisms shouldn't generate a new point
            if (!godCommand) {
                point = generateEscapePointPred();
            }
        //Else move to the enemy position
        } else {
            if (!godCommand) {
                int randX = SwarmMovement.generateRandomness(100);
                int randY = SwarmMovement.generateRandomness(100);
                point = new Point(pred.getX() + 30 + randX, pred.getY() + 30 + randY);
            }
        }
    }
    

    private void checkCamps() {
        Campfire camp;
        for (int i = 0; i < game.getCampfires().getAmount(); i++) {
            camp = game.getCampfires().getCamp(i);
            
            if (camp.containsRectInRad(getPerimeter())) {
                inCamp = true;
                return;
            }
        }
        
        inCamp = false;
    }
    

    /**
     * Process for the organism to be born
     */
    private void born() {        
        born = true;
        //Check if a mutation will occur. Chance is defined in the commons class
        if (((int) (Math.random() * MUTATION_CHANCE) == 0) && !other && !game.getOrganisms().getMutPanel().isActive()) {
            needMutation = true;
        } else {
            egg = false;
            life = currentMaxHealth;
        }
    }
    
    /**
     * Update movement according to its current target
     */
    public void handleTarget() {
        //If no target, do nothing
        if (target == null) {
            return;
        }
        
        if (!isConsuming()) {
            point.x = target.getX();
            point.y = target.getY();
        }
    }

    /**
     * Kill the organism and leave the current resource
     */
    public void kill() {
        dead = true;
        safeLeaveResource();
        
        if (target != null && target.hasParasite(this)) {
            target.removeParasite(this);
        }
    }
    
    /**
     * Update its stats and vitals with its current mutations
     * @param trait trait index
     * @param newTier tier index
     */
    public void updateMutation(int trait, int newTier){
        int currStrength = strength + getOrgMutations().getMutations().get(trait).get(newTier).getStrength();
        int currSpeed = speed + getOrgMutations().getMutations().get(trait).get(newTier).getSpeed();
        int currMaxHealth = maxHealth + getOrgMutations().getMutations().get(trait).get(newTier).getMaxHealth();
        int currStealth = stealth + getOrgMutations().getMutations().get(trait).get(newTier).getStealth();
        
        setStrength(currStrength > 0 ? currStrength : 0);
        setSpeed(currSpeed > 0 ? currSpeed : 0);
        setMaxHealth(currMaxHealth > 0 ? currMaxHealth : 0);
        setStealth(currStealth > 0 ? currStealth : 0);
        
        updateStats(false);
    }
    
    /**
     * update stats values with current mutations
     * @param other state if organism is opponent
     */
    public void updateMutations(boolean other) {
        strength = 0;
        speed = 0;
        maxHealth = 0;
        stealth = 0;

        for (int i = 0; i < orgMutations.getMutations().size(); i++) {
            int newStr = 0;
            int newSpeed = 0;
            int newHealth = 0;
            int newStealth = 0;
            
            for (int j = 0; j < orgMutations.getMutations().get(i).size(); j++) {
                newStr += orgMutations.getMutations().get(i).get(j).getStrength();
                newSpeed += orgMutations.getMutations().get(i).get(j).getSpeed();
                newHealth += orgMutations.getMutations().get(i).get(j).getMaxHealth();
                newStealth += orgMutations.getMutations().get(i).get(j).getStealth();
                
                if (orgMutations.getMutations().get(i).get(j).isActive()) {
                    strength += newStr;
                    speed += newSpeed;
                    maxHealth += newHealth;
                    stealth += newStealth;
                    break;
                }
            }
        }
        strength = strength > 0 ? strength : 0;
        speed = speed > 0 ? speed : 0;
        maxHealth = maxHealth > 0 ? maxHealth : 0;
        stealth = stealth > 0 ? stealth : 0;;
        updateStats(other);
    }
    
    /**
     * Take current stats defined by mutations and transform them to useful numbers 
     * @param other 
     */
    private void updateStats(boolean other) {
        //Transform stat numbers to useful numbers
        currentMaxHealth = maxHealth * 2 + 100;
        
        if (!other) {
            life = currentMaxHealth;
        }
        
        if (maxHealth >= 70) {
            currentSize = (int) ((maxHealth-40) * 0.5 + 30);
            width = currentSize;
            height = currentSize;
        } else {
            currentSize = (int) ((maxHealth) * 0.5 + 30);
            width = currentSize;
            height = currentSize;
        }
        
        stealthRange = MAX_SIGHT_DISTANCE - (stealth) * 9;
        
        damage = strength * (0.05/20.0) + 0.05;
        
        absMaxVel = (int) ((double) speed * (1.0/20.0)) + 1;
    }
    
    /**
     * Create a copy of the organism
     * @return new organism
     */
    public Organism cloneOrg(){
        Organism org = new Organism(x,y,width, height, game, skin, id, other);
        org.setPoint((Point) point.clone());
        org.setMaxVel(maxVel);
        org.setSpeed(speed);
        org.setStealth(stealth);
        org.setStrength(strength);
        org.setMaxHealth(maxHealth);
        org.setIntelligence(intelligence);
        org.setLife(maxHealth*2+60);
        org.setGeneration(generation+1);
        
        for(int i=0; i<4; i++){
            for(int j=0; j<orgMutations.getMutations().get(i).size(); j++){
                if(orgMutations.getMutations().get(i).get(j).isActive()){
                    org.getOrgMutations().getMutations().get(i).get(j).setActive(true);
                }
            }
        }
        
        org.updateStats(false);
        
        return org;
    }
    
    
    /**
     * leave a resource safely, meaning, remove the organism parasite form the
     * resource and set the target to null
     *
     */
    public void safeLeaveResource() {
        if (target != null) {
            if (target.hasParasite(this)) {
                target.removeParasite(this);
            }
            
            eating = false;
            drinking = false;
            target = null;
        }
    }
    
    /**
    * Check for predators nearby and act accordingly
    */
    private void checkPredators() {
        PredatorManager predators = game.getPredators();
        
        beingChased = false;
        
        //Check for every predator
        for (int j = 0; j < predators.getPredatorAmount(); j++) {
            Predator p = predators.getPredator(j);
            

            //If predator is in the range of the organism, run or fight
            if (SwarmMovement.distanceBetweenTwoPoints(x, y, p.getX(), p.getY()) < MAX_SIGHT_DISTANCE) {
                safeLeaveResource();
                beingChased = true;
                pred = p;
            }
        }
    }
    
    /**
    * Check for opponents nearby and act accordingly
    */
    private void checkOthers() {
        OrganismManager others = game.getOtherOrganisms();
        
        for (int i = 0; i < others.getAmount(); i++) {
            Organism o = others.getOrganism(i);

            //If opponent is in the range of the organism
            if (SwarmMovement.distanceBetweenTwoPoints(x, y, o.getX(), o.getY()) < o.getStealthRange()) {
                safeLeaveResource();
                beingChased = true;
                pred = o;
                
                if (o.intersects(this) && !o.isEgg()) {
                    life -= o.getDamage();
                }
            }
        }
    }
    
     /**
     * Generate a point to run when an organism is being chased by a predator or opponent
     *
     * @return the generated point
     */
    public Point generateEscapePointPred() {

        Point generatedPoint = new Point(x, y);
        
        if (SwarmMovement.distanceBetweenTwoPoints(x, y, pred.getX(), pred.getY()) < 20) {
            generatedPoint = new Point(findNearestOrganism().getX(), findNearestOrganism().getY());
        }
        
        //Create a point in the contrary direction of the enemy
        generatedPoint.x = x + (x - pred.getX()) + SwarmMovement.generateRandomness(100);
        generatedPoint.y = y + (y - pred.getY()) + SwarmMovement.generateRandomness(100);
        
        /**
         * Limit the point to the map size
         */
        if (generatedPoint.x <= 0) {
            generatedPoint.x = 100;
        }

        if (generatedPoint.x >= BACKGROUND_WIDTH) {
            generatedPoint.x = BACKGROUND_WIDTH - 100;
        }

        if (generatedPoint.y <= 0) {
            generatedPoint.y = 100;
        }

        if (generatedPoint.y >= BACKGROUND_HEIGHT) {
            generatedPoint.y = BACKGROUND_HEIGHT - 100;
        }

        return generatedPoint;
    }
    
     /**
     * Checks if the target resource for each organism is still valid (has qty
     * and is not full) if not, leave and look for another target resource
     */
    public void checkTargetStatus() {
        //Check if target exists
        if (target != null) {
            //Check if the current target is already full and target does not have organism
            if ((target.isFull() && !target.hasParasite(this)) || target.isOver()) {
                //System.out.println("HEHE CHANGE RESOURCE");

                safeLeaveResource();
                eating = false;
                drinking = false;
            }
        //If organism is full of that resource, leave it
        } else if (target != null && (target.getType() == Resource.ResourceType.Plant && hunger == 100) 
                && target.getType() == Resource.ResourceType.Water && thirst == 100){
            safeLeaveResource();
            eating = false;
            drinking = false;
        //Else, look for something
        } else {
            eating = false;
            drinking = false;
        }
    }
    
     /**
     * Check if the organism has arrived to resource, if so, assign it to it
     */
    public void checkArrivalOnTarget() {
        if (target != null) {
            if (target.intersects(this)) {
                if (!target.isFull()) {
                    if (!target.hasParasite(this)) {
                        target.addParasite(this);
                        //Check the resource type
                        if (target.getType() == Resource.ResourceType.Plant) {
                            eating = true;
                            drinking = false;
                        } else {
                            drinking = true;
                            eating = false;
                        }
                    } else {
                        //System.out.println("ORG ALREADY IN TARGET");
                    }
                } else {
                   if (!target.hasParasite(this)) {
                        autoLookTarget();
                   }
                }
            }
        }
    }
    
     /**
     * Look for a new resource according to what the organism is looking for
     */
    public void autoLookTarget() {
        Resource plant = findNearestValidFood();
        Resource water = findNearestValidWater();
        Organism friend = findNearestOrganism();
        if (searchFood && searchWater) {
            //Find closest of both
            //System.out.println("FINDING BOTH");

            if (hunger > 90 && thirst > 90) {
                goWithAFriend(friend);
                return;
            }

            if (hunger > 90) {
                target = water;
                return;
            }

            if (thirst > 90) {
                target = plant;
                return;
            }

            double distPlant = Math.sqrt(Math.pow(x - plant.getX(), 2)
                    + Math.pow(y- plant.getY(), 2));
            double distWater = Math.sqrt(Math.pow(x- water.getX(), 2)
                    + Math.pow(y - water.getY(), 2));

            if (distPlant < distWater) {
                target = plant;
            } else {
                target = water;
            }
        } else if (searchFood) {
            //System.out.println("FINDING FOOD ONLY");
            if (hunger > 90) {
                goWithAFriend(friend);
                return;
            }

            target = plant;
        } else if (searchWater) {
            //System.out.println("FINDING WATER ONLY");
            if (thirst > 90) {
                goWithAFriend(friend);
                return;
            }
            target = water;
        } else {
            target = null;
        }
    }
    
    /**
     * Go to a nearby organism
     * @param friend organism friend
     */
    private void goWithAFriend(Organism friend) {
        int randX = SwarmMovement.generateRandomness(120);
        int randY = SwarmMovement.generateRandomness(120);
        safeLeaveResource();
        point = new Point(friend.getX() + randX, friend.getY() + randY);
    }
    
     /**
     * Finds the nearest valid (not empty and not full) source of food
     *
     * @return the closest food
     */
    public Resource findNearestValidFood() {
        Resource closestPlant = null;
        double closestDistanceBetweenPlantAndOrganism = 1000000;

        for (int i = 0; i < game.getResources().getPlantAmount(); i++) {
            double distanceBetweenPlantAndOrganism = 7072;
            if (!game.getResources().getPlant(i).isFull() && !game.getResources().getPlant(i).isOver()) {
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(x - game.getResources().getPlant(i).getX(), 2)
                        + Math.pow(y - game.getResources().getPlant(i).getY(), 2));
            }

            if (distanceBetweenPlantAndOrganism < closestDistanceBetweenPlantAndOrganism) {
                closestDistanceBetweenPlantAndOrganism = distanceBetweenPlantAndOrganism;
                closestPlant = game.getResources().getPlant(i);
            }
        }

        return closestPlant;
    }
    
     /**
     * Finds the nearest valid (not empty and not full) source of water
     *
     * @return the closest water
     */
    public Resource findNearestValidWater() {
        Resource closestWater = null;
        double closestDistanceBetweenWaterAndOrganism = 1000000;

        for (int i = 0; i < game.getResources().getWaterAmount(); i++) {
            double distanceBetweenPlantAndOrganism = 7072;
            if (!game.getResources().getWater(i).isFull() && !game.getResources().getWater(i).isOver()) {
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(x - game.getResources().getWater(i).getX(), 2)
                        + Math.pow(y - game.getResources().getWater(i).getY(), 2));
            }

            if (distanceBetweenPlantAndOrganism < closestDistanceBetweenWaterAndOrganism) {
                closestDistanceBetweenWaterAndOrganism = distanceBetweenPlantAndOrganism;
                closestWater = game.getResources().getWater(i);
            }
        }

        return closestWater;
    }
    
    /**
     * Find the nearest organism to this organism
     * @return 
     */
    public Organism findNearestOrganism(){
        Organism closestOrganism = null; 
        double closestDistanceBetweenPredatorAndOrganism = 1000000;

        //Organism(int x, int y, int width, int height, Game game, int skin, int id)
        for(int i = 0; i < game.getOrganisms().getOrganismsAmount(); i++){
            double distanceBetweenPredatorAndOrganism = 7072;

                distanceBetweenPredatorAndOrganism = Math.sqrt(Math.pow(x-game.getOrganisms().getOrganism(i).getX(),2)
                        + Math.pow(y-game.getOrganisms().getOrganism(i).getY(),2) );

            
            if(distanceBetweenPredatorAndOrganism<closestDistanceBetweenPredatorAndOrganism){
                closestDistanceBetweenPredatorAndOrganism = distanceBetweenPredatorAndOrganism;
                closestOrganism = game.getOrganisms().getOrganism(i);
            }
        }
        
        return closestOrganism;
    }
    
    /**
     * Save the organism information into the print writer
     * @param pw print writer
     */
    public void save(PrintWriter pw) {
        //Save id
        pw.println(Integer.toString(id));
        pw.println(name);
        
        //Save position and speed
        pw.println(Integer.toString(x));
        pw.println(Integer.toString(y));
        pw.println(Integer.toString(xVel));
        pw.println(Integer.toString(yVel));
        
        //save point
        pw.println(Integer.toString(point.x));
        pw.println(Integer.toString(point.y));

        //Save vitals
        pw.println(Integer.toString((int) life));
        pw.println(Integer.toString((int) hunger));
        pw.println(Integer.toString((int) thirst));
        pw.println(Integer.toString(maturity));
        pw.println(Integer.toString(intelligence));

        //Save Activities
        pw.println(Integer.toString(searchFood ? 1 : 0));
        pw.println(Integer.toString(searchWater ? 1 : 0));
        pw.println(Integer.toString(aggressive ? 1 : 0));
        
        //Egg state
        pw.println(Integer.toString(egg ? 1 : 0));

        //Save generation and time
        pw.println(Integer.toString(generation));
        pw.println(Long.toString(time.getTicker()));
        
        //Save mutations
        orgMutations.save(pw);
    }
    
    /**
     * Load the organism to the information in the save file
     * @param br buffered reader
     * @throws IOException 
     */
    public void load(BufferedReader br) throws IOException {
        id = Integer.parseInt(br.readLine());
        name = br.readLine();
        
        x = Integer.parseInt(br.readLine());
        y = Integer.parseInt(br.readLine());
        
        xVel = Integer.parseInt(br.readLine());
        yVel = Integer.parseInt(br.readLine());
        
        int px = Integer.parseInt(br.readLine());
        int py = Integer.parseInt(br.readLine());
        
        point = new Point(px, py);
        
        life = Integer.parseInt(br.readLine());
        hunger = Integer.parseInt(br.readLine());
        thirst = Integer.parseInt(br.readLine());
        maturity = Integer.parseInt(br.readLine());
        intelligence = Integer.parseInt(br.readLine());
        
        searchFood = Integer.parseInt(br.readLine()) == 1;
        searchWater = Integer.parseInt(br.readLine()) == 1;
        aggressive = Integer.parseInt(br.readLine()) == 1;
        
        egg = Integer.parseInt(br.readLine()) == 1;
        
        generation = Integer.parseInt(br.readLine());
        time.setTicker(Long.parseLong(br.readLine()));
        
        orgMutations.load(br);
        
        updateMutations(false);
        updateStats(false);
    }

    /**
     * Renders the organisms relative to the camera
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        double barOffX = 0.05;
        double barOffY = 1.1;
        
        if (!visible) {
            return;
        }
        
        if (egg) {
            g.drawImage(Assets.egg, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width - 10, height - 10, null);
            
            g.setColor(Color.RED);
            g.fillRect(game.getCamera().getRelX(x) + (int) ((currentSize - 10) * barOffX) ,
                    game.getCamera().getRelY(y) + (int) ((currentSize - 10) * barOffY)+1, (int) ((currentSize - 10) * this.life / currentMaxHealth), 3);
            
            g.setColor(Color.white);
            g.drawRect(game.getCamera().getRelX(x) + (int) ((currentSize - 10) * barOffX) -1,
                    game.getCamera().getRelY(y) + (int) ((currentSize - 10) * barOffY), (currentSize - 10), 4);
            
            g.setColor(Color.YELLOW);
            g.fillRect(game.getCamera().getRelX(x) + (int) ((currentSize - 10) * barOffX) ,
                    game.getCamera().getRelY(y) + (int) ((currentSize - 10) * barOffY) + 5, (int) ((currentSize - 10) * hatchTime / BORN_TIME), 3);
            
            g.setColor(Color.white);
            g.drawRect(game.getCamera().getRelX(x) + (int) ((currentSize - 10) * barOffX) -1,
                    game.getCamera().getRelY(y) + (int) ((currentSize - 10) * barOffY) + 4, (currentSize - 10), 4);
            
        } else {
            if (dead) {
                //g.drawImage(Assets.setAlpha((byte) alfa, Assets.orgColors.get(skin)), game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
            }
            g.drawImage(Assets.orgColors.get(skin), game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);

            //Warning that the organism can reproduce
            if(isNeedOffspring()){
                g.setColor(Color.BLACK);
                g.drawImage(Assets.repClock, game.getCamera().getRelX(getX() - width / 2), game.getCamera().getRelY(getY() - width / 2) - 5, 15, 17, null);
                //g.fillOval(game.getCamera().getRelX(getX() - width / 2), game.getCamera().getRelY(getY() - width / 2), currentSize / 2, currentSize / 2);
            }

            orgMutations.render(g);

            g.setColor(Color.RED);
            g.fillRect(game.getCamera().getRelX(x) + (int) (currentSize * barOffX) ,
                    game.getCamera().getRelY(y) + (int) (currentSize * barOffY), (int) (currentSize * this.life / currentMaxHealth), 3);

            g.setColor(Color.white);
            g.drawRect(game.getCamera().getRelX(x) + (int) (currentSize * barOffX) -1,
                    game.getCamera().getRelY(y) + (int) (currentSize * barOffY), currentSize, 4);

            if (selected) {
                 g.drawImage(Assets.glow, game.getCamera().getRelX(x) - 6, game.getCamera().getRelY(y) - 6, width + 12, height + 12, null);
            }
            /*
            if (beingChased) {
                //g.drawString(Integer.toString((int) point.getX())  + "-" + point.getY(), game.getCamera().getRelX(x), game.getCamera().getRelY(y));
                g.setColor(Color.RED);
                g.fillOval(game.getCamera().getRelX(getX() - width / 2 + 50), game.getCamera().getRelY(getY() - width / 2 + 50), currentSize / 2, currentSize / 2);
            }*/
        }
    }
    
    /**
     * ======================================
     *          GETTERS AND SETTERS
     * ======================================
     */
    
    /**
     * to get the name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * to set the name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * to get the speed
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * to get the strength
     * @return strength
     */
    public int getStrength() {
        return strength;
    }
    
    /**
     * to get the stealth
     * @return stealth
     */
    public int getStealth() {
        return stealth;
    }
    
    /**
     * to get the max health
     * @return max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    
    /**
     * to get intelligence
     * @return 
     */
    public int getIntelligence() {
        return intelligence;
    }
    
    /**
     * to get the life
     * @return life
     */
    public double getLife() {
        return life;
    }

    /**
     * to set life
     * @param life life
     */
    public void setLife(double life){
        this.life = life;
    }
    
    /**
     * to get hunger
     * @return hunger
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * to get thirst
     * @return thirst
     */
    public int getThirst() {
        return thirst;
    }

    /**
     * to get maturity
     * @return maturity
     */
    public int getMaturity() {
        return maturity;
    }

    /**
     * to get time
     * @return time
     */
    public Time getTime() {
        return time;
    }

    /**
     * to set time
     * @param time
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * to get generation
     * @return generation
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * to set generation
     * @param generation current generation
     */
    public void setGeneration(int generation) {
        this.generation = generation;
    }
    
    /**
     * to set speed
     * @param speed current speed
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    /**
     * to set stealth
     * @param stealth current stealth
     */
    public void setStealth(int stealth){
        this.stealth = stealth;
    }

    /**
     * to set strength
     * @param strength current strength
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * to set maxHealth
     * @param maxHealth current max health
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    /**
     * to set maturity
     * @param maturity current maturity
     */
    public void setMaturity(int maturity) {
        this.maturity = maturity;
    }
    
    /**
     * to set intelligence
     * @param intelligence current intelligence
     */
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    /**
     * to set life
     * @param life current life
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * To get the point
     *
     * @return point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * To set the point
     *
     * @param point point
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * To get needOffspring
     *
     * @return needOffspring
     */
    public boolean isNeedOffspring() {
        return needOffspring;
    }

    /**
     * To set needOffspring
     *
     * @param needOffspring needOffspring state
     */
    public void setNeedOffspring(boolean needOffspring) {
        this.needOffspring = needOffspring;
    }

    /**
     * To check if the organism is dead
     *
     * @return dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * To set dead
     *
     * @param dead dead state
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * to get target
     * @return target
     */
    public Resource getTarget() {
        return target;
    }

    /**
     * to set target
     * @param target new target
     */
    public void setTarget(Resource target) {
        this.target = target;
    }

    /**
     * to get searchFood
     * @return searchFood
     */
    public boolean isSearchFood() {
        return searchFood;
    }

    /**
     * to get searchWater
     * @return searchWater
     */
    public boolean isSearchWater() {
        return searchWater;
    }

    /**
     * to get aggressive
     * @return aggressive
     */
    public boolean isAggressive() {
        return aggressive;
    }

    /**
     * to set searchFood
     * @param searchFood searchFood state
     */
    public void setSearchFood(boolean searchFood) {
        this.searchFood = searchFood;
    }

    /**
     * to set searchWater
     * @param searchWater searchWater state
     */
    public void setSearchWater(boolean searchWater) {
        this.searchWater = searchWater;
    }

    /**
     * to set aggressive
     * @param aggressive aggressive state
     */
    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }
    
    /**
     * to get eating
     * @return eating
     */
    public boolean isEating() {
        return eating;
    }

    /**
     * to get drinking
     * @return drinking
     */
    public boolean isDrinking() {
        return drinking;
    }

    /**
     * to set eating
     * @param eating eating
     */
    public void setEating(boolean eating) {
        this.eating = eating;
    }

    /**
     * to seat drinking
     * @param drinking drinking
     */
    public void setDrinking(boolean drinking) {
        this.drinking = drinking;
    }

    /**
     * to set get if the organism is consuming something
     * @return
     */
    public boolean isConsuming() {
        return eating || drinking;
    }

    /**
     * to get current skin id
     * @return skin
     */
    public int getSkin() {
        return skin;
    }

    /**
     * to set skin id
     * @param skin new skin id
     */
    public void setSkin(int skin) {
        this.skin = skin;
    }

    /**
     * to get id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * to set maxVel
     * @param maxVel new maxVel
     */
    public void setMaxVel(int maxVel) {
        this.maxVel = maxVel;
    }

    /**
     * to set id
     * @param id new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * to get the mutationManager
     * @return orgMutations
     */
    public MutationManager getOrgMutations() {
        return orgMutations;
    }
    
    /**
     * to set hunger
     * @param hunger new hunger
     */
    public void setHunger(int hunger){
        this.hunger = hunger;
    }
    
    /**
     * to get hunger
     * @param thirst new thirst
     */
    public void setThirst(int thirst){
        this.thirst = thirst;
    }
  
    /**
     * to get selected
     * @return selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * to set selected
     * @param selected selected state
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
     
    /**
     * to get beingChased
     * @return beingChased
     */
    public boolean isBeingChased(){
        return beingChased;
    }
    
    /**
     * to set beingChased
     * @param beingChased beingChased state
     */
    public void isBeingChased(boolean beingChased){
        this.beingChased = beingChased;
    }

    /**
     * to get godCommand
     * @return godCommand
     */
    public boolean isGodCommand() {
        return godCommand;
    }

    /**
     * to set godCommand
     * @param godCommand godCommand state
     */
    public void setGodCommand(boolean godCommand) {
        this.godCommand = godCommand;
    }

    /**
     * to get damage
     * @return damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * to set damage
     * @param damage new damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * to set xVel
     * @param xVel new xVel
     */
    public void setxVel(int xVel) {
        this.xVel = xVel;
    }

    /**
     * to set yVel
     * @param yVel new yVel
     */
    public void setyVel(int yVel) {
        this.yVel = yVel;
    }

    /**
     * to set beingChased
     * @param beingChased beingChased state
     */
    public void setBeingChased(boolean beingChased) {
        this.beingChased = beingChased;
    }

    /**
     * to get the stealth range
     * @return stealth range
     */
    public int getStealthRange() {
        return stealthRange;
    }

    /**
     * to set the stealth range
     * @param stealthRange new stealth range
     */
    public void setStealthRange(int stealthRange) {
        this.stealthRange = stealthRange;
    }

    /**
     * to get current size
     * @return currentSize
     */
    public int getCurrentSize() {
        return currentSize;
    }

    /**
     * to set current size
     * @param currentSize new size
     */
    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    /**
     * to get current max health
     * @return currentMaxHealth
     */
    public int getCurrentMaxHealth() {
        return currentMaxHealth;
    }

    /**
     * to set current max health
     * @param currentMaxHealth new max health
     */
    public void setCurrentMaxHealth(int currentMaxHealth) {
        this.currentMaxHealth = currentMaxHealth;
    }

    /**
     * to check if the organism is egg
     * @return egg
     */
    public boolean isEgg() {
        return egg;
    }

    /**
     * to set egg state
     * @param egg egg state
     */
    public void setEgg(boolean egg) {
        this.egg = egg;
    }

    /**
     * to get need mutation
     * @return return needMutation
     */
    public boolean isNeedMutation() {
        return needMutation;
    }

    /**
     * to set need mutation state
     * @param needMutation need mutations state
     */
    public void setNeedMutation(boolean needMutation) {
        this.needMutation = needMutation;
    }

    /**
     * to get born state
     * @return born
     */
    public boolean isBorn() {
        return born;
    }

    /**
     * to set born state
     * @param born born state
     */
    public void setBorn(boolean born) {
        this.born = born;
    }

    /**
     * to get other state
     * @return other
     */
    public boolean isOther() {
        return other;
    }

    /**
     * to set other state
     * @param other other state
     */
    public void setOther(boolean other) {
        this.other = other;
    }

    /**
     * to set visible state
     * @param visible visible state
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * to check if animation is done playing
     * @return animationDone
     */
    public boolean isAnimationDone() {
        return animationDone;
    }

    public boolean isInCamp() {
        return inCamp;
    }

    public void setInCamp(boolean inCamp) {
        this.inCamp = inCamp;
    }
}