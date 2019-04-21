package evolith.entities;

import evolith.game.Game;
import evolith.menus.Hover;
import evolith.game.Item;
import evolith.helpers.SwarmMovement;
import evolith.helpers.Time;
import evolith.engine.Assets;
import evolith.helpers.Commons;
import static evolith.helpers.Commons.WATERS_AMOUNT;
import static evolith.helpers.Commons.WATER_SIZE;

import evolith.menus.OrganismPanel;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class PredatorManager implements Commons {

    private ArrayList<Predator> predators;  //array of all organisms
    private int amount;         //max organism amount

    private Game game;          // game instance
    private int idCounter;

    /**
     * Constructor of the organisms
     *
     * @param game
     */
    public PredatorManager(Game game) {
        this.game = game;
        predators = new ArrayList<>();
        amount = PREDATORS_AMOUNT;
        idCounter = 0;
/*
        for (int i = 0; i < amount; i++) {
            predators.add(new Predator(INITIAL_POINT + i*100, INITIAL_POINT + i*100, PREDATOR_SIZE, PREDATOR_SIZE, game));
        }*/
        
        Random randomGen = new Random();
        
        int newWidthPredators = (int) Math.ceil( 5000/Math.sqrt(PREDATORS_AMOUNT) );
        int newHeightPredators = (int) Math.ceil( 5000/Math.sqrt(PREDATORS_AMOUNT) );
         
        for (int i = newWidthPredators; i < 5000; i += newWidthPredators){
            for (int j = newHeightPredators; j < 5000; j += newHeightPredators){
                int xCoord, yCoord; 
                xCoord = randomGen.nextInt(newWidthPredators) + j;
                yCoord = randomGen.nextInt(newHeightPredators) + i;
                predators.add(new Predator(xCoord, yCoord, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            }
        }
    }

    /**
     * updates all organisms
     */
    public void tick() {
        for (int i = 0; i < predators.size(); i++) { 
            predators.get(i).tick();
            
            //Look for the nearest organism, if no, then water
            autoLookTarget(predators.get(i));
            checkWithOrganisms(predators.get(i));
            checkKill(predators.get(i));
        }
        
        checkArrivalOnResource();
        checkPredatorResourceStatus();
    }
    
    private void checkWithOrganisms(Predator pred) {
        //Check status with every organism
        for (int j = 0; j < game.getOrganisms().getOrganismsAmount(); j++) {
            Organism org = game.getOrganisms().getOrganism(j);
            //Check if the predator is touching an organism
            if (pred.intersects(org)) {
                //Get current life
                double acutalLife = org.getLife();

                //Decrease life
                org.setLife(acutalLife - pred.getDamage());
                pred.setLife(pred.getLife() - org.getDamage());
            }           
        }
    }

    /**
     * Check if an organism needs to be killed
     *
     * @param org
     */
    private void checkKill(Predator pred) {
        if (pred.isDead()) {
            predators.remove(pred);
            amount--;
        }
    }
    
    public void checkPredatorResourceStatus() {
        for (int i = 0; i < predators.size(); i++) {
            Predator pred = predators.get(i);
            Resource target = predators.get(i).getTargetResource();
            //Check if target exists
            if (target != null) {
                //Check if the current target does have a predator
                if ((target.getPredator() != null && target.getPredator() != pred) || target.isOver()) {
                    System.out.println("FULL:  " + i);
                    autoLookTarget(pred);
                } else {
                    //System.out.println("PREDATOR IN RESOURCE:  " + i);
                }
            } else {
                //System.out.println("NO TARGET:  " + i);
                autoLookTarget(pred);
            }
        }
    }
    
    /**
     * Check if the predator has arrived to resource, if so, assign it to it
     */
    public void checkArrivalOnResource() {
        for (int i = 0; i < predators.size(); i++) {
            Predator pred = predators.get(i);
            Resource target = predators.get(i).getTargetResource();
            if (target != null) {
                if (target.intersects(pred) && target.getPredator() == null) {
                    target.setPredator(pred);
                } else {
                    autoLookTarget(pred);
                }
            }
        }
    }
    
    public void autoLookTarget(Predator pred) {
        
        //Finds closest organism or water
        Resource res = findNearestValidWater(pred);
        Organism org = findNearestOrganism(pred);
        
        //If there is an organism and is in valid distance
        if (org != null && SwarmMovement.distanceBetweenTwoPoints(pred.getX(), pred.getY(), org.getX(), org.getY()) < MAX_SIGHT_DISTANCE 
                && !pred.isRecovering()) {
            pred.setTarget(org);
            
            if (pred.getTargetResource() != null && pred.getTargetResource().getPredator() == pred) {
                pred.getTargetResource().setPredator(null);
            }
            
            pred.setTargetResource(null);
            pred.setStamina(pred.getStamina() - 0.3);
        } else if (res != null) {
            if (pred.getTargetResource() == null) {
                pred.setTargetResource(res);
                pred.setTarget(null);
            } else if (pred.getTargetResource().getPredator() != pred) {
                //If not check if a resource is nearby and set target to that one
                pred.setTargetResource(res);
                pred.setTarget(null);
            }
        }
    }
        
    /**
     *
     * @param pred
     * @return
     */
    public Organism findNearestOrganism(Predator pred){
        Organism closestOrganism = null; 
        double closestDistanceBetweenPredatorAndOrganism = 1000000;

        //Organism(int x, int y, int width, int height, Game game, int skin, int id)
        for(int i = 1; i < game.getOrganisms().getOrganismsAmount(); i++){
            double distanceBetweenPredatorAndOrganism = 7072;

                distanceBetweenPredatorAndOrganism = Math.sqrt(Math.pow(pred.getX()-game.getOrganisms().getOrganism(i).getX(),2)
                        + Math.pow(pred.getY()-game.getOrganisms().getOrganism(i).getY(),2) );

            
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
    
    public Resource findNearestValidWater(Predator pred) {
        Resource closestWater = null; 
        double closestDistanceBetweenWaterAndOrganism = 1000000;
        
        for(int i = 1; i < game.getResources().getWaterAmount(); i++){
            double distanceBetweenPlantAndOrganism = 7072;
            
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(pred.getX()- game.getResources().getWater(i).getX(), 2)
                        + Math.pow(pred.getY()- game.getResources().getWater(i).getY(), 2));

            if (distanceBetweenPlantAndOrganism < closestDistanceBetweenWaterAndOrganism) {
                if (game.getResources().getWater(i).getPredator() == null) {
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
     * To render the organisms
     *
     * @param g
     */
    public void render(Graphics g) {

        for (int i = 0; i < predators.size(); i++) {
            predators.get(i).render(g);
        }
    }

    /**
     * Get the positions <code>Point</code> of the organisms
     *
     * @return
     */
    public ArrayList<Point> getPredatorsPositions() {
        ArrayList<Point> positions = new ArrayList<>();

        for (int i = 0; i < predators.size(); i++) {
            positions.add(new Point(predators.get(i).getX(), predators.get(i).getY()));
        }

        return positions;
    }

    public Predator getPredator(int i){
        return predators.get(i);
    }

    public int getPredatorAmount() {
        return predators.size();
    }
    /**
     * Single organism class
     */
}
