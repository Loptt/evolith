package evolith.menus;

import evolith.engine.Assets;
import evolith.entities.Organism;
import evolith.game.Game;
import evolith.helpers.Commons;
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
 *
 * @author ErickFrank
 */
public class OrganismPanel extends Menu implements Commons {

    private Organism organism;              //Organism that is being displayed in the panel

    private String fontPath;                //Path to where the font is located
    private Font fontEvolve;                //Font used in the organism panel
    private InputStream is;                 //Manages the input of the name in the panel

    private InputReader inputReader;        //Manages the input keyboard of the name in the panel

    private boolean active;                 //Determines whether the panel is active     
    private boolean searchNext;             //Searches the Next reproductable Species
    private boolean searchPrev;             //Searches the previous reproductable Species
    private boolean reproduce;              //Determines if the reproduce button is pressed
    private int index;                      //Actual index of the organism in the organism manager

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
        fontPath = "/Fonts/MADE-Evolve-Sans-Regular.ttf";
        this.is = OrganismPanel.class.getResourceAsStream(fontPath);
        try {
            fontEvolve = Font.createFont(Font.TRUETYPE_FONT, is);
            fontEvolve = fontEvolve.deriveFont(20f);
        } catch (FontFormatException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        inputReader = new InputReader(game);
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

        this.organism = org;
        //Sets all events to false
        this.searchNext = false;
        this.searchPrev = false;
        this.reproduce = false;

        this.index = 0;
        //Set display to true
        this.active = true;
        //Close button
        buttons.add(new Button(this.x + this.width - 20, this.y - 20, 40, 40));
        //Edit buttons name
        buttons.add(new Button(this.x + 196, this.y + 281, 190, 35)); // Edit 
        // Arrow next
        buttons.add(new Button(this.x + PANEL_WIDTH + 50, this.y + PANEL_HEIGHT / 2, 50, 50, Assets.organismPanel_nextArrow));
        // Arrow prev
        buttons.add(new Button(this.x - 100, this.y + PANEL_HEIGHT / 2, 50, 50, Assets.organismPanel_prevArrow));
        // Reproduce button 
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 - 150, this.y + 400, 300, 75, Assets.organismPanel_reproduceButton_ON,Assets.organismPanel_reproduceButton_OFF));

        inputReader = new InputReader(game);
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
        /*if(organism.isDead())
        {
            active = false;
        }*/
        //Reads the input
        inputReader.readInput();
        //If the panel is not active, do nothing
        if (!active) {
            return;
        }
        //Checks the mouse positon relative to the button
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                //if the mouse is over the button 
                buttons.get(i).setActive(true);
                //if left click change mouse status
                if (game.getMouseManager().isLeft()) {
                    //Sets the button to the pressed status
                    buttons.get(i).setPressed(true);
                    //Turns off mouse 
                    game.getMouseManager().setLeft(false);
                }
            } else {
                //Sets the button to false if the button is hovered
                buttons.get(i).setActive(false);
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
            }

        }
    }

    public boolean isSearchPrev() {
        return searchPrev;
    }

    public void setSearchPrev(boolean searchPrev) {
        this.searchPrev = searchPrev;
    }

    public boolean isReproduce() {
        return reproduce;
    }

    public void setReproduce(boolean reproduce) {
        this.reproduce = reproduce;

    }

    public boolean isSearchNext() {
        return searchNext;
    }

    public void setSearchNext(boolean searchNext) {
        this.searchNext = searchNext;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }

    @Override
    public void render(Graphics g) {

        if (active) {

            g.drawImage(Assets.organismPanel_menu, x, y, width, height, null);

            g.drawImage(Assets.orgColors.get(organism.getSkin()), x + 83, y + 70, 196, 196, null);

            for (int i = 0; i < organism.getOrgMutations().getMutations().size(); i++) {
                for (int j = 0; j < organism.getOrgMutations().getMutations().get(i).size(); j++) {
                    if (organism.getOrgMutations().getMutations().get(i).get(j).isActive()) {
                        g.drawImage(organism.getOrgMutations().getMutations().get(i).get(j).getSprite(), x + 83, y + 70, 196, 196, null);
                    }
                }
            }

            g.drawImage(Assets.organismPanel_close, x + width - 20, y - 20, BUTTON_CLOSE_DIMENSION, BUTTON_CLOSE_DIMENSION, null);
            //Stealth
            g.setColor(Color.ORANGE);
            g.fillRect(x + 473, y + 113, (int) 68 * organism.getSpeed() / MAX_SPEED, 20);
            //Max Health
            g.setColor(Color.CYAN);
            g.fillRect(x + 474, y + 165, (int) 68 * organism.getSize() / MAX_SIZE, 20);
            //maturity
            g.setColor(Color.YELLOW);
            g.fillRect(x + 474, y + 219, (int) 68 * organism.getStrength() / MAX_STRENGTH, 20);
            //speed
            g.setColor(Color.MAGENTA);
            g.fillRect(x + 369, y + 113, (int) 68 * organism.getStealth() / MAX_STEALTH, 20);
            //size
            g.setColor(Color.WHITE);
            g.fillRect(x + 369, y + 165, (int) 68 * organism.getMaxHealth() / MAX_SIZE, 20);
            //strength
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x + 369, y + 219, (int) 68 * organism.getMaturity() / MAX_MATURITY, 20);

            // Edit
            g.setColor(Color.WHITE);
            g.setFont(fontEvolve);
            g.drawString(Integer.toString(organism.getGeneration()), x + 474, y + 270);
            g.drawString(Double.toString(organism.getTime().getSeconds()), x + 458, y + 294);

            g.setColor(Color.WHITE);
            g.setFont(fontEvolve);
            g.drawString(organism.getName(), x + 196, y + 281);

            for (int i = 0; i < buttons.size(); i++) {

                if (i != 4 || organism.isNeedOffspring()) {
                    buttons.get(i).render(g);
                }

            }

        }

    }
};
