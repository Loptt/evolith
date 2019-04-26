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
import evolith.helpers.Time;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

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
    private int size;
    private int speed;
    private int strength;
    private int stealth;
    private int maxHealth;

    private double life;           //Health points of the organism
    private int hunger;         //hunger of the organism
    private int thirst;         //thirst of the organism
    private int maturity;       //maturity level of the organsim
    private int generation;     //generation level of the organsim
    private int skin;

    private int prevHungerRed;  //Time in seconds at which hunger was previously reduced
    private int prevThirstRed;  //Time in seconds at which hunger was previously reduced
    private int prevMatInc;     //Time in seconds at which maturity was previously increased

    private boolean needOffspring;  //Value indicating if the organisms needs to reproduce
    private boolean dead;           //Whether the organism is dead or not
    private boolean beingChased;    //Value wether it is being chased or not
    private String name;            //Name of the organism

    private boolean moving;         //Value if it is moving
    private boolean inPlant;        //Value if it is on a plant
    private boolean inWater;        //Value if it is in a water srource
    private boolean inResource;     //Value if it is on a resource

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
    private int stealthRange;
    
    private int currentMaxHealth;
    private int currentSize;

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
     */
    public Organism(int x, int y, int width, int height, Game game, int skin, int id) {
        super(x, y, width, height);
        this.game = game;
        this.skin = skin;
        this.id = id;
        point = new Point(x, y);
        maxVel = 2;
        absMaxVel = 2;
        xVel = 0;
        yVel = 0;
        acc = 1;
        
        //Initialize stats
        size = 0;
        speed = 0;
        strength = 0;
        stealth = 0;
        maxHealth = 0;
                
        updateStats();
        
        hunger = 50;
        thirst = 50;

        maturity = 0;
        generation = 1;
        prevHungerRed = 0;
        prevThirstRed = 0;
        prevMatInc = 0;

        needOffspring = false;
        dead = false;
        inPlant = false;
        inWater = false;
        inResource = false;

        searchFood = false;
        searchWater = false;
        aggressive = false;

        eating = false;
        drinking = false;
        selected = false;
        godCommand = false;
        
        damage = 0.05;

        time = new Time();
        name = "";

        orgMutations = new MutationManager(this, game);
    }

    /**
     * Update the position of the organism according to the point
     */
    private void checkMovement() {
        // if the organism is less than 25 units reduce velocity
        if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 25) {
            // if the organism is less than 15 units reduce velocity
            if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 15) {
                // if the organism is less than 5 units reduce velocity
                if (Math.abs((int) point.getX() - x) < 5 && Math.abs((int) point.getY() - y) < 5) {
                    moving = false;
                    maxVel = 0;
                    if (godCommand) {
                        godCommand = false;
                    }
                } else {
                    moving = true;
                    maxVel = 1;
                }
            } else {
                moving = true;
                maxVel = absMaxVel / 2;
            }
        } else {
            moving = true;
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
        //Reduce hunger every x seconds defined in the commmons class
        if (time.getSeconds() >= prevHungerRed + SECONDS_PER_HUNGER && !eating) {
            hunger--;
            prevHungerRed = (int) time.getSeconds();
        }

        //Reduce thirst every x seconds defined in the commmons class
        if (time.getSeconds() >= prevThirstRed + SECONDS_PER_THIRST && !drinking) {
            thirst--;
            prevThirstRed = (int) time.getSeconds();
        }
        
        if (hunger <= 0) {
            hunger = 0;
            life -= 0.05;
        }
        
        if (thirst <= 0) {
            thirst = 0;
            life -= 0.05;
        }

        //Increase maturity every x seconds defined in the commmons class
        if (time.getSeconds() >= prevMatInc + SECONDS_PER_MATURITY) {
            maturity++;
            prevMatInc = (int) time.getSeconds();

            //Reproduction happen at these two points in maturity
            if (maturity == 3) {
                needOffspring = true;
            }

            if (maturity == 10) {
                needOffspring = true;
            }
            
        }

        //Once the organisms reaches max maturity, kill it
        if (maturity >= MAX_MATURITY) {
            //kill();
        }
        
        if (life <= 0) {
            dead = true;
        }
    }
    
    /**
     * Update according to its current target
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
     * Kill the organism
     */
    public void kill() {
        dead = true;
        if (target != null && isConsuming()) {
            target.removeParasite(this, id);
        }
    }
    
    /**
     * Update its stats and vitals with its current mutations
     * @param trait
     * @param newTier 
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
        
        updateStats();
    }
    
    private void updateStats() {
        //Transform stat numbers to useful numbers
        currentMaxHealth = maxHealth * 2 + 100;
        life = currentMaxHealth;
        
        currentSize = (int) (maxHealth * 0.5 + 30);
        width = currentSize;
        height = currentSize;
        
        stealthRange = MAX_SIGHT_DISTANCE - (stealth) * 5;
        
        damage = strength * (0.05/20.0) + 0.05;
        
        absMaxVel = (int) ((double) speed * (1.0/20.0)) + 1;
    }
    
    /**
     * Create a copy of the organism
     * @return new organism
     */
    public Organism cloneOrg(){
        Organism org = new Organism(x,y,width, height, game, skin, id);
        org.setPoint((Point) point.clone());
        org.setMaxVel(maxVel);
        org.setSize(size);
        org.setSpeed(speed);
        org.setStrength(strength);
        org.setMaxHealth(maxHealth);
        org.setLife(maxHealth*2+60);
        org.setGeneration(generation+1);
        
        for(int i=0; i<4; i++){
            for(int j=0; j<orgMutations.getMutations().get(i).size(); j++){
                if(orgMutations.getMutations().get(i).get(j).isActive()){
                    org.getOrgMutations().getMutations().get(i).get(j).setActive(true);
                }
            }
        }
        
        return org;
    }
    
    /**
     * To tick the organism
     */
    @Override
    public void tick() {
        //to determine the lifespan of the organism
        time.tick();
        handleTarget();
        checkMovement();
        checkVitals();  
    }

    /**
     * Renders the organisms relative to the camera
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.orgColors.get(skin), game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
        
        //Warning that the organism can reproduce
        if(isNeedOffspring()){
            g.setColor(Color.BLACK);
            g.fillOval(game.getCamera().getRelX(getX() - width / 2), game.getCamera().getRelY(getY() - width / 2), currentSize / 2, currentSize / 2);
        }
        
        orgMutations.render(g);
        
        double barOffX = 0.05;
        double barOffY = 1.1;

        g.setColor(Color.RED);
        g.fillRect(game.getCamera().getRelX(x) + (int) (currentSize * barOffX) ,
                game.getCamera().getRelY(y) + (int) (currentSize * barOffY), (int) (currentSize * this.life / currentMaxHealth), 3);
        
        g.setColor(Color.white);
        g.drawRect(game.getCamera().getRelX(x) + (int) (currentSize * barOffX) -1,
                game.getCamera().getRelY(y) + (int) (currentSize * barOffY), currentSize, 4);
      
        if (selected) {
             g.drawImage(Assets.glow, game.getCamera().getRelX(x) - 6, game.getCamera().getRelY(y) - 6, width + 12, height + 12, null);
        }
        
       
        
        //g.setColor(Color.BLACK);
        //g.drawString(Integer.toString(id), game.getCamera().getRelX(x)-20, game.getCamera().getRelY(y) + 70);
    }
    
    /**
     * GETTERS AND SETTERS
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
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * to get the size
     * @return size
     */
    public int getSize() {
        return size;
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
     * to get the life
     * @return life
     */
    public double getLife() {
        return life;
    }

    /**
     * to set life
     * @param life
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
     * @param generation
     */
    public void setGeneration(int generation) {
        this.generation = generation;
    }
    
    /**
     * to set speed
     * @param speed
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    /**
     * to set stealth
     * @param stealth
     */
    public void setStealth(int stealth){
        this.stealth = stealth;
    }

    /**
     * to set size
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * to set strength
     * @param strength
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * to set maxHealth
     * @param maxHealth
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * to set life
     * @param life
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * To get the point
     *
     * @return
     */
    public Point getPoint() {
        return point;
    }

    /**
     * To set the point
     *
     * @param point
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
     * @param needOffspring
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
     * @param dead
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * to get moving
     * @return moving
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * to set moving
     * @param moving
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * to get inPlant
     * @return inPlant
     */
    public boolean isInPlant() {
        return inPlant;
    }

    /**
     * to set inPlant
     * @param inPlant
     */
    public void setInPlant(boolean inPlant) {
        this.inPlant = inPlant;
    }

    /**
     * to get inWater
     * @return inWater
     */
    public boolean isInWater() {
        return inWater;
    }

    /**
     * to set inWater
     * @param inWater
     */
    public void setInWater(boolean inWater) {
        this.inWater = inWater;
    }

    /**
     * to get inResource
     * @return inResource
     */
    public boolean isInResource() {
        return inResource;
    }

    /**
     * to set inResource
     * @param inResource
     */
    public void setInResource(boolean inResource) {
        this.inResource = inResource;
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
     * @param target
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
     * @param searchFood
     */
    public void setSearchFood(boolean searchFood) {
        this.searchFood = searchFood;
    }

    /**
     * to set searchWater
     * @param searchWater
     */
    public void setSearchWater(boolean searchWater) {
        this.searchWater = searchWater;
    }

    /**
     * to set aggressive
     * @param aggressive
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
     * @param eating
     */
    public void setEating(boolean eating) {
        this.eating = eating;
    }

    /**
     * to seat drinking
     * @param drinking
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
     * @param skin
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
     * @param maxVel
     */
    public void setMaxVel(int maxVel) {
        this.maxVel = maxVel;
    }

    /**
     * to set id
     * @param id
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
     * @param hunger
     */
    public void setHunger(int hunger){
        this.hunger = hunger;
    }
    
    /**
     * to get hunger
     * @param thirst
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
     * @param selected
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
     * @param beingChased
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
     * @param godCommand
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
     * @param damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setxVel(int xVel) {
        this.xVel = xVel;
    }

    public void setyVel(int yVel) {
        this.yVel = yVel;
    }

    public void setBeingChased(boolean beingChased) {
        this.beingChased = beingChased;
    }

    public int getStealthRange() {
        return stealthRange;
    }

    public void setStealthRange(int stealthRange) {
        this.stealthRange = stealthRange;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }
}