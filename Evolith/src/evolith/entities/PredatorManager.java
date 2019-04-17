package evolith.entities;

import evolith.game.Game;
import evolith.menus.Hover;
import evolith.game.Item;
import evolith.helpers.SwarmMovement;
import evolith.helpers.Time;
import evolith.engine.Assets;
import evolith.helpers.Commons;
import static evolith.helpers.Commons.INITIAL_POINT;
import static evolith.helpers.Commons.PREDATOR_SIZE;
import static evolith.helpers.Commons.PANEL_HEIGHT;
import static evolith.helpers.Commons.PANEL_WIDTH;
import static evolith.helpers.Commons.PANEL_X;
import static evolith.helpers.Commons.PANEL_Y;

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
    private int counter;        //frame counter

    private Hover h;            // hover panel
    private boolean hover;      // to know if hovering

    private int newX;           // new x position of the organisms
    private int newY;           // new y position of the organisms

    private int skin;

    private OrganismPanel panel;
    private ArrayList<Point> currentPoss;

    private Point centralPoint;
    private Point targetPoint;
    
    private int panelNum;
    private int idCounter;

    /**
     * Constructor of the organisms
     *
     * @param game
     */
    public PredatorManager(Game game) {
        panelNum = 0;
        this.game = game;
        predators = new ArrayList<>();
        amount = PREDATORS_AMOUNT;
        idCounter = 1;

        for (int i = 0; i < amount; i++) {
            // public Organism(int x, int y, int width, int height, Game game, int skin, int id) {
            // public Predator(int x, int y, int width, int height, Game game, int skin, int id) {
            predators.add(new Predator(INITIAL_POINT + i*100, INITIAL_POINT + i*100, PREDATOR_SIZE, PREDATOR_SIZE, game, 0, idCounter++));
        }
        newX = INITIAL_POINT;
        newY = INITIAL_POINT;

        centralPoint = new Point(INITIAL_POINT, INITIAL_POINT);

        panel = new OrganismPanel(0, 0, 0, 0, this.game);

        targetPoint = new Point(INITIAL_POINT, INITIAL_POINT);
        currentPoss = SwarmMovement.getPositions(500, 500, 50, 1);
    }

    /**
     * updates all organisms
     */
    public void tick() {
        for (int i = 0; i < amount; i++) {
            predators.get(i).tick();
            autoLookTarget(predators.get(i));
            //checkReproduce(organisms.get(i));
            //checkKill(predators.get(i));
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

    public void setOrganism(Organism org) {
        for (int i = 0; i < amount; i++) {
            predators.get(i).setTarget(org);
        }
    }
    
    public void setResource(Resource res){
        for (int i = 0; i < amount; i++) {
            predators.get(i).setTargetResource(res);
        }  
    }

    public void checkOrganismResourceStatus() {
        for (int i = 0; i < amount; i++) {
            Predator pred = predators.get(i);
            Organism target = predators.get(i).getTarget();
            //Check if target exists
        }
    }

    public double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
    }
    
    public void autoLookTarget(Predator pred) {
        Resource res = findNearestValidWater(pred);
        Organism org = findNearestOrganism(pred);
        
        if( distanceBetweenTwoPoints(pred.getX(), pred.getY(), org.getX(), org.getY()) > 100){
            pred.setTargetResource(res);
            pred.setTarget(null);
        }else{
            pred.setTarget(org);
            pred.setTargetResource(null);
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
            
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(pred.getX()-game.getResources().getWater(i).getX(),2)
                        + Math.pow(pred.getY()-game.getResources().getWater(i).getY(),2) );

            if(distanceBetweenPlantAndOrganism<closestDistanceBetweenWaterAndOrganism){
                closestDistanceBetweenWaterAndOrganism = distanceBetweenPlantAndOrganism;
                closestWater = game.getResources().getWater(i);
            }
        }
        
        return closestWater;
    }

    public void checkArrivalOnResource() {
        for (int i = 0; i < predators.size(); i++) {
            Predator pred = predators.get(i);
            Resource target = predators.get(i).getTargetResource();
            if (target != null) {
                if (target.intersects(pred)) {
                    if (!target.isFull()) {
                        if (!target.hasPredator(pred)) {
                            target.addPredator(pred);
                            //Check the resource type
                            if (target.getType() == Resource.ResourceType.Water) {
                                pred.setEating(true);
                            } else {
                                pred.setDrinking(true);
                            }
                        } else {
                            //System.out.println("ORG ALREADY IN TARGET");
                            autoLookTarget(pred);
                        }
                    } else {
                        //System.out.println("TARGET FULL");
                        autoLookTarget(pred);
                    }
                }
            }
        }
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
     * Set the skin of the organisms
     *
     * @param skin
     */
    public void setSkin(int skin) {
        this.skin = skin;

        for (int i = 0; i < predators.size(); i++) {
            predators.get(i).setSkin(skin);
        }
    }

    /**
     * Get the skin <code>int</code> used by the organisms
     *
     * @return
     */
    public int getSkin() {
        return skin;
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
     * to set the central point of the swarm
     *
     * @param centralPoint
     */
    public void setCentralPoint(Point centralPoint) {
        this.centralPoint = centralPoint;
    }

    /**
     * to get the central point of the swarm
     *
     * @return
     */
    public Point getCentralPoint() {
        return centralPoint;
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
