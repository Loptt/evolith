package evolith.entities;

import evolith.game.Game;
import evolith.menus.Hover;
import evolith.helpers.SwarmMovement;
import evolith.helpers.Commons;
import evolith.menus.MutationPanel;

import evolith.menus.OrganismPanel;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;

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

    private Hover h;            // hover panel
    private boolean hover;      // to know if hovering

    private int skin;           //skin id of the organisms

    private OrganismPanel orgPanel; //info panel 
    private MutationPanel mutPanel; //Mutation panel

    private int panelIndex;
    private int idCounter;
    private String speciesName;
    
    private boolean updatedNight;

    /**
     * Constructor of the organisms
     *
     * @param game
     */
    public OrganismManager(Game game) {
        panelIndex = 0;
        this.game = game;
        organisms = new ArrayList<>();
        amount = 1;
        idCounter = 1;

        for (int i = 0; i < amount; i++) {
            organisms.add(new Organism(INITIAL_POINT, INITIAL_POINT, ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, game, 0, idCounter++));
        }
        
        organisms.get(0).setEgg(false);
        organisms.get(0).setBorn(true);

        orgPanel = new OrganismPanel(0, 0, 0, 0, this.game);
        mutPanel = new MutationPanel(0, 0, 0, 0, this.game);
        
        updatedNight = false;
        speciesName = "";
    }

    /**
     * updates all organisms
     */
    public void tick() {
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).tick();
            checkNeedMutation(organisms.get(i));
            checkKill(organisms.get(i));
        }
        
        checkNight();
        updateMenuPanels();
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

        if (organisms.size() > 0) {
            points = SwarmMovement.getPositions(x, y, organisms.size());
            for (int i = 0; i < organisms.size(); i++) {
                organisms.get(i).setPoint(points.get(i));
            }
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

        points = SwarmMovement.getPositions(x - ORGANISM_SIZE_STAT / 2, y - ORGANISM_SIZE_STAT / 2, amount, obj);
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setPoint(points.get(i));
        }
    }

    /**
     * move the selected organisms to an indicated x and y coordinate
     *
     * @param x coordinate in the x axis
     * @param y coordinate in the y axis
     */
    public void moveSelectedSwarm(int x, int y) {
        ArrayList<Point> points;

        int count = 0;

        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).isSelected()) {
                count++;
            }
        }

        if (count <= 0) {
            return;
        }

        points = SwarmMovement.getPositions(x - ORGANISM_SIZE_STAT / 2, y - ORGANISM_SIZE_STAT / 2, count);

        int pointIndex = 0;

        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).isSelected()) {
                organisms.get(i).setPoint(points.get(pointIndex++));
            }
        }
    }

    /**
     * deprecated
     *
     * @param x
     * @param y
     * @param obj
     */
    public void moveSwarmToPoint(int x, int y, int obj) {
        Point p = new Point(x, y);

        for (int i = 0; i < amount; i++) {
            organisms.get(i).setPoint(p);
        }
    }
    
    /**
     * update all menu panels of the organisms
     */
    private void updateMenuPanels() {
        if (orgPanel.isActive()) {
            orgPanel.tick();
            setHover(false);

            if (orgPanel.isSearchNext() || orgPanel.isSearchPrev()) {
                for (int i = 0; i < organisms.size(); i++) {
                    if (orgPanel.getOrganism() == organisms.get(i)) {
                        orgPanel.setIndex(i);
                        panelIndex = i;
                    }
                }

                while (orgPanel.isSearchNext() || orgPanel.isSearchPrev()) {
                    if (orgPanel.isSearchNext()) {
                        int auxIndex = orgPanel.getIndex();
                        orgPanel.setIndex(++auxIndex % organisms.size());
                        //panel.setIndex((panel.getIndex()+1) % organisms.size());
                    }

                    if (orgPanel.isSearchPrev()) {
                        int auxIndex = orgPanel.getIndex();
                        orgPanel.setIndex(realMod(--auxIndex, organisms.size()));
                    }

                    if (panelIndex == orgPanel.getIndex() || organisms.get(orgPanel.getIndex()).isNeedOffspring()) {

                        panelIndex = orgPanel.getIndex();
                        orgPanel.setOrganism(organisms.get(panelIndex));
                        orgPanel.setSearchNext(false);
                        orgPanel.setSearchPrev(false);
                    }
                }
            }

            if (orgPanel.isReproduce()) {
                reproduce(orgPanel.getOrganism());
            }   
        } else if (mutPanel.isActive()) {
            mutPanel.tick();
            setHover(false);
        } else {
            checkHover();
        }
    }
    
    private void checkNight() {
        if (game.isNight()) {
            if (!updatedNight) {
                for (int i = 0; i < organisms.size(); i++) {
                    organisms.get(i).setStealthRange(organisms.get(i).getStealthRange() - 100);
                }
                updatedNight = true;
            }
        } else if (updatedNight) {
            for (int i = 0; i < organisms.size(); i++) {
                organisms.get(i).setStealthRange(organisms.get(i).getStealthRange() + 100);
            }
            
            updatedNight = false;
        }
    }

    /**
     * To check the hover panel over an organism
     */
    public void checkHover() {

        for (int i = 0; i < organisms.size(); i++) {
            //if mouse is countained in a certain organism
            if (organisms.get(i).getPerimeter().contains(game.getCamera().getAbsX(game.getMouseManager().getX()),
                    game.getCamera().getAbsY(game.getMouseManager().getY())) && !organisms.get(i).isEgg()) {
                //sets new hover panel with that organism's location and information
                h = new Hover(game.getMouseManager().getX(), game.getMouseManager().getY(), 170, 220,
                        organisms.get(i).getHunger(), organisms.get(i).getThirst(), organisms.get(i).getLife(), organisms.get(i).getCurrentMaxHealth(),
                        game, organisms.get(i));
                //activates the hover
                setHover(true);
                break;
            } else {
                setHover(false);
            }
        }
    }

    private int realMod(int a, int b) {
        return ((((a % b) + b) % b) + b) % b;
    }
    
    /**
     * check if orgPanel is activated
     * @return true if orgPanel has been activated, false otherwise
     */
    public boolean checkPanel() {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getPerimeter().contains(game.getCamera().getAbsX(game.getMouseManager().getX()),
                    game.getCamera().getAbsY(game.getMouseManager().getY())) && !organisms.get(i).isEgg()) {
                if (game.getMouseManager().isLeft()) { //Unnecessary if statement, but ok
                    orgPanel = new OrganismPanel(PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT, game, organisms.get(i));
                    orgPanel.setIndex(panelIndex);
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * Check which organisms are in the selected area and toggle them
     *
     * @param r the selection area
     */
    public void checkSelection(Rectangle r) {
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setSelected(organisms.get(i).intersects(r));
        }
    }
    
    private void reproduce(Organism org) {
        
        Organism offspring = new Organism(org.getX() + ORGANISM_SIZE_STAT, org.getY(), ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, game, org.getSkin(), idCounter++);
        //generate an int for a chance of mutation
        int mutationChance = (int) (Math.random() * (2 - 0));
        //if it should not mutate

        offspring = org.cloneOrg();
        
      //  if((orgPanel.isReproduce() && mutPanel.getButtons().get(0).isPressed()) || (orgPanel.isReproduce() && !mutPanel.isActive()) ){
        amount++;
        orgPanel.setReproduce(false);
        offspring.setId(idCounter + 1);
        idCounter++;
        organisms.add(offspring);
        org.setNeedOffspring(false);
        
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
    
    private void checkNeedMutation(Organism org) {
        if (org.isNeedMutation()) {
            mutPanel = new MutationPanel(org, MUTATION_PANEL_X, MUTATION_PANEL_Y, MUTATION_PANEL_WIDTH, MUTATION_PANEL_HEIGHT, game);
            orgPanel.setActive(false);
            mutPanel.setActive(true);
            org.setNeedMutation(false);
        }
    }

    /**
     * sets a resource for all organisms (deprecated)
     *
     * @param resource
     */
    public void setResource(Resource resource) {
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setTarget(resource);
        }
    }

    /**
     * sets a resource for selected organisms
     *
     * @param resource
     */
    public void setSelectedResource(Resource resource) {
        for (int i = 0; i < amount; i++) {
            if (organisms.get(i).isSelected()) {
                organisms.get(i).setTarget(resource);
            }
        }
    }

    /**
     * empty the resource target for all organisms
     */
    public void emptyTargets() {
        for (int i = 0; i < organisms.size(); i++) {
            Organism org = organisms.get(i);
            org.safeLeaveResource();
        }
    }

    /**
     * empty the resource target for selected organisms
     */
    public void emptySelectedTargets() {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).isSelected()) {
                Organism org = organisms.get(i);
                org.safeLeaveResource();
            }
        }
    }

    /**
     * set the god command to selected organisms
     *
     * @param value
     */
    public void setSelectedGodCommand(boolean value) {
        for (int i = 0; i < amount; i++) {
            if (organisms.get(i).isSelected()) {
                organisms.get(i).setGodCommand(value);
            }
        }
    }

    /**
     * get if at least one form selection has activeFood
     *
     * @return true if at least one has searchFood active, false otherwise
     */
    public boolean selectionHasActiveFood() {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).isSearchFood() && organisms.get(i).isSelected()) {
                return true;
            }
        }

        return false;
    }

    /**
     * get if at least one form selection has activeWater
     *
     * @return true if at least one has searchWater active, false otherwise
     */
    public boolean selectionHasActiveWater() {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).isSearchWater() && organisms.get(i).isSelected()) {
                return true;
            }
        }

        return false;
    }

    /**
     * get if at least one form selection has aggressiveness
     *
     * @return true if at least one has aggressiveness active, false otherwise
     */
    public boolean selectionHasAggressiveness() {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).isAggressive() && organisms.get(i).isSelected()) {
                return true;
            }
        }

        return false;
    }

    /**
     * To render the organisms
     *
     * @param g
     */
    public void render(Graphics g) {
        
        HashSet<Organism> eggs = new HashSet<>();

        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).isEgg()) {
                eggs.add(organisms.get(i));
            }
        }
        
        for (Organism org: eggs) {
            org.render(g);
        }
        
        for (int i = 0; i < organisms.size(); i++) {
            if (!organisms.get(i).isEgg()) {
                organisms.get(i).render(g);
            }
        }
        //render the hover panel of an organism

        if (!orgPanel.isActive() || !mutPanel.isActive()) {
            if (h != null && isHover()) {
                h.render(g);
            }
        }
        
        //Handle orgPanel and mutPanel render in game to prevent other elements
        //to overlap them
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
     * set searchFood for all organisms
     *
     * @param val boolean to be assigned
     */
    public void setSearchFood(boolean val) {
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setSearchFood(val);
        }
    }

    /**
     * set searchFood for selected organisms
     *
     * @param val boolean to be assigned
     */
    public void setSelectedSearchFood(boolean val) {
        for (int i = 0; i < amount; i++) {
            if (organisms.get(i).isSelected()) {
                organisms.get(i).setSearchFood(val);
            }
        }
    }

    /**
     * set searchWater for all organisms
     *
     * @param val boolean to be assigned
     */
    public void setSearchWater(boolean val) {
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setSearchWater(val);
        }
    }

    /**
     * set searchWater for selected organisms
     *
     * @param val boolean to be assigned
     */
    public void setSelectedSearchWater(boolean val) {
        for (int i = 0; i < amount; i++) {
            if (organisms.get(i).isSelected()) {
                organisms.get(i).setSearchWater(val);
            }
        }
    }

    /**
     * set aggressiveness for all organisms
     *
     * @param val boolean to be assigned
     */
    public void setAggressiveness(boolean val) {
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setAggressive(val);
        }
    }

    /**
     * set aggressiveness for selected organisms
     *
     * @param val boolean to be assigned
     */
    public void setSelectedAggressiveness(boolean val) {
        for (int i = 0; i < amount; i++) {
            if (organisms.get(i).isSelected()) {
                organisms.get(i).setAggressive(val);
            }
        }
    }

    /**
     * to get the organisms amount
     *
     * @return the <code>ArrayList</code> size
     */
    public int getOrganismsAmount() {
        return organisms.size();
    }

    /**
     * to get a specific organism
     *
     * @param i index of the array
     * @return organism at index i
     */
    public Organism getOrganism(int i) {
        return organisms.get(i);
    }
    
    /**
     * to check if orgPanel is active
     * @return orgPanel.isActive()
     */
    public boolean isOrgPanelActive() {
        return orgPanel.isActive();
    }
    
    /**
     * to check if mutPanel is active
     * @return mutPanel.isActive()
     */
    public boolean isMutPanelActive() {
        return mutPanel.isActive();
    }

    /**
     * to get the orgPanel
     * @return orgPanel
     */
    public OrganismPanel getOrgPanel() {
        return orgPanel;
    }
    
    /**
     * to get the mutPanel
     * @return mutPanel
     */
    public MutationPanel getMutPanel() {
        return mutPanel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public boolean isUpdatedNight() {
        return updatedNight;
    }

    public void setUpdatedNight(boolean updatedNight) {
        this.updatedNight = updatedNight;
    }
    
}
