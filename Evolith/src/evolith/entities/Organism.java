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

    private Point point;
    private int maxVel;
    private int acc;
    private int xVel;
    private int yVel;
    private Game game;

    private Time time;
    
    private int id;

    /**
     * These are the five evolutionary traits
     */
    private int size;
    private int speed;
    private int strength;
    private int stealth;
    private int survivability;

    private double life;           //Health points of the organism
    private int hunger;         //hunger of the organism
    private int thirst;         //thirst of the organism
    private int maturity;       //maturity level of the organsim
    private int generation;     //generation level of the organsim
    private int skin;

    private int prevHungerRed;  //Time in seconds at which hunger was previously reduced
    private int prevThirstRed;  //Time in seconds at which hunger was previously reduced
    private int prevMatInc;     //Time in seconds at which maturity was previously increased

    private boolean needOffspring;
    private boolean dead;
    private boolean beingChased;
    private String name;
    private Point escapePoint;

    private boolean moving;
    private boolean inPlant;
    private boolean inWater;
    private boolean inResource;

    private Resource target;

    private boolean searchFood;
    private boolean searchWater;

    private boolean eating;
    private boolean drinking;
    private boolean selected;

    /**
     * Constructor of the organism
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param skin
     * @param id
     */
    public Organism(int x, int y, int width, int height, Game game, int skin, int id) {
        super(x, y, width, height);
        this.game = game;
        this.skin = skin;
        this.id = id;
        point = new Point(x, y);
        maxVel = 3;
        xVel = 0;
        yVel = 0;
        acc = 1;

        size = 100;
        speed = 20;
        strength = 20;
        stealth = 10;
        survivability = 10;

        life = 100;
        hunger = 100;
        thirst = 100;
        maturity = 0;
        generation = 1;
        escapePoint = point;
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

        eating = false;
        drinking = false;
        selected = false;

        time = new Time();
        name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    public int getStealth() {
        return stealth;
    }

    public int getSurvivability() {
        return survivability;
    }

    public double getLife() {
        return life;
    }

    public void setLife(double life){
        this.life = life;
    }
    
    public int getHunger() {
        return hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public int getMaturity() {
        return maturity;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
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

        //Increase maturity every x seconds defined in the commmons class
        if (time.getSeconds() >= prevMatInc + SECONDS_PER_MATURITY) {
            maturity++;
            prevMatInc = (int) time.getSeconds();

            //Reproduction happen at these two points in maturity
            if (maturity == 10) {
                needOffspring = true;
            }

            if (maturity == 26) {
                needOffspring = true;
            }
        }

        //Once the organisms reaches max maturity, kill it
        if (maturity >= MAX_MATURITY) {
            //kill();
        }
    }
    
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
     * Renders the organisms relative to the camera
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.orgColors.get(skin), game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
        if (selected) {
            g.setColor(Color.RED);
            g.fillOval(game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height);
        }
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

    public Resource getTarget() {
        return target;
    }

    public void setTarget(Resource target) {
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

    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public int getId() {
        return id;
    }
    
    public void setHunger(int hunger){
        this.hunger = hunger;
    }
    
    public void setThirst(int thirst){
        this.thirst = thirst;
    }
  
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
     
    public boolean isBeingChased(){
        return beingChased;
    }
    
    public void isBeingChased(boolean a){
        this.beingChased = a;
    }
    
    public void setEscapePoint(Point p){
        this.escapePoint = p;
    }
    
    public Point getEscapePoint(){
        return escapePoint;
    }
}
