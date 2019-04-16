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
    
    private int idCounter;

    /**
     * Constructor of the organisms
     *
     * @param game
     */
    public OrganismManager(Game game) {
        this.game = game;
        organisms = new ArrayList<>();
        amount = 1;
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
            checkReproduce(organisms.get(i));
            checkKill(organisms.get(i));
        }

        //check the hover
        checkHover();
        checkPanel();
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
    private void checkHover() {

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

    private void checkPanel() {

        for (int i = 0; i < amount; i++) {
            if (organisms.get(i).getPerimeter().contains(game.getCamera().getAbsX(game.getMouseManager().getX()),
                    game.getCamera().getAbsY(game.getMouseManager().getY()))) {

                if (game.getMouseManager().isLeft()) {
                    int speed = organisms.get(i).getSpeed();
                    int size = organisms.get(i).getSize();
                    int strength = organisms.get(i).getStrength();
                    int stealth = organisms.get(i).getStealth();
                    int survivability = organisms.get(i).getSurvivability();
                    int maturity = organisms.get(i).getMaturity();
                    int generation = organisms.get(i).getGeneration();
                    double duration = organisms.get(i).getTime().getSeconds();
                    String name = organisms.get(i).getName();
                    
                    panel = new OrganismPanel(PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT, speed, size, strength, survivability, stealth, maturity, generation, duration, name, game);
                    game.getMouseManager().setLeft(false);
                }
            }
            if (panel.isActive()) {
                panel.setSpeed(organisms.get(i).getSpeed());
                panel.setSize(organisms.get(i).getSize());
                panel.setStrength(organisms.get(i).getStrength());
                panel.setStealth(organisms.get(i).getStealth());
                panel.setSurvivability(organisms.get(i).getSurvivability());
                panel.setMaturity(organisms.get(i).getMaturity());
                panel.setGeneration(organisms.get(i).getGeneration());
                panel.setDuration(organisms.get(i).getTime().getSeconds());

            }

        }
        panel.tick();
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
                    System.out.println("HEHE CHANGE RESOURCE");
                    org.setTarget(null);
                    org.setEating(false);
                    autoLookNewTarget(org);
                }
            } else {
                org.setEating(false);
                org.setDrinking(false);
                autoLookNewTarget(org);
            }
        }
    }
    
    public void autoLookNewTarget(Organism org) {
        if (!org.isConsuming()) {
            if (org.isSearchFood()) {
                findNearestValidFood(org);
            } else if (org.isSearchWater()) {
                findNearestValidWater(org);
            }
        }
    }
    
    public void findNearestValidFood(Organism org) {
        Resource closestPlant = null; 
        double closestDistanceBetweenPlantAndOrganism = 1000000;
        
        for(int i = 1; i < game.getResources().getPlantAmount(); i++){
            double distanceBetweenPlantAndOrganism = 7072;
            if(!game.getResources().getPlant(i).isFull()){
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(org.getX()-game.getResources().getPlant(i).getX(),2)
                        + Math.pow(org.getY()-game.getResources().getPlant(i).getY(),2) );
            }
            
            if(distanceBetweenPlantAndOrganism<closestDistanceBetweenPlantAndOrganism){
                closestDistanceBetweenPlantAndOrganism = distanceBetweenPlantAndOrganism;
                closestPlant = game.getResources().getPlant(i);
            }
        }
        
        org.setTarget(closestPlant);
    }
    
    public void findNearestValidWater(Organism org) {
        Resource closestWater = null; 
        double closestDistanceBetweenWaterAndOrganism = 1000000;
        
        for(int i = 1; i < game.getResources().getWaterAmount(); i++){
            double distanceBetweenPlantAndOrganism = 7072;
            if(!game.getResources().getWater(i).isFull()){
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(org.getX()-game.getResources().getWater(i).getX(),2)
                        + Math.pow(org.getY()-game.getResources().getWater(i).getY(),2) );
            }
            
            if(distanceBetweenPlantAndOrganism<closestDistanceBetweenWaterAndOrganism){
                closestDistanceBetweenWaterAndOrganism = distanceBetweenPlantAndOrganism;
                closestWater = game.getResources().getWater(i);
            }
        }
        
        org.setTarget(closestWater);
    }

    public void checkArrivalOnResource() {
        for (int i = 0; i < organisms.size(); i++) {
            Organism org = organisms.get(i);
            Resource target = organisms.get(i).getTarget();
            if (target != null) {
                if (target.intersects(org) && !target.isFull() && !target.hasParasite(org)) {
                    target.addParasite(org);
                    //Check the resource type
                    if (target.getType() == Resource.ResourceType.Plant) {
                        org.setEating(true);
                    } else {
                        org.setDrinking(true);
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
                System.out.println("Removing targets in org manager");
                target.removeParasite(org);
                org.setTarget(null);
            }
        }
    }
    
    /*
    public void checkProximity(Plants plants) {
        for (int i = 0; i < amount; i++) {
            if (plants.checkRadius(organisms.get(i).getRadius(),i) && !organisms.get(i).isInPlant()) {
                System.out.println("CLOSE");
                organisms.get(i).setPoint(currentPoss.get(0));
                organisms.get(i).setInPlant(true);
                currentPoss.remove(0);
            }
        }
    }*/
    /*
    public void checkOnResource(ResourceManager resources) {
        for (int i = 0; i < amount; i++) {
            Item target = organisms.get(i).getTarget();
            Organism org = organisms.get(i);
            if (target != null) {
                if (target.intersects(org.getPerimeter()) && !org.isEating()) {
                    ((Plant) target).addParasite(org);
                    org.setEating(true);
                }
            }
        }
    }
    
    public void setResource(Item item) {
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setTarget(item);
        }
    }*/
    
    /*
    public void checkProximity(Waters waters) {
        for (int i = 0; i < amount; i++) {
            if (waters.checkRadius(organisms.get(i).getRadius(),i) && !organisms.get(i).isInWater()) {
                System.out.println("CLOSE");
                organisms.get(i).setPoint(currentPoss.get(0));
                organisms.get(i).setInWater(true);
                currentPoss.remove(0);
            }
        }
    }*/

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
    /*
    public void checkIfTargetValid(ResourceManager resources) {
        for (int i = 0; i < amount; i++) {
            if (organisms.get(i).getTarget() != null && ((Plant)organisms.get(i).getTarget()).isFull()) {
                if (organisms.get(i).isSearchFood()) {
                    findNearestValidFood(organisms.get(i), resources);
                }
            } else if (organisms.get(i).getTarget() == null) {
                if (organisms.get(i).isSearchFood()) {
                    findNearestValidFood(organisms.get(i), resources);
                }
            }
        }
    }
    
    public void findNearestValidFood(Organism org, ResourceManager resources) {
        Plant closestWater = resources.getPlant(0); 
        double closestDistanceBetweenWaterAndOrganism = Math.sqrt(Math.pow(org.getX()-resources.getPlant(0).getX(),2) + Math.pow(org.getY()-resources.getPlant(0).getY(),2) );
        for(int i = 1; i<resources.getPlantsAmount(); i++){
            double distanceBetweenPlantAndOrganism = 7072;
            if(!resources.getPlant(i).isFull()){
                distanceBetweenPlantAndOrganism = Math.sqrt(Math.pow(org.getX()-resources.getPlant(i).getX(),2) + Math.pow(org.getY()-resources.getPlant(i).getY(),2) );
            }
            
            if(distanceBetweenPlantAndOrganism<closestDistanceBetweenPlantAndOrganism){
                closestDistanceBetweenWaterAndOrganism = distanceBetweenPlantAndOrganism;
                closestWater = resources.getPlant(i);
            }
        }
        
        org.setTarget(closestWater);
    }
*/
    /**
     * Single organism class
     */
}