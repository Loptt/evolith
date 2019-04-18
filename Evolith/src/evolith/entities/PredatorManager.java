package evolith.entities;

import evolith.game.Game;
import evolith.menus.Hover;
import evolith.game.Item;
import evolith.helpers.SwarmMovement;
import evolith.helpers.Time;
import evolith.engine.Assets;
import evolith.helpers.Commons;

import evolith.menus.OrganismPanel;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

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

    /**
     * Constructor of the organisms
     *
     * @param game
     */
    public PredatorManager(Game game) {
        this.game = game;
        predators = new ArrayList<>();
        amount = PREDATORS_AMOUNT;

        for (int i = 0; i < amount; i++) {
            predators.add(new Predator(INITIAL_POINT + i*100, INITIAL_POINT + i*100, PREDATOR_SIZE, PREDATOR_SIZE, game));
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
            
            //Check status with every organism
            for (int j = 0; j < game.getOrganisms().getOrganismsAmount(); j++) {
                //Check if the predator is touching an organism
                if (predators.get(i).intersects(game.getOrganisms().getOrganism(j))) {
                    //Get current life
                    double acutalLife = game.getOrganisms().getOrganism(j).getLife();
                    
                    //Decrease life
                    game.getOrganisms().getOrganism(j).setLife(acutalLife - 0.1);
                }           
            }

            checkKill(predators.get(i));
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
    
    public void autoLookTarget(Predator pred) {
        
        //Finds closest organism or water
        Resource res = findNearestValidWater(pred);
        Organism org = findNearestOrganism(pred);
        
        //If there is an organism and is in valid distance
        if (org != null && SwarmMovement.distanceBetweenTwoPoints(pred.getX(), pred.getY(), org.getX(), org.getY()) > MAX_SIGHT_DISTANCE) {
            pred.setTarget(org);
            pred.setTargetResource(null);
        } else if (res != null) {
            //If not check if a resource is nearby and set target to that one
            pred.setTargetResource(res);
            pred.setTarget(null);
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

            if(distanceBetweenPlantAndOrganism < closestDistanceBetweenWaterAndOrganism) {
                closestDistanceBetweenWaterAndOrganism = distanceBetweenPlantAndOrganism;
                closestWater = game.getResources().getWater(i);
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

        for (int i = 0; i < amount; i++) {
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
