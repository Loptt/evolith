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
import static evolith.helpers.Commons.MAX_MATURITY;
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
    private boolean searchFood;
    private boolean searchWater;

    private boolean eating;
    private boolean drinking;
    
    private double damage;

    /**
     * Constructor of the organism
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     */
    public Predator(int x, int y, int width, int height, Game game) {
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
                    maxVel = 1;
                }
            } else {
                moving = true;
                maxVel = 2;
            }
        } else {
            moving = true;
            maxVel = 3;
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
        if (time.getSeconds() >= prevHungerRed + SECONDS_PER_HUNGER) {
            hunger--;
            prevHungerRed = (int) time.getSeconds();
        }

        //Reduce thirst every x seconds defined in the commmons class
        if (time.getSeconds() >= prevThirstRed + SECONDS_PER_THIRST) {
            thirst--;
            prevThirstRed = (int) time.getSeconds();
        }
        
        if (life <= 0) {
            dead = true;
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
}
