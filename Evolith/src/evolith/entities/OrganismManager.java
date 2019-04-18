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
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class OrganismManager implements Commons {

    private ArrayList<Organism> organisms;  //array of all organisms
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
    public OrganismManager(Game game) {
        panelNum = 0;
        this.game = game;
        organisms = new ArrayList<>();
        amount = 10;
        idCounter = 1;

        for (int i = 0; i < amount; i++) {
            organisms.add(new Organism(INITIAL_POINT, INITIAL_POINT, ORGANISM_SIZE, ORGANISM_SIZE, game, 0, idCounter++));
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
            organisms.get(i).tick();
            //checkReproduce(organisms.get(i));
            checkKill(organisms.get(i));
        }
        
        panel.tick();
    }

    /**
     * Perform action on mouse clicked
     *
     * @param x
     * @param y
     */
    public void applyMouse(int x, int y) {
        moveSwarm(x, y);
    }

    /**
     * To move the entire swarm to the x and y given
     *
     * @param x
     * @param y
     */
    public void moveSwarm(int x, int y) {
        ArrayList<Point> points;
        //if left clicked move the organisms to determined point

        centralPoint = new Point(x, y);

        points = SwarmMovement.getPositions(centralPoint.x - ORGANISM_SIZE / 2, centralPoint.y - ORGANISM_SIZE / 2, amount);
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setPoint(points.get(i));
        }
    }

    /**
     * to move the swarm to the specified coordinates given there is an object
     * in the middle
     *
     * @param x
     * @param y
     * @param obj
     */
    public void moveSwarm(int x, int y, int obj) {
        ArrayList<Point> points;
        //if left clicked move the organisms to determined point

        centralPoint = new Point(x, y);

        points = SwarmMovement.getPositions(centralPoint.x - ORGANISM_SIZE / 2, centralPoint.y - ORGANISM_SIZE / 2, amount, obj);
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setPoint(points.get(i));
        }
    }

    public void moveSwarmToPoint(int x, int y, int obj) {
        Point p = new Point(x, y);

        for (int i = 0; i < amount; i++) {
            organisms.get(i).setPoint(p);
        }
    }

    /**
     * To check the hover panel over an organism
     */
    public void checkHover() {

        for (int i = 0; i < amount; i++) {
            //if mouse is countained in a certain organism
            if (organisms.get(i).getPerimeter().contains(game.getCamera().getAbsX(game.getMouseManager().getX()),
                    game.getCamera().getAbsY(game.getMouseManager().getY()))) {
                //sets new hover panel with that organism's location and information
                h = new Hover(game.getMouseManager().getX(), game.getMouseManager().getY(), 170, 220,
                        organisms.get(i).getHunger(), organisms.get(i).getThirst(), organisms.get(i).getLife(), game);
                //activates the hover
                setHover(true);
                break;
            } else {
                setHover(false);
            }
        }
    }

    public boolean checkPanel() {
       
        for (int i = 0; i < amount; i++) {
            if (organisms.get(i).getPerimeter().contains(game.getCamera().getAbsX(game.getMouseManager().getX()),
                    game.getCamera().getAbsY(game.getMouseManager().getY()))) {
                if (game.getMouseManager().isLeft() && !game.getSelection().isActive()) {
                    panelNum = i;
                    panel = new OrganismPanel(PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT, game, organisms.get(panelNum));
                    game.getMouseManager().setLeft(false);
                    return true;
                }
            }
        }
        /*
        if(panel.isActive())
            {
                panel.update(organisms.get(panelNum));
                organisms.get(panelNum).setName(panel.getName());
                System.out.println("The name is " + organisms.get(panelNum).getName());
            }
        */
        return false;
    }
    
    public void checkSelection(Rectangle r) {
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setSelected(organisms.get(i).intersects(r));
            System.out.println("SELECTED: " + organisms.get(i).isSelected());
        }
    }

    /**
     * Check if individual organism needs reproduction
     *
     * @param org
     */
    private void checkReproduce(Organism org) {
        if (org.isNeedOffspring()) {
            org.setNeedOffspring(false);
            amount++;
            organisms.add(new Organism(org.getX() + ORGANISM_SIZE, org.getY(), ORGANISM_SIZE, ORGANISM_SIZE, game, org.getSkin(), idCounter++));
            organisms.get(organisms.size()-1).setSearchFood(org.isSearchFood());
            organisms.get(organisms.size()-1).setSearchWater(org.isSearchWater());
        }
    }

    /**
     * Check if an organism needs to be killed
     *
     * @param org
     */
    private void checkKill(Organism org) {
        if (org.isDead()) {
            organisms.remove(org);
            amount--;
        }
    }

    public void setResource(Resource resource) {
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setTarget(resource);
        }
    }

    public void checkOrganismResourceStatus() {
        for (int i = 0; i < amount; i++) {
            Organism org = organisms.get(i);
            Resource target = organisms.get(i).getTarget();
            //Check if target exists
            if (target != null) {
                //Check if the current target is already full and target does not have organism
                if ((target.isFull() && !target.hasParasite(org)) || target.isOver()) {
                    //System.out.println("HEHE CHANGE RESOURCE");
                    
                    if (target.hasParasite(org)) {
                        target.removeParasite(org, i + 1000);
                    }
                    
                    org.setTarget(null);
                    org.setEating(false);
                    org.setDrinking(false);
                    autoLookTarget(org);
                }
            } else {
                org.setEating(false);
                org.setDrinking(false);
                autoLookTarget(org);
            }
        }
    }
    
    public void autoLookTarget(Organism org) {
        if (!org.isConsuming()) {
            Resource plant = findNearestValidFood(org);
            Resource water = findNearestValidWater(org);
            if (org.isSearchFood() && org.isSearchWater()) {
                //Find closest of both
                //System.out.println("FINDING BOTH");
                double distPlant = Math.sqrt(Math.pow(org.getX() - plant.getX(), 2)
                        + Math.pow(org.getY()-plant.getY(), 2));
                double distWater = Math.sqrt(Math.pow(org.getX() - water.getX(), 2)
                        + Math.pow(org.getY()-water.getY(), 2));
                
                if (distPlant < distWater) {
                    org.setTarget(plant);
                } else {
                    org.setTarget(water);
                }
            } else if (org.isSearchFood()) {
                //System.out.println("FINDING FOOD ONLY");
                org.setTarget(plant);
            } else if (org.isSearchWater()) {
                //System.out.println("FINDING WATER ONLY");
                org.setTarget(water);
            } else {
                //System.out.println("FINDING NEITHER");
                //Not looking for anything, idle
                org.setTarget(null);
            }
        } else {
            //System.out.println("AUTOLOOK WHEN CONSUMING");
        }
    }
    
    public Resource findNearestValidFood(Organism org) {
        Resource closestPlant = null; 
        double closestDistanceBetweenPlantAndOrganism = 1000000;
        
        for(int i = 1; i < game.getResources().getPlantAmount(); i++){
            double distanceBetweenPlantAndOrganism = 7072;
            if(!game.getResources().getPlant(i).isFull() && !game.getResources().getPlant(i).isOver()){
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(org.getX()-game.getResources().getPlant(i).getX(),2)
                        + Math.pow(org.getY()-game.getResources().getPlant(i).getY(),2) );
            }
            
            if(distanceBetweenPlantAndOrganism<closestDistanceBetweenPlantAndOrganism){
                closestDistanceBetweenPlantAndOrganism = distanceBetweenPlantAndOrganism;
                closestPlant = game.getResources().getPlant(i);
            }
        }
        
        return closestPlant;
    }
    
    public Resource findNearestValidWater(Organism org) {
        Resource closestWater = null; 
        double closestDistanceBetweenWaterAndOrganism = 1000000;
        
        for(int i = 1; i < game.getResources().getWaterAmount(); i++){
            double distanceBetweenPlantAndOrganism = 7072;
            if(!game.getResources().getWater(i).isFull() && !game.getResources().getWater(i).isOver()){
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(org.getX()-game.getResources().getWater(i).getX(),2)
                        + Math.pow(org.getY()-game.getResources().getWater(i).getY(),2) );
            }
            
            if(distanceBetweenPlantAndOrganism<closestDistanceBetweenWaterAndOrganism){
                closestDistanceBetweenWaterAndOrganism = distanceBetweenPlantAndOrganism;
                closestWater = game.getResources().getWater(i);
            }
        }
        
        return closestWater;
    }

    public void checkArrivalOnResource() {
        for (int i = 0; i < organisms.size(); i++) {
            Organism org = organisms.get(i);
            Resource target = organisms.get(i).getTarget();
            if (target != null) {
                if (target.intersects(org)) {
                    if (!target.isFull()) {
                        if (!target.hasParasite(org)) {
                            target.addParasite(org);
                            //Check the resource type
                            if (target.getType() == Resource.ResourceType.Plant) {
                                org.setEating(true);
                            } else {
                                org.setDrinking(true);
                            }
                        } else {
                            //System.out.println("ORG ALREADY IN TARGET");
                            autoLookTarget(org);
                        }
                    } else {
                        //System.out.println("TARGET FULL");
                        autoLookTarget(org);
                    }
                }
            }
        }
    }

    
    public void emptyTargets() {
        for (int i = 0; i < organisms.size(); i++) {
            Organism org = organisms.get(i);
            Resource target = organisms.get(i).getTarget();
            if (target != null) {
                //System.out.println("REMOVING TARGET ORGMANAGER");
                
                if (org.isConsuming()) {
                    target.removeParasite(org, org.getId() + 5000);
                    org.setEating(false);
                    org.setDrinking(false);
                    //System.out.println("DONE REMOVING TARGET ORGMANAGER");
                }
                org.setTarget(null);
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
            organisms.get(i).render(g);
        }
        //render the hover panel of an organism
        if (h != null && isHover()) {
            h.render(g);

        }
        panel.render(g);
    }

    /**
     * To set the hover status
     *
     * @param hover
     */
    public void setHover(boolean hover) {
        this.hover = hover;
    }

    /**
     * To know if hover is active
     *
     * @return hover
     */
    public boolean isHover() {
        return hover;
    }

    /**
     * Set the skin of the organisms
     *
     * @param skin
     */
    public void setSkin(int skin) {
        this.skin = skin;

        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setSkin(skin);
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
    public ArrayList<Point> getOrganismsPositions() {
        ArrayList<Point> positions = new ArrayList<>();

        for (int i = 0; i < organisms.size(); i++) {
            positions.add(new Point(organisms.get(i).getX(), organisms.get(i).getY()));
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

    public void setSearchFood(boolean val) {
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setSearchFood(val);
        }
    }

    public void setSearchWater(boolean val) {
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setSearchWater(val);
        }
    }
    
    public int getOrganismsAmount() {
        return organisms.size();
    }
    public Organism getOrganism(int i){
        return organisms.get(i);
    }

    /**
     * Single organism class
     */
}
