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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Predator extends Item implements Commons {

    private Point point;        //point to move
    private int maxVel;         //Current max vel of the predator
    private double acc;         //rate at which speed increases
    private double xVel;        //speed in the x axis
    private double yVel;        //speed in the y asis
    private Game game;          //Game object

    private Time time;          //Time object
    
    private int maxHealth;      //Predator max life
    private double life;        //Life of the predator

    private int prevHungerRed;  //Time in seconds at which hunger was previously reduced
    private int prevThirstRed;  //Time in seconds at which hunger was previously reduced

    private boolean dead;       //Dead state

    private Organism target;                    //Current organism target
    private Resource targetResource;            //Current resource targe
    private Resource prevTargetResource;        //Previous target resource
    private Resource prevprevTargetResource;    //Prevois previous target resource
    private boolean leaving;
    
    private double damage;                      //Current damage to organisms
    private double stamina;                     //Current stamina
    
    private boolean recovering;                 //Recovering state
    private int id;                             //Unique identifier
    
    private int chasingSpeed;                   //Speed to chase organisms
    
    private int absMaxVel;                      //Absolute max velocity
    private int prevResourceChangeSec;          //Seconds when last resource change occured
    private int prevPointGeneratedSec;          //Seconds when last point generated occured
    
    /**
     *
     */
    public enum Mode { 

        /**
         *
         */
        Water, 

        /**
         *
         */
        Roaming, 

        /**
         *
         */
        Attacking};   //Predator modes
    private Mode mode;                          //Current mode
    private Mode prevMode;                      //Previous mode
    
    private boolean visible;                    //Visible state

    /**
     * Constructor of the Predator
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param width predator width
     * @param height predator height
     * @param game game object
     * @param id unique identifier
     */
    public Predator(int x, int y, int width, int height, Game game, int id) {
        super(x, y, width, height);
        this.game = game;
        point = new Point(x, y);
        maxVel = 2;
        xVel = 0;
        yVel = 0;
        acc = 0.1;

        prevHungerRed = 0;
        prevThirstRed = 0;

        dead = false;
        
        leaving = false;
        
        maxHealth = 120;

        life = maxHealth;

        time = new Time();
        
        damage = 0.3;
        stamina = MAX_STAMINA;
        recovering = false;
        this.id = id;
        absMaxVel = 2;
        
        prevResourceChangeSec = 0;
        prevPointGeneratedSec = 0;
        mode = Mode.Roaming;
        prevMode = Mode.Roaming;
        
        applyVariances();
        visible = false;
        
        chasingSpeed = 3;
    }

    /**
     * To tick the predator
     */
    @Override
    public void tick() {
        time.tick();
        
        //If leaving, just move
        if (leaving) {
            checkMovement();
            checkVitals();
            checkLeft();
            return;
        }
        
        autoLookTarget();
        
        //Check each mode
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
    
    /**
     * Modify the predator to its 3 possible configurations
     */
    private void applyVariances() {
        int chance = (int) (Math.random() * 10);
        
        // 3/10 chance of becoming the big predator, only if there are more than 15 organisms
        if (chance < 3 && game.getOrganisms().getAmount() > 15) {
            //Big *scary*
            width = PREDATOR_SIZE + 20;
            height = PREDATOR_SIZE + 20;
            
            damage = 0.3;
            
            chasingSpeed = 2;
            
            maxHealth = 150;
            life = maxHealth;
            // 4 /10 chance of becoming the medium predator
        } else if (chance  < 7) {
            //Medium
            width = PREDATOR_SIZE;
            height = PREDATOR_SIZE;
            
            damage = 0.2;
            
            chasingSpeed = 2;
            // 4 / 10 chance of becoming the small predator
        } else {
            //Small
            width = PREDATOR_SIZE - 20;
            height = PREDATOR_SIZE - 20;
            
            damage = 0.07;
            
            chasingSpeed = 3;
            
            maxHealth = 100;
            life = 100;
        }
    }

    /**
     * Update the position of the predator accordingly
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
    
    /**
     * Check if the predator has left the map
     */
    private void checkLeft() {
        if (x > BACKGROUND_WIDTH + 100) {
            kill();
        }
        
        if (x < 0 - 100) {
            kill();
        }
        
        if (y > BACKGROUND_HEIGHT + 100) {
            kill();
        }
        
        if (y < 0 - 100) {
            kill();
        }
    }
    
    /**
     * Move to the designated point
     */
    private void moveToPoint() {
        // if the organism is less than 25 units reduce velocity
        if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 25) {
            // if the organism is less than 15 units reduce velocity
            if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 15) {
                // if the organism is less than 5 units reduce velocity
                if (Math.abs((int) point.getX() - x) < 5 && Math.abs((int) point.getY() - y) < 5) {
                    maxVel = 0;
                } else {
                    maxVel = (int) Math.ceil(absMaxVel / 3);
                }
            } else {
                maxVel = (int) Math.ceil(absMaxVel / 2);
            }
        } else {
            maxVel = absMaxVel / 1;
        }
    }
    
    /**
     * Check if the predator needs to change water
     */
    private void waterChecking() {
        if (time.getSeconds() >= prevResourceChangeSec + PREDATOR_SECONDS_IN_RESOURCE) {
            lookNewTarget();
            prevResourceChangeSec = (int) time.getSeconds();
        }
    }
    
    /**
     * Roam the map
     */
    private void roaming() {
        absMaxVel = 1;
        if (time.getSeconds() >= prevPointGeneratedSec + PREDATOR_SECONDS_TO_ROAM) {
            assignNewPoint();
            prevPointGeneratedSec = (int) time.getSeconds();
        }
    }
    
    /**
     * Assign a new point to roam
     */
    private void assignNewPoint() {
        int newX = (int) (Math.random() * (BACKGROUND_WIDTH - 200) + 100 );
        int newY = (int) (Math.random() * (BACKGROUND_HEIGHT - 200) + 100 );
        
        point = new Point(newX, newY);
    }

    /**
     * To check the update and react to the vital stats of the organism
     */
    private void checkVitals() {
        
        if (stamina <= 0) {
            recovering = true;
            target = null;
            assignNewPoint();
            mode = prevMode;
        }
        
        if (stamina >= MAX_STAMINA / 2) {
            recovering = false;
        }
        
        if (life <= 0) {
            dead = true;
        }
        
        if (stamina < 100) {
            stamina += 0.1;
        }
    }
    
    /**
     * Handle current target
     */
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
     * check if resource is still valid
     */
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
    
    /**
     * Look new target
     */
    public void autoLookTarget() {
        //Finds closest organism or water
        Resource res = findNearestValidWater();
        Organism org = findNearestOrganism();
        
        //If there is an organism and is in valid distance
        if (org != null && SwarmMovement.distanceBetweenTwoPoints(getX(), getY(), org.getX(), org.getY()) < org.getStealthRange()
                && !isRecovering()) {
            setTarget(org);
            
            if (getTargetResource() != null && getTargetResource().getPredator() == this) {
                getTargetResource().setPredator(null);
            }
            
            absMaxVel = chasingSpeed;
            if (mode != Mode.Attacking) {
                prevMode = mode;
                mode = Mode.Attacking;
            }
            
            if (game.isServer()) {
                setTargetResource(null);
            }
            setStamina(getStamina() - 0.3);
        } else {
            if (mode == Mode.Attacking) {
                assignNewPoint();
                mode = prevMode;
            }
            if (res != null && mode != Mode.Roaming) {
                if (getTargetResource() == null) {
                    absMaxVel = 1;
                    
                    if (game.isServer()) {
                        setTargetResource(res);
                        setTarget(null);
                    }
                } else if (getTargetResource().getPredator() != this) {
                    //If not check if a resource is nearby and set target to that one
                    if (game.isServer()) {
                        setTargetResource(res);
                        setTarget(null);
                    }
                    absMaxVel = 1;
                }
            }
        }
    }
    
    /**
     * 
     */
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
    
    /**
     *
     * @return
     */
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
    
    /**
     *
     * @return
     */
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
    
    /**
     *
     */
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
     *
     * @param pw
     */
    public void save(PrintWriter pw) {
        pw.println(Integer.toString(id));
        
        //Save positions
        pw.println(Integer.toString(x));
        pw.println(Integer.toString(y));
        pw.println(Integer.toString((int) xVel));
        pw.println(Integer.toString((int) yVel));
        
        pw.println(Integer.toString(width));
        pw.println(Integer.toString(absMaxVel));
        pw.println(Double.toString(damage));
        pw.println(Integer.toString(maxHealth));
        
        //Save vitals
        pw.println(Integer.toString((int) life));
        pw.println(Integer.toString((int) stamina));
    }
    
    /**
     *
     * @param br
     * @throws IOException
     */
    public void load(BufferedReader br) throws IOException {
        id = Integer.parseInt(br.readLine());
        
        x = Integer.parseInt(br.readLine());
        y = Integer.parseInt(br.readLine());
        xVel = Integer.parseInt(br.readLine());
        yVel = Integer.parseInt(br.readLine());
        
        width = Integer.parseInt(br.readLine());
        height = width;
        
        absMaxVel = Integer.parseInt(br.readLine());
        damage = Double.parseDouble(br.readLine());
        maxHealth = Integer.parseInt(br.readLine());
        
        life = Integer.parseInt(br.readLine());
        stamina = Integer.parseInt(br.readLine());
    }

    /**
     * Renders the organisms relative to the camera
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        if (!visible) {
            return;
        }
        
        g.drawImage(Assets.predator, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
        
        double barOffX = 0.03;
        double barOffY = 0.87;
        
        g.setColor(Color.RED);
        g.fillRect(game.getCamera().getRelX(x)+ (int) (width * barOffX), game.getCamera().getRelY(y) + (int) (height * barOffY), (int) ((int)(width) * this.life / maxHealth), 5);
        g.setColor(Color.white);
        g.drawRect(game.getCamera().getRelX(x)+ (int) (width * barOffX) - 1, game.getCamera().getRelY(y) + (int) (height * barOffY), (int)(width), 6);
        
        g.setColor(Color.YELLOW);
        g.fillRect(game.getCamera().getRelX(x)+(int) (width * barOffX), game.getCamera().getRelY(y) + (int) (height * barOffY) + 6, (int) ((int)(width) * this.stamina / MAX_STAMINA), 5);
        g.setColor(Color.white);
        g.drawRect(game.getCamera().getRelX(x)+(int) (width * barOffX) -1, game.getCamera().getRelY(y) + (int) (height * barOffY) + 6 - 1, (int)(width), 6);
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
     *
     * @return
     */
    public double getLife() {
        return life;
    }

    /**
     *
     * @return
     */
    public Time getTime() {
        return time;
    }

    /**
     *
     * @param time
     */
    public void setTime(Time time) {
        this.time = time;
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
     *
     * @return
     */
    public Organism getTarget() {
        return target;
    }
    
    /**
     *
     * @return
     */
    public Resource getTargetResource(){
        return targetResource;
    }
    
    /**
     *
     * @param target
     */
    public void setTargetResource(Resource target){
        this.targetResource = target;
    }

    /**
     *
     * @param target
     */
    public void setTarget(Organism target) {
        this.target = target;
    }
    
    /**
     *
     * @return
     */
    public double getDamage() {
        return damage;
    }

    /**
     *
     * @param damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     *
     * @param life
     */
    public void setLife(double life) {
        this.life = life;
    }

    /**
     *
     * @param stamina
     */
    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    /**
     *
     * @return
     */
    public double getStamina() {
        return stamina;
    }

    /**
     *
     * @return
     */
    public boolean isRecovering() {
        return recovering;
    }

    /**
     *
     * @param recovering
     */
    public void setRecovering(boolean recovering) {
        this.recovering = recovering;
    }

    /**
     *
     * @return
     */
    public boolean isLeaving() {
        return leaving;
    }

    /**
     *
     * @param leaving
     */
    public void setLeaving(boolean leaving) {
        this.leaving = leaving;
    }

    /**
     *
     * @return
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     *
     * @param absMaxVel
     */
    public void setAbsMaxVel(int absMaxVel) {
        this.absMaxVel = absMaxVel;
    }

    /**
     *
     * @param maxHealth
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     *
     * @return
     */
    public Mode getMode() {
        return mode;
    }

    /**
     *
     * @param maxVel
     */
    public void setMaxVel(int maxVel) {
        this.maxVel = maxVel;
    }

    /**
     *
     * @return
     */
    public int getAbsMaxVel() {
        return absMaxVel;
    }

    /**
     *
     * @param chasingSpeed
     */
    public void setChasingSpeed(int chasingSpeed) {
        this.chasingSpeed = chasingSpeed;
    }

    /**
     *
     * @return
     */
    public int getChasingSpeed() {
        return chasingSpeed;
    }
}
