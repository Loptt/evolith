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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author charles
 */
public class Predator extends Item implements Commons {

    private Point point;
    private int maxVel;
    private double acc;
    private double xVel;
    private double yVel;
    private Game game;

    private Time time;
    
    private double life;
    private int hunger;
    private int thirst;

    private int prevHungerRed;  //Time in seconds at which hunger was previously reduced
    private int prevThirstRed;  //Time in seconds at which hunger was previously reduced

    private boolean dead;

    private boolean moving;
    private boolean inPlant;
    private boolean inWater;
    private boolean inOrganism;
    private boolean inResource;

    private Organism target;
    private Resource targetResource;
    private Resource prevTargetResource;
    private Resource prevprevTargetResource;
    private boolean searchFood;
    private boolean searchWater;

    private boolean eating;
    private boolean drinking;
    
    private double damage;
    private double stamina;
    
    private boolean recovering;
    private int id;
    
    private int absMaxVel;
    private int prevResourceChangeSec;
    private int prevPointGeneratedSec;
    
    private enum Mode {Water, Roaming, Attacking};
    private Mode mode;
    private Mode prevMode;

    /**
     * Constructor of the organism
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param id
     */
    public Predator(int x, int y, int width, int height, Game game, int id) {
        super(x, y, width, height);
        this.game = game;
        point = new Point(x, y);
        maxVel = 3;
        xVel = 0;
        yVel = 0;
        acc = 0.1;

        prevHungerRed = 0;
        prevThirstRed = 0;

        dead = false;
        inPlant = false;
        inWater = false;
        inResource = false;

        searchFood = false;
        searchWater = false;

        eating = false;
        drinking = false;
        
        hunger = 100;
        thirst = 100;
        life = 100;

        time = new Time();
        
        damage = 0.1;
        stamina = 100;
        recovering = false;
        this.id = id;
        absMaxVel = 3;
        
        prevResourceChangeSec = 0;
        prevPointGeneratedSec = 0;
        mode = Mode.Roaming;
        prevMode = Mode.Roaming;
    }

    /**
     * To tick the organism
     */
    @Override
    public void tick() {
        //to determine the lifespan of the organism
        time.tick();
        autoLookTarget();
        
        switch (mode) {
            case Attacking:
                break;
            case Roaming:
                roaming();
                break;
            case Water:
                checkResourceStatus();
                waterChecking();
                break;
        }

        handleTarget();
        checkMovement();
        checkVitals();
        
        //Expected value: once every 40 seconds
        int random = (int) (Math.random() * 2400);
        
        if (random == 10 && mode != Mode.Attacking) {
            
            if (mode == Mode.Roaming) {
                mode = Mode.Water;
            } else {
                mode = Mode.Roaming;
                if (targetResource.getPredator() != null && targetResource.getPredator() == this) {
                    targetResource.setPredator(null);
                }
                targetResource = null;
                assignNewPoint();
            }
        }
    }
    
    public double getLife() {
        return life;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * Update the position of the organism accordingly
     */
    private void checkMovement() {
        moveToPoint();

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
    
    private void moveToPoint() {
        // if the organism is less than 25 units reduce velocity
        if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 25) {
            // if the organism is less than 15 units reduce velocity
            if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 15) {
                // if the organism is less than 5 units reduce velocity
                if (Math.abs((int) point.getX() - x) < 5 && Math.abs((int) point.getY() - y) < 5) {
                    moving = false;
                    maxVel = 0;
                } else {
                    moving = true;
                    maxVel = (int) Math.ceil(absMaxVel / 3);
                }
            } else {
                moving = true;
                maxVel = (int) Math.ceil(absMaxVel / 2);
            }
        } else {
            moving = true;
            maxVel = absMaxVel / 1;
        }
    }
    
    private void waterChecking() {
        if (time.getSeconds() >= prevResourceChangeSec + PREDATOR_SECONDS_IN_RESOURCE) {
            lookNewTarget();
            prevResourceChangeSec = (int) time.getSeconds();
        }
    }
    
    private void roaming() {
        absMaxVel = 1;
        if (time.getSeconds() >= prevPointGeneratedSec + PREDATOR_SECONDS_TO_ROAM) {
            assignNewPoint();
            prevPointGeneratedSec = (int) time.getSeconds();
        }
    }
    
    private void assignNewPoint() {
        int newX = (int) (Math.random() * (BACKGROUND_WIDTH - 200) + 100 );
        int newY = (int) (Math.random() * (BACKGROUND_HEIGHT - 200) + 100 );
        
        point = new Point(newX, newY);
    }

    /**
     * To check the update and react to the vital stats of the organism
     */
    private void checkVitals() {
        //Reduce hunger every x seconds defined in the commmons class
        if (time.getSeconds() >= prevHungerRed + SECONDS_PER_HUNGER) {
            hunger--;
            prevHungerRed = (int) time.getSeconds();
        }

        //Reduce thirst every x seconds defined in the commmons class
        if (time.getSeconds() >= prevThirstRed + SECONDS_PER_THIRST) {
            thirst--;
            prevThirstRed = (int) time.getSeconds();
        }
        
        if (stamina <= 0) {
            recovering = true;
            target = null;
            assignNewPoint();
            mode = prevMode;
        }
        
        if (stamina >= 50) {
            recovering = false;
        }
        
        if (life <= 0) {
            dead = true;
        }
        
        if (stamina < 100) {
            stamina += 0.1;
        }
    }
    
    public void handleTarget() {
        //If no target, do nothing
        if (target == null && targetResource != null) {
            point.x = targetResource.getX();
            point.y = targetResource.getY(); 
        } else if(target != null && targetResource == null){
            point.x = target.getX();
            point.y = target.getY();
        }
    }
    
    public void checkResourceStatus() {
        //Check if target exists
        if (targetResource != null) {
            checkArrivalOnResource();
            //Check if the current target does have a predator
            if ((targetResource.getPredator() != null && targetResource.getPredator() != this) || targetResource.isOver()) {
                autoLookTarget();
            } else {
                //System.out.println("PREDATOR IN RESOURCE:  " + i);
            }
        } else {
            //System.out.println("NO TARGET:  " + i);
            autoLookTarget();
        }
    }
    
    public void autoLookTarget() {
        //Finds closest organism or water
        Resource res = findNearestValidWater();
        Organism org = findNearestOrganism();
        
        //If there is an organism and is in valid distance
        if (org != null && SwarmMovement.distanceBetweenTwoPoints(getX(), getY(), org.getX(), org.getY()) < MAX_SIGHT_DISTANCE 
                && !isRecovering()) {
            setTarget(org);
            
            if (getTargetResource() != null && getTargetResource().getPredator() == this) {
                getTargetResource().setPredator(null);
            }
            
            absMaxVel = 3;
            if (mode != Mode.Attacking) {
                prevMode = mode;
                mode = Mode.Attacking;
            }
            
            setTargetResource(null);
            setStamina(getStamina() - 0.3);
        } else {
            if (mode == Mode.Attacking) {
                assignNewPoint();
                mode = prevMode;
            }
            if (res != null && mode != Mode.Roaming) {
                if (getTargetResource() == null) {
                    setTargetResource(res);
                    setTarget(null);
                    absMaxVel = 1;
                } else if (getTargetResource().getPredator() != this) {
                    //If not check if a resource is nearby and set target to that one
                    setTargetResource(res);
                    setTarget(null);
                    absMaxVel = 1;
                }
            }
        }
    }
    
    private void lookNewTarget() {
        Resource closestWater = null; 
        double closestDistanceBetweenWaterAndOrganism = 1000000;
        
        for(int i = 0; i < game.getResources().getWaterAmount(); i++){
            double distanceBetweenPlantAndOrganism = 7072;
            
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(getX()- game.getResources().getWater(i).getX(), 2)
                        + Math.pow(getY()- game.getResources().getWater(i).getY(), 2));

            if (distanceBetweenPlantAndOrganism < closestDistanceBetweenWaterAndOrganism) {
                if (game.getResources().getWater(i).getPredator() == null) {
                    closestDistanceBetweenWaterAndOrganism = distanceBetweenPlantAndOrganism;
                    closestWater = game.getResources().getWater(i);
                } else {
                    //System.out.println("RESOURCE BUSY");
                }
            }
        }
        
        if (prevprevTargetResource != null) {
            prevprevTargetResource.setPredator(null);
        }
        
        prevprevTargetResource = prevTargetResource;
        prevTargetResource = targetResource;
        targetResource = closestWater;
    }
    
    public Organism findNearestOrganism(){
        Organism closestOrganism = null; 
        double closestDistanceBetweenPredatorAndOrganism = 1000000;

        //Organism(int x, int y, int width, int height, Game game, int skin, int id)
        for(int i = 0; i < game.getOrganisms().getOrganismsAmount(); i++){
            double distanceBetweenPredatorAndOrganism = 7072;

                distanceBetweenPredatorAndOrganism = Math.sqrt(Math.pow(getX()-game.getOrganisms().getOrganism(i).getX(),2)
                        + Math.pow(getY()-game.getOrganisms().getOrganism(i).getY(),2) );

            
            if(distanceBetweenPredatorAndOrganism<closestDistanceBetweenPredatorAndOrganism){
                closestDistanceBetweenPredatorAndOrganism = distanceBetweenPredatorAndOrganism;
                closestOrganism = game.getOrganisms().getOrganism(i);
            }
        }
        /*
        if (closestDistanceBetweenPredatorAndOrganism > 100){
            return null;
        }
        */
        
        return closestOrganism;
    }
    
    public Resource findNearestValidWater() {
        Resource closestWater = null; 
        double closestDistanceBetweenWaterAndOrganism = 1000000;
        
        for(int i = 0; i < game.getResources().getWaterAmount(); i++){
            double distanceBetweenPlantAndOrganism = 7072;
            
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(getX()- game.getResources().getWater(i).getX(), 2)
                        + Math.pow(getY()- game.getResources().getWater(i).getY(), 2));

            if (distanceBetweenPlantAndOrganism < closestDistanceBetweenWaterAndOrganism) {
                if (game.getResources().getWater(i).getPredator() == null && game.getResources().getWater(i) != prevTargetResource) {
                    closestDistanceBetweenWaterAndOrganism = distanceBetweenPlantAndOrganism;
                    closestWater = game.getResources().getWater(i);
                } else {
                    //System.out.println("RESOURCE BUSY");
                }
            }
        }
        
        return closestWater;
    }
    
    public void checkArrivalOnResource() {
        if (targetResource != null) {
            if (targetResource.intersects(this) && targetResource.getPredator() == null) {
                targetResource.setPredator(this);
                prevResourceChangeSec = (int) time.getSeconds();
            } else {
                autoLookTarget();
            }
        }
    }

    /**
     * Kill the organism
     */
    public void kill() {
        dead = true;
    }

    /**
     * Renders the organisms relative to the camera
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.predator, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
        
        g.setColor(Color.RED);
        g.fillRect(game.getCamera().getRelX(x)+3, game.getCamera().getRelY(y) + 70, (int) (80 * this.life / 100), 5);
        g.setColor(Color.white);
        g.drawRect(game.getCamera().getRelX(x)+2, game.getCamera().getRelY(y) + 70, 80, 6);
        
        g.setColor(Color.YELLOW);
        g.fillRect(game.getCamera().getRelX(x)+3, game.getCamera().getRelY(y) + 76, (int) (80 * this.stamina / 100), 5);
        g.setColor(Color.white);
        g.drawRect(game.getCamera().getRelX(x)+2, game.getCamera().getRelY(y) + 76, 80, 6);
        
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(id), game.getCamera().getRelX(x)-20, game.getCamera().getRelY(y) + 90);
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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isInPlant() {
        return inPlant;
    }

    public void setInPlant(boolean inPlant) {
        this.inPlant = inPlant;
    }
    
    public void setInOrganism(boolean inOrganism){
        this.inOrganism = inOrganism;
    }

    public boolean isInOrganism(){
        return inOrganism;
    }
    
    public boolean isInWater() {
        return inWater;
    }

    public void setInWater(boolean inWater) {
        this.inWater = inWater;
    }

    public boolean isInResource() {
        return inResource;
    }

    public void setInResource(boolean inResource) {
        this.inResource = inResource;
    }

    public Organism getTarget() {
        return target;
    }
    
    public Resource getTargetResource(){
        return targetResource;
    }
    
    public void setTargetResource(Resource target){
        this.targetResource = target;
    }

    public void setTarget(Organism target) {
        this.target = target;
    }

    public boolean isSearchFood() {
        return searchFood;
    }

    public boolean isSearchWater() {
        return searchWater;
    }

    public void setSearchFood(boolean searchFood) {
        this.searchFood = searchFood;
    }

    public void setSearchWater(boolean searchWater) {
        this.searchWater = searchWater;
    }

    public boolean isEating() {
        return eating;
    }

    public boolean isDrinking() {
        return drinking;
    }

    public void setEating(boolean eating) {
        this.eating = eating;
    }

    public void setDrinking(boolean drinking) {
        this.drinking = drinking;
    }

    public boolean isConsuming() {
        return eating || drinking;
    }
    
    public void setHunger(int hunger){
        this.hunger = hunger;
    }
    
    public void setThirst(int thirst){
        this.thirst = thirst;
    }    

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public double getStamina() {
        return stamina;
    }

    public boolean isRecovering() {
        return recovering;
    }

    public void setRecovering(boolean recovering) {
        this.recovering = recovering;
    }
}
