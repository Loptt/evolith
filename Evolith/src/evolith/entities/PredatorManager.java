package evolith.entities;

import evolith.game.Game;
import evolith.helpers.Commons;

import java.awt.Graphics;
import java.awt.Point;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
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
    
    private int currentMaxAmount;
    
    private boolean purged;

    /**
     * Constructor of the organisms
     *
     * @param game
     */
    public PredatorManager(Game game) {
        this.game = game;
        predators = new ArrayList<>();
        idCounter = 0;
        currentMaxAmount = PREDATORS_AMOUNT;
        purged = false;
/*
        for (int i = 0; i < amount; i++) {
            predators.add(new Predator(INITIAL_POINT + i*100, INITIAL_POINT + i*100, PREDATOR_SIZE, PREDATOR_SIZE, game));
        }*/
        
        /*
        Random randomGen = new Random();
        
        int newWidthPredators = (int) Math.ceil(5000/Math.sqrt(PREDATORS_AMOUNT) );
        int newHeightPredators = (int) Math.ceil(5000/Math.sqrt(PREDATORS_AMOUNT) );
         
        for (int i = newWidthPredators; i < 5000; i += newWidthPredators){
            for (int j = newHeightPredators; j < 5000; j += newHeightPredators){
                int xCoord, yCoord; 
                xCoord = randomGen.nextInt(newWidthPredators) + j;
                yCoord = randomGen.nextInt(newHeightPredators) + i;
                predators.add(new Predator(xCoord, yCoord, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            }
        }
        */
        
        int separation = BACKGROUND_WIDTH / (PREDATORS_AMOUNT / 4);
        
        for (int i = 0; i < PREDATORS_AMOUNT / 4; i++) {
            predators.add(new Predator(separation * i, -1000, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            predators.add(new Predator(separation * i, BACKGROUND_HEIGHT+1000, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            predators.add(new Predator(-1000, separation*i, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            predators.add(new Predator(BACKGROUND_WIDTH + 1000, separation*i, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
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
        
        if (game.isNight()) {
            currentMaxAmount = PREDATORS_AMOUNT + 4;
            purged = false;
        } else {
            currentMaxAmount = PREDATORS_AMOUNT;
        }
        
        checkPredatorsAmount();
    }
    
    private void checkPredatorsAmount() {
        if (predators.size() < currentMaxAmount && game.isNight()) {
            int missing = currentMaxAmount - predators.size();
            boolean foo;
            boolean bar;
                        
            int yCord; 
            int xCord; 
            
            //Generate missing predators
            for (int i = 0; i < missing; i++) {
                foo = ((int) (Math.random() * 2)) == 1;
                bar = ((int) (Math.random() * 2)) == 1;
                
                //RIGHT
                if (foo && bar) {
                    yCord = (int) (Math.random() *  BACKGROUND_HEIGHT);
                    xCord = BACKGROUND_WIDTH + 300;
                //DOWN
                } else if (!foo && bar) {
                    xCord = (int) (Math.random() *  BACKGROUND_WIDTH);
                    yCord = BACKGROUND_HEIGHT + 300;
                //LEFT
                } else if (foo && !bar) {
                    yCord = (int) (Math.random() *  BACKGROUND_HEIGHT);
                    xCord = -300;
                //UP
                } else {
                    xCord = (int) (Math.random() *  BACKGROUND_WIDTH);
                    yCord = -300;
                }
                
                predators.add(new Predator(xCord, yCord, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            }
        } else if (predators.size() > currentMaxAmount &&!purged) {
            //Remove excess predators
            purged = true;
            int excess = predators.size() - currentMaxAmount;
            HashSet<Integer> leavingIndices = new HashSet<>();
            
            for (int i = 0; i < excess; i++) {
                int index = (int) (Math.random() * predators.size());
                
                if (!leavingIndices.contains(index)) {
                    leavingIndices.add(index);
                } else {
                    i--;
                }
            }
            
            for (Integer i : leavingIndices) {
                predators.get(i).setLeaving(true);
                predators.get(i).setPoint(new Point(2500, -1000));
            }
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
    
    public void save(PrintWriter pw) {
        pw.println(Integer.toString(predators.size()));
        
        for (int i = 0; i < predators.size(); i++) {
            predators.get(i).save(pw);
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
