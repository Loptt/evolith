package evolith.entities;

import evolith.game.Game;
import evolith.helpers.Commons;
import static evolith.helpers.Commons.MAX_SIGHT_DISTANCE;
import evolith.helpers.SwarmMovement;

import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
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
    private int amount;                     //max organism amount

    private Game game;          // game instance
    private int idCounter;      //Counter to assign ids
    
    private int currentMaxAmount; //current max predator amount
    
    private boolean purged;       //purged stat

    /**
     * Constructor of the predators
     *
     * @param game game object
     */
    public PredatorManager(Game game) {
        this.game = game;
        predators = new ArrayList<>();
        idCounter = 0;
        currentMaxAmount = PREDATORS_AMOUNT;
        purged = false;
        
        int separation = BACKGROUND_WIDTH / (PREDATORS_AMOUNT / 4);
        
        for (int i = 0; i < PREDATORS_AMOUNT / 4; i++) {
            predators.add(new Predator(separation * i, -1000, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            predators.add(new Predator(separation * i, BACKGROUND_HEIGHT+1000, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            predators.add(new Predator(-1000, separation*i, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            predators.add(new Predator(BACKGROUND_WIDTH + 1000, separation*i, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
        }
    }

    /**
     * updates all predators
     */
    public void tick() {
        for (int i = 0; i < predators.size(); i++) { 
            predators.get(i).tick();
            checkWithOrganisms(predators.get(i));
            
            if (game.getOtherOrganisms() != null) {
                checkWithOtherOrganisms(predators.get(i));
            }
            
            checkKill(predators.get(i));
        }
        
        if (game.isNight()) {
            currentMaxAmount = PREDATORS_AMOUNT + 4 * (game.getOrganisms().getAmount() / 10);
            purged = false;
        } else {
            currentMaxAmount = PREDATORS_AMOUNT + 2 * (game.getOrganisms().getAmount() / 10);;
        }
        
        checkPredatorsAmount();
    }
    
    /**
     * Check if predator amount is under the allowed limit
     */
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
    
    /**
     * Check if a predator needs to be removed
     */
    public void checkKill() {
        for (int i = 0; i < predators.size(); i++) { 
            checkKill(predators.get(i));
        }
    }
    
    /**
     * Check if predators and organisms are intersecting to remove life, also check visible
     * @param pred predator to check
     */
    private void checkWithOrganisms(Predator pred) {
        //Check status with every organism
        pred.setVisible(false);
        for (int j = 0; j < game.getOrganisms().getOrganismsAmount(); j++) {
            Organism org = game.getOrganisms().getOrganism(j);
            //Check if the predator is touching an organism
            if (SwarmMovement.distanceBetweenTwoPoints(org.getX(), org.getY(), pred.getX(), pred.getY()) < MAX_SIGHT_DISTANCE + 350) {
                pred.setVisible(true);
                game.getSfx().playAlien();
            }
            
            if (pred.intersects(org)) {
                //Get current life
                double acutalLife = org.getLife();
                
                if (!org.isEgg()) {
                    pred.setLife(pred.getLife() - org.getDamage());
                }
                
                org.setLife(acutalLife - pred.getDamage());
                //Decrease life
                
            }           
        }
    }
    
    /**
     * Check with opponent organisms
     * @param pred predator to check
     */
    private void checkWithOtherOrganisms(Predator pred) {
        for (int j = 0; j < game.getOtherOrganisms().getOrganismsAmount(); j++) {
            Organism org = game.getOtherOrganisms().getOrganism(j);
            if (pred.intersects(org)) {
                //Get current life
                double acutalLife = org.getLife();
                pred.setLife(pred.getLife() - org.getDamage());
            }           
        }
    }

    /**
     * Check if an organism needs to be killed
     *
     * @param pred predator to check
     */
    private void checkKill(Predator pred) {
        if (pred.isDead()) {
            predators.remove(pred);
            amount--;
        }
    }
    
    /**
     * Save current predators state to print writer
     * @param pw print writer
     */
    public void save(PrintWriter pw) {
        pw.println(Integer.toString(predators.size()));
        
        for (int i = 0; i < predators.size(); i++) {
            predators.get(i).save(pw);
        }
    }
    
    /**
     * load last saved state from buffered reader
     * @param br buffered reader
     * @throws IOException 
     */
    public void load(BufferedReader br) throws IOException {
        int am = Integer.parseInt(br.readLine());
        predators.clear();
        
        for (int i = 0; i < am; i++) {
            predators.add(new Predator(0,0,PREDATOR_SIZE, PREDATOR_SIZE, game, 0));
            predators.get(i).load(br);
        }
    }
    
    /**
     * Reset predators to its initial values
     */
    public void reset() {
        predators.clear();
        idCounter = 0;
        int separation = BACKGROUND_WIDTH / (PREDATORS_AMOUNT / 4);
        
        for (int i = 0; i < PREDATORS_AMOUNT / 4; i++) {
            predators.add(new Predator(separation * i, -1000, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            predators.add(new Predator(separation * i, BACKGROUND_HEIGHT+1000, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            predators.add(new Predator(-1000, separation*i, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
            predators.add(new Predator(BACKGROUND_WIDTH + 1000, separation*i, PREDATOR_SIZE, PREDATOR_SIZE, game, ++idCounter));
        }
    }
    
    /**
     * To render the organisms
     *
     * @param g graphics
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
    
    /**
     * Get predator at position i
     * @param i position
     * @return predator at position i
     */
    public Predator getPredator(int i){
        return predators.get(i);
    }
    
    /**
     * Get the current predator amount
     * @return 
     */
    public int getPredatorAmount() {
        return predators.size();
    }
    
    /**
     * Get the game object
     * @return game
     */
    public Game getGame() {
        return game;
    }
}
