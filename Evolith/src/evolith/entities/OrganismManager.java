package evolith.entities;

import evolith.database.JDBC;
import evolith.game.Game;
import evolith.menus.Hover;
import evolith.helpers.SwarmMovement;
import evolith.helpers.Commons;
import evolith.menus.MutationPanel;

import evolith.menus.OrganismPanel;
import evolith.menus.StatisticsPanel;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
    private ArrayList<Organism> deadOrgs;

    private Game game;                      // game instance

    private Hover h;                        // hover panel
    private boolean hover;                  // to know if hovering

    private int skin;                       //skin id of the organisms

    private OrganismPanel orgPanel;         //info panel 
    private MutationPanel mutPanel;         //Mutation panel
    private StatisticsPanel statsPanel;     //Statistics panel

    private int panelIndex;                 //Current index in panel
    private int idCounter;                  //Counter to assign ids
    private String speciesName;             //Name of the species
    private int speciesID;                  //ID of the speces
    
    private boolean updatedNight;           //Updated night state
    private int avg[];                      //Average
    private JDBC mysql;                     //MySql connection class
    private int maxIntelligence;            //Max intelligence of organisms
    private int maxGeneration;              //Max generation of organisms

    private boolean other;                  //State indicating if organisms belong to opponent


    /**
     * Constructor of the organisms
     *
     * @param game game object
     * @param other other flag
     */
    public OrganismManager(Game game, boolean other) {
        panelIndex = 0;
        this.game = game;
        this.other = other;
        organisms = new ArrayList<>();
        deadOrgs = new ArrayList<>();
        int amount = 1;
        idCounter = 1;
        maxGeneration = 1;

        for (int i = 0; i < amount; i++) {
            organisms.add(new Organism(INITIAL_POINT, INITIAL_POINT, ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, game, 0, idCounter++, other));
            organisms.get(i).setEgg(false);
            organisms.get(i).setBorn(true);
            organisms.get(i).setMaturity(100);
        }
       
        orgPanel = new OrganismPanel(0, 0, 0, 0, this.game);
        mutPanel = new MutationPanel(0, 0, 0, 0, this.game);
        
        updatedNight = false;
        speciesName = "";
        avg = new int[4];
        this.mysql =  game.getMysql();
        //this.speciesID = mysql.getSpeciesID( game.getGameID());
        this.maxIntelligence = 0;
        statsPanel = new StatisticsPanel(PANEL_STATS_X,PANEL_STATS_Y,0,0,game,false,true,207,250);
        }
    
    /**
     * updates all organisms
     */
    public void tick() {
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).tick();
            checkNeedMutation(organisms.get(i));
            checkGeneration(organisms.get(i));
        }
      
        for (int i = 0; i < deadOrgs.size(); i++) {
            Organism org = deadOrgs.get(i);
            org.tick();
            
            if (org.isAnimationDone()) {
                deadOrgs.remove(org);
            }
        }
        checkNight();
        updateMenuPanels();
    }
    
    /**
     * To calculate the average of organisms stats
     */
    public void calculateAverage() {
        for (int i = 0; i < organisms.size(); i++) {
            avg[0] += organisms.get(i).getSpeed();
            avg[1] += organisms.get(i).getStealth();
            avg[2] += organisms.get(i).getStrength();
            avg[3] += organisms.get(i).getMaxHealth();
        }

        if (!organisms.isEmpty()) {
            avg[0] /= organisms.size();
            avg[1] /= organisms.size();
            avg[2] /= organisms.size();
            avg[3] /= organisms.size();
            statsPanel.setSpeed(avg[0]);
            statsPanel.setStealth(avg[1]);
            statsPanel.setStrength(avg[2]);
            statsPanel.setHealth(avg[3]);
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
     * Select organisms which are in the specified rectangle
     * @param r Rectangle to check
     */
    public void selectInRect(Rectangle r) {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).intersects(r) && !organisms.get(i).isEgg()) {
                organisms.get(i).setSelected(true);
            } else {
                organisms.get(i).setSelected(false);
            }
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
        } else if(statsPanel.isActive())
        {
            calculateAverage();
            statsPanel.tick();
            setHover(false);
        }
        else {
            checkHover();
        }
    }
    
    /**
     * Check if night changes need to be applied
     */
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
                    game.getCamera().getAbsY(game.getMouseManager().getY())) && !organisms.get(i).isEgg() && !organisms.get(i).isOther()) {
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
    
    /**
     * get the absolute mod in the for a mod b
     * @param a dividend
     * @param b divisor
     * @return real mod of a % b
     */
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
     * In multiplayer, check if the opponent organisms are visible
     */
    public void checkOtherVisible() {
        OrganismManager others = game.getOtherOrganisms();
        
        if (others == null) {
            return;
        }
        for (int i = 0; i < others.getAmount(); i++) {
            Organism org = others.getOrganism(i);
            org.setVisible(false);
            
            for (int j = 0; j < organisms.size(); j++) {
                Organism thisOrg = organisms.get(j);
                
                if (SwarmMovement.distanceBetweenTwoPoints(thisOrg.getX(), thisOrg.getY(), org.getX(), org.getY()) < MAX_SIGHT_DISTANCE + 350) {
                    org.setVisible(true);
                }
            }
        }
    }

    /**
     * Check which organisms are in the selected area and toggle them
     *
     * @param r the selection area
     */
    public void checkSelection(Rectangle r) {
        for (int i = 0; i < organisms.size(); i++) {
            if (!organisms.get(i).isEgg()) {
                organisms.get(i).setSelected(organisms.get(i).intersects(r));
            }
        }
    }
    
    /**
     * Reproduce an organism by creating another identical organism
     * @param org Organism to reproduce 
     */
    private void reproduce(Organism org) {
        
        Organism offspring;

        offspring = org.cloneOrg();
        
      //  if((orgPanel.isReproduce() && mutPanel.getButtons().get(0).isPressed()) || (orgPanel.isReproduce() && !mutPanel.isActive()) ){
        orgPanel.setReproduce(false);
        offspring.setId(idCounter-1);
        organisms.add(offspring);
        offspring.setSearchFood(org.isSearchFood());
        offspring.setSearchWater(org.isSearchWater());
        offspring.setIntelligence(offspring.getIntelligence() + 15);

        org.setNeedOffspring(false);
        mysql.insertOrganism(speciesID,offspring.isDead() ? 1 : 0, offspring.getGeneration(), offspring.getSpeed(), offspring.getStealth(), offspring.getStrength(), offspring.getMaxHealth());   
    }
    
    /**
     * Update organisms in the database
     */
    private void updateOrganismsDB() {
            mysql.updateOrganisms(this);
    }
  
    /**
     * Check if an organism needs to be killed
     */
    public void checkKill() {
        for (int i = 0; i < organisms.size(); i++) {
            Organism org = organisms.get(i);
            if (org.isDead()) {
                updateOrganismsDB();
                organisms.remove(org);
                deadOrgs.add(org);
            }
        }
    }
    
    /**
     * check if the organism needs to be mutated
     * @param org Organism to check
     */
    private void checkNeedMutation(Organism org) {
        if (org.isNeedMutation()) {
            mutPanel = new MutationPanel(org, MUTATION_PANEL_X, MUTATION_PANEL_Y, MUTATION_PANEL_WIDTH, MUTATION_PANEL_HEIGHT, game);
            orgPanel.setActive(false);
            mutPanel.setActive(true);
            org.setNeedMutation(false);
        }
    }
    
    /**
     * Get the most intelligent organism of the species
     * @return most intelligent organism
     */
    public Organism getMostIntelligent() {
        Organism mostInt;
        if (organisms.isEmpty()) {
            return null;
        }
        
        mostInt = organisms.get(0);
        
        for (int i = 1; i < organisms.size(); i++) {
            if (organisms.get(i).getIntelligence() > mostInt.getIntelligence() && !organisms.get(i).isEgg()) {
                mostInt = organisms.get(i);
            }
        }
        
        return mostInt;
    }

    /**
     * sets a resource for selected organisms
     *
     * @param resource resource to set
     */
    public void setSelectedResource(Resource resource) {
        for (int i = 0; i < organisms.size(); i++) {
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
     * @param value state to set the seletec organisms
     */
    public void setSelectedGodCommand(boolean value) {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).isSelected()) {
                organisms.get(i).setGodCommand(value);
            }
        }
    }
    
    /**
     * Deselect all organisms
     */
    public void clearSelection() {
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setSelected(false);
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
     * Check if one organisms has reached max intelligence
     * @return true if at least one organisms has max intelligence
     */
    public boolean isMaxIntelligence() {
        for (int i = 0; i < organisms.size(); i++) {
            if(maxIntelligence < organisms.get(i).getIntelligence())
            {
                maxIntelligence = organisms.get(i).getIntelligence();
            }
            if (organisms.get(i).getIntelligence() >= MAX_INTELLIGENCE) {
                return true;
            }
        }
        
        return false;
    }

    
    /**
     * Add an organism to the array
     * @param org Organism to add
     */
    public void addOrganism(Organism org) {
        organisms.add(org);
    }
    
    /**
     * Save the current state of the organisms to the print writer
     * @param pw print writer
     */
    public void save(PrintWriter pw) {
        /*
        Save organisms but drop if 
        */
        mysql.updateOrganisms(this);
        //mysql.saveOrganisms(this);
        //Save amount
        pw.println(Integer.toString(organisms.size()));
        
        //Skin
        pw.println(Integer.toString(skin));
        
        //Save each organism
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).save(pw);
        }
    }
    
    /**
     * Load the last save state of the organisms from the buffered reader
     * @param br buffered reader
     * @throws IOException 
     */
    public void load(BufferedReader br) throws IOException {
        /*
        Update table the organisms from backup_organism
        */
        
        
        int am = Integer.parseInt(br.readLine());
        organisms.clear();
        skin = Integer.parseInt(br.readLine());
        for (int i = 0; i < am; i++) {
            organisms.add(new Organism(0,0, ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, game, skin, idCounter++, other));
            organisms.get(i).load(br);
            mysql.insertOrganism(speciesID , 1 ,organisms.get(i).getGeneration(),organisms.get(i).getSpeed(),organisms.get(i).getStealth() , organisms.get(i).getStrength(),organisms.get(i).getMaxHealth());
        }
    }
    
    /**
     * Reset organisms to its initial state
     */
    public void reset() {
        organisms.clear();
        
        idCounter = 1;
        
        //If the game is mutliplayer, check where to position the first organism
        if (game.getState() == Game.States.Multi) {
            if (game.isServer() && !other) {
                organisms.add(new Organism(INITIAL_POINT_HOST, INITIAL_POINT_HOST, ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, game, 0, idCounter++, other));
                organisms.get(0).setSkin(skin);
            } else if (!game.isServer() && !other) {
                organisms.add(new Organism(INITIAL_POINT_CLIENT, INITIAL_POINT_CLIENT, ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, game, 0, idCounter++, other));
                organisms.get(0).setSkin(skin);
            } else if (game.isServer() && other) {
                organisms.add(new Organism(INITIAL_POINT_CLIENT, INITIAL_POINT_CLIENT, ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, game, 0, idCounter++, other));
                organisms.get(0).setSkin(skin);
            } else {
                organisms.add(new Organism(INITIAL_POINT_HOST, INITIAL_POINT_HOST, ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, game, 0, idCounter++, other));
                organisms.get(0).setSkin(skin);
            }
        } else {
            organisms.add(new Organism(INITIAL_POINT, INITIAL_POINT, ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, game, 0, idCounter++, other));
        }
        
        organisms.get(0).setEgg(false);
        organisms.get(0).setBorn(true);
        organisms.get(0).setMaturity(100);

        orgPanel = new OrganismPanel(0, 0, 0, 0, this.game);
        mutPanel = new MutationPanel(0, 0, 0, 0, this.game);
        
        updatedNight = false;
        speciesName = "";
        
    }

    /**
     * To render the organisms
     *
     * @param g graphics
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

        statsPanel.render(g);

        for (int i = 0; i < deadOrgs.size(); i++) {
            deadOrgs.get(i).render(g);
        }

    }

    /**
     * To set the hover status
     *
     * @param hover hover state
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
     * @param skin new skin
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
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setSearchFood(val);
        }
    }

    /**
     * set searchFood for selected organisms
     *
     * @param val boolean to be assigned
     */
    public void setSelectedSearchFood(boolean val) {
        for (int i = 0; i < organisms.size(); i++) {
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
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setSearchWater(val);
        }
    }

    /**
     * set searchWater for selected organisms
     *
     * @param val boolean to be assigned
     */
    public void setSelectedSearchWater(boolean val) {
        for (int i = 0; i < organisms.size(); i++) {
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
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setAggressive(val);
        }
    }

    /**
     * set aggressiveness for selected organisms
     *
     * @param val boolean to be assigned
     */
    public void setSelectedAggressiveness(boolean val) {
        for (int i = 0; i < organisms.size(); i++) {
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
     * to check if statsPanel is active
     * @return mutPanel.isActive()
     */
    public boolean isStatsPanelActive() {
        return statsPanel.isActive();
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

    /**
     * to get current organism amount
     * @return amount of organisms
     */
    public int getAmount() {
        return organisms.size();
    }

    /**
     * to get the id counter
     * @return idCounter
     */
    public int getIdCounter() {
        return idCounter;
    }

    /**
     * to set the idCounter
     * @param idCounter new idCounter
     */
    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }

    /**
     * to get the species name
     * @return speciesName
     */
    public String getSpeciesName() {
        return speciesName;
    }

    /**
     * to set the species name
     * @param speciesName name
     */
    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    /**
     * to check if organisms have been updated to night
     * @return updatedNight
     */
    public boolean isUpdatedNight() {
        return updatedNight;
    }

    /**
     * to set updatedNight
     * @param updatedNight updated night state
     */
    public void setUpdatedNight(boolean updatedNight) {
        this.updatedNight = updatedNight;
    }
    
    /**
     * to get the species id
     * @return speciedID
     */
    public int getSpeciesID() {
        return speciesID;
    }
    
    /**
     * to set the species id
     * @param speciesID 
     */
    public void setSpeciesID(int speciesID) {
        this.speciesID = speciesID;
    }
    
    /**
     * to get max intelligence
     * @return max intelligence
     */
    public int getMaxIntelligence() {
        return maxIntelligence;
    }
    
    /**
     * to set max intelligence
     * @param maxIntelligence new max intelligence
     */
    public void setMaxIntelligence(int maxIntelligence) {
        this.maxIntelligence = maxIntelligence;
    }
    
    /**
     * to check if generation is updated
     * @param o organism to check
     */
    private void checkGeneration(Organism o) {
        if(o.getGeneration()>= maxGeneration)
            maxGeneration = o.getGeneration();
    }
    
    /**
     * get max generation
     * @return maxGeneration
     */
    public int getMaxGeneration() {
        return maxGeneration;
    }
    
    /**
     * to get stats panel
     * @return statsPanel
     */
    public StatisticsPanel getStatsPanel() {
        return statsPanel;
    }
    
    /**
     * to set the stats panel
     * @param statsPanel new stats panel
     */
    public void setStatsPanel(StatisticsPanel statsPanel) {
        this.statsPanel = statsPanel;
    }
    
    /**
     * to get an average
     * @return avg
     */
    public int[] getAvg() {
        return avg;
    }
    
    /**
     * to get the game object
     * @return game object
     */
    public Game getGame() {
        return game;
    }

    /**
     * to get the hover object
     * @return h
     */
    public Hover getH() {
        return h;
    }
}
