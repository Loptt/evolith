package evolith.menus;

import evolith.engine.Assets;
import evolith.entities.CampfireManager;
import evolith.entities.Organism;
import evolith.game.Game;
import evolith.helpers.Commons;
import evolith.helpers.FontLoader;
import evolith.helpers.InputReader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class OrganismPanel extends Menu implements Commons {

    private Organism organism;              //Organism that is being displayed in the panel
    private FontLoader f;
    private InputReader inputReader;        //Manages the input keyboard of the name in the panel

    private boolean active;                 //Determines whether the panel is active     
    private boolean searchNext;             //Searches the Next reproductable Species
    private boolean searchPrev;             //Searches the previous reproductable Species
    private boolean reproduce;              //Determines if the reproduce button is pressed
    private int index;                      //Actual index of the organism in the organism manager
    private int timeOpen;
    private boolean tickToWrite;
    private boolean inputActive;
    private boolean campfire;
    
    private CampfireManager campfires;
    

    /**
     * Constructor of the panel initializes the reader and font
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     */
    public OrganismPanel(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        active = false;
        f = new FontLoader();
        inputReader = new InputReader(game);
        inputActive = false;
        
    }

    /**
     * Constructor that activates the display of the organism received
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param org
     */
    public OrganismPanel(int x, int y, int width, int height, Game game, Organism org) {
        super(x, y, width, height, game);
        
        f = new FontLoader();

        this.organism = org;
        //Sets all events to false
        this.searchNext = false;
        this.searchPrev = false;
        this.reproduce = false;
        this.campfire = false;

        this.index = 0;
        //Set display to true
        this.active = true;
        //Close button
        buttons.add(new Button(this.x + this.width - 20, this.y - 20, 40, 40));
        //Edit buttons name
        buttons.add(new Button(this.x + 196, this.y + 281, 190, 35)); // Edit 
        // Arrow next
        buttons.add(new Button(this.x + PANEL_WIDTH + 50, this.y + PANEL_HEIGHT / 2, 50, 50, Assets.nextArrow));
        // Arrow prev
        buttons.add(new Button(this.x - 100, this.y + PANEL_HEIGHT / 2, 50, 50, Assets.prevArrow));
        // Reproduce button 
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 - 150, this.y + 380, 300, 75, Assets.organismPanel_reproduceButton_ON, Assets.organismPanel_reproduceButton_OFF));
        //Name button
        buttons.add(new Button(this.x + 110, this.y + 300, 193, 27));
        //campfire
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 - 100, this.y + 480, 200, 50, Assets.setCampfireOn, Assets.setCampfireOff));
        
        if (this.organism.getName() != null || this.organism.getName() != "") {
            inputReader = new InputReader(this.organism.getName(), game);
        } else {
            inputReader = new InputReader(game);
        }
        this.timeOpen = 0;
        this.tickToWrite = false;
        inputActive = false;
        
        this.campfires = game.getCampfires();
    }

    /**
     * Gets the index of the organism
     *
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index of the organism
     *
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Gets the organism of the actived panel
     *
     * @return organism
     */
    public Organism getOrganism() {
        return organism;
    }

    /**
     * Sets the organism to the new organism
     *
     * @param organism
     */
    public void setOrganism(Organism organism) {
        this.organism = organism;

    }

    /**
     * Ticks the organism panel depending on the info
     */
    @Override
    public void tick() {
        
        //If the panel is not active, do nothing
        if (!active) {
            return;
        }
        //Reads the input
        if (inputActive) {
            game.getInputKeyboard().tick();
        }

        organism.setName(inputReader.getSpeciesName());
        //Checks the mouse positon relative to the button
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                //if the mouse is over the button 
                if(i!=6){
                   buttons.get(i).setActive(true);
                    //if left click change mouse status
                    if (game.getMouseManager().isLeft()) {
                        //Sets the button to the pressed status
                        buttons.get(i).setPressed(true);
                        for (int j = 0; j < buttons.size(); j++) {
                            if (i != j) {
                                buttons.get(j).setPressed(false);
                            }
                        }
                        //Turns off mouse 
                        game.getMouseManager().setLeft(false); 
                        break;
                    }
                }
                else if(!campfires.isCooldown() && organism.getIntelligence() > INT_FOR_CAMP && game.getState() == Game.States.Play
                    && !game.getWeather().getRain().isActive() && !game.getWeather().getStorm().isActive()){
                    buttons.get(i).setActive(true);
                    //if left click change mouse status
                    if (game.getMouseManager().isLeft()) {
                        //Sets the button to the pressed status
                        buttons.get(i).setPressed(true);
                        for (int j = 0; j < buttons.size(); j++) {
                            if (i != j) {
                                buttons.get(j).setPressed(false);
                            }
                        }
                        //Turns off mouse 
                        game.getMouseManager().setLeft(false);
                        break;
                    }
                }
            } else {
                //Sets the button to false if the button is hovered
                buttons.get(i).setActive(false);
            }
        }
        
        //Closes the 
        if (buttons.get(0).isPressed()) {
            active = false;
        }
        //next
        if (buttons.get(2).isPressed()) {
            if (searchNext) {
                buttons.get(2).setPressed(false);
            } else {
                searchNext = true;
            }
        }
        //prev
        if (buttons.get(3).isPressed()) {
            if (searchPrev) {
                buttons.get(3).setPressed(false);
            } else {
                searchPrev = true;
            }
        }
        //reproduce
        if (buttons.get(4).isPressed() && organism.isNeedOffspring()) {

            if (reproduce) {
                buttons.get(4).setPressed(false);
                reproduce = false;
            } else {
                reproduce = true;
                buttons.get(4).setPressed(true);
            }
            active = false;
        }
        
        if (game.getMouseManager().isLeft()) {
            buttons.get(5).setPressed(false);
            game.getMouseManager().setLeft(false);
        }

        if (buttons.get(5).isPressed()) {
            inputActive = true;
            timeOpen++;
            if (this.organism.getName() != null || this.organism.getName() != "") {
                inputReader = new InputReader(this.organism.getName(), game);
            } else {
                inputReader = new InputReader(game);
            }
            if (game.getG().getFontMetrics().stringWidth(inputReader.getSpeciesName()) > 200) {
                inputReader.setOnlyDelete(true);
            } else {
                inputReader.setOnlyDelete(false);
            }

            inputReader.readInput();
        } else {
            inputActive = false;
        }
        
        //campfires
        if (buttons.get(6).isPressed() && game.getState() == Game.States.Play 
                && !game.getWeather().getRain().isActive() && !game.getWeather().getStorm().isActive()) {
            if (campfire) {
                buttons.get(6).setPressed(false);
                campfire = false;
            } else if (organism.getIntelligence() > INT_FOR_CAMP) {
                campfire = true;
                buttons.get(6).setPressed(true);
                int posx = organism.getX();
                int posy = organism.getY();
                if(campfires==null){
                    System.out.println("null in button");
                }
                campfires.addCampfire(posx, posy);
            }
            active = false;
        }
        
        String name = organism.getName();
        
        if (name.length() > 1) {
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            organism.setName(name);
        }
    }
    /**
     * Returns if the search is the previous
     * @return searchPrev
     */
    public boolean isSearchPrev() {
        return searchPrev;
    }
    /**
     * Set the search to the previous
     * @param searchPrev 
     */
    public void setSearchPrev(boolean searchPrev) {
        this.searchPrev = searchPrev;
    }
    /**
     * Check if the organism is reproducible
     * @return reproduce
     */
    public boolean isReproduce() {
        return reproduce;
    }
    /**
     * To set if the organism is reproducible
     * @param reproduce 
     */
    public void setReproduce(boolean reproduce) {
        this.reproduce = reproduce;

    }
    /**
     * To search the next organism
     * @return searchNext
     */
    public boolean isSearchNext() {
        return searchNext;
    }
    /**
     * To set if the next organism is being searched
     * @param searchNext 
     */
    public void setSearchNext(boolean searchNext) {
        this.searchNext = searchNext;
    }
    /**
     * To check if the panel is active
     * @return 
     */
    public boolean isActive() {
        return active;
    }
    /**
     * To set active the panel
     * @param active 
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * To check if the input is active
     * @return inputActive
     */
    public boolean isInputActive() {
        return inputActive;
    }
    
    /**
     * To render the graphics of the panel
     * @param g 
     */
    @Override
    public void render(Graphics g) {

        if (!active) {
            return;
        }

        g.drawImage(Assets.organismPanel_menu, x, y, width, height, null);

        g.drawImage(Assets.orgColors.get(organism.getSkin()), x + 83, y + 70, 196, 196, null);

        int prevSize = organism.getCurrentSize();
        int prevX = organism.getX();
        int prevY = organism.getY();
        organism.setCurrentSize(196);
        organism.setX(game.getCamera().getAbsX(x + 83));
        organism.setY(game.getCamera().getAbsY(y + 70));
        
        organism.getOrgMutations().render(g);
        /*
        for (int i = 0; i < organism.getOrgMutations().getMutations().size(); i++) {
            for (int j = 0; j < organism.getOrgMutations().getMutations().get(i).size(); j++) {
                if (organism.getOrgMutations().getMutations().get(i).get(j).isActive()) {
                    organism.getOrgMutations().getMutations().get(i).get(j).render(g);
                }
            }
        }*/

        organism.setCurrentSize(prevSize);

        organism.setX(prevX);
        organism.setY(prevY);

        g.drawImage(Assets.organismPanel_close, x + width - 20, y - 20, BUTTON_CLOSE_DIMENSION, BUTTON_CLOSE_DIMENSION, null);
        
        g.setColor(Color.CYAN);
        //Speed
        //g.setColor(Color.ORANGE);
        g.fillRect(x + 464, y + 97, (int) 68 * organism.getSpeed() / MAX_SPEED, 20);
        //Max Health
        //g.setColor(Color.CYAN);
        g.fillRect(x + 464, y + 146, (int) 68 * organism.getMaxHealth()/ MAX_SIZE, 20);
        //strength
        //g.setColor(Color.YELLOW);
        g.fillRect(x + 464, y + 197, (int) 68 * organism.getStrength() / MAX_STRENGTH, 20);
        //Stealth
       // g.setColor(Color.MAGENTA);
        g.fillRect(x + 369, y + 97, (int) 68 * organism.getStealth() / MAX_STEALTH, 20);
        //size
        //g.setColor(Color.WHITE);
        g.fillRect(x + 369, y + 146, (int) 68 * organism.getMaxHealth() / MAX_SIZE, 20);
        //maturity
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x + 369, y + 197, (int) 68 * organism.getMaturity() / MAX_MATURITY, 20);
        
        //intelligence
        g.setColor(new Color(255,215,0));
        g.fillRect(x + 375, y + 244, (int) 151 * organism.getIntelligence() / MAX_INTELLIGENCE, 19);
        
        // Edit
        g.setColor(Color.WHITE);
        g.setFont(f.getFontEvolve());
        g.drawString(Integer.toString(organism.getGeneration()), x + 474, y + 286);
        g.drawString(Double.toString(organism.getTime().getSeconds()), x + 458, y + 313);

        g.setColor(Color.WHITE);
        g.setFont(f.getFontEvolve());

        for (int i = 0; i < buttons.size() - 1; i++) {

            if (i != 4 || organism.isNeedOffspring()) {
                buttons.get(i).render(g);
            }
        }
        
        if (!campfires.isCooldown() && organism.getIntelligence() > INT_FOR_CAMP && game.getState() == Game.States.Play
                && !game.getWeather().getRain().isActive() && !game.getWeather().getStorm().isActive()) {
            buttons.get(6).render(g);
        }

        if (timeOpen % 60 == 0) {
            timeOpen = 0;

            tickToWrite = !tickToWrite;
        }

        g.drawString(organism.getName(), x + 125, y + height-45);

        int width = g.getFontMetrics().stringWidth(organism.getName());

        if (tickToWrite && !inputReader.isOnlyDelete() && buttons.get(5).isPressed()) {
            g.drawString("l", x + 125 + width, y + height - 45);
        }
        
        if (inputActive) {
            g.setColor(Color.white);
            g.drawRect(x + 118, y + height - 63, 180, 22);
        }
    }
};
