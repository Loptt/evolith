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
            checkWithOrganisms(predators.get(i));
            checkKill(predators.get(i));
        }
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
