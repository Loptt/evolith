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

    private Organism organism;

    private String fontPath;
    private String name;
    private Font fontEvolve;
    private InputStream is;

    private InputReader inputReader;

    private boolean active;
    private boolean clickEdit;
    private boolean searchNext;
    private boolean searchPrev;
    private boolean reproduce;
    private int index;

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

    public OrganismPanel(int x, int y, int width, int height, Game game, Organism org) {
        super(x, y, width, height, game);
        this.organism = org;
        this.searchNext = false;
        this.searchPrev = false;
        this.reproduce = false;
        this.index = 0;

        this.active = true;
        //Exit
        buttons.add(new Button(this.x + this.width - 20, this.y - 20, 40, 40));
        //Edit
        buttons.add(new Button(this.x + 32, this.y + 28, 190, 35)); // Edit 
        // Arrow next
        buttons.add(new Button(this.x + PANEL_WIDTH + 50, this.y + PANEL_HEIGHT / 2, 50, 50, Assets.organismPanel_nextArrow));
        // Arrow prev
        buttons.add(new Button(this.x - 100, this.y + PANEL_HEIGHT / 2, 50, 50, Assets.organismPanel_prevArrow));
        // Reproduce button 
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 - 150, this.y + 400, 300, 75, Assets.organismPanel_reproduceButton));

        inputReader = new InputReader(game);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public Organism getOrganism() {
        return organism;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;

    }

    @Override
    public void tick() {
        /*if(organism.isDead())
        {
            active = false;
        }*/
        inputReader.readInput();
        if (active) {

            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                    //if the mouse is over the button 
                    buttons.get(i).setActive(true);
                    //if left click change mouse status
                    if (game.getMouseManager().isLeft()) {
                        buttons.get(i).setPressed(true);
                        game.getMouseManager().setLeft(false);
                    }
                } else {
                    buttons.get(i).setActive(false);
                }
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

    public boolean isClickEdit() {
        return clickEdit;
    }

    public void setClickEdit(boolean clickEdit) {
        this.clickEdit = clickEdit;
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
            g.drawImage(Assets.organismPanel_close, x + width - 20, y - 20, BUTTON_CLOSE_DIMENSION, BUTTON_CLOSE_DIMENSION, null);
            //Stealth
            g.setColor(Color.ORANGE);
            g.fillRect(x + 470, y + 112, (int) 68 * organism.getSpeed() / MAX_SPEED, 20);
            //Max Health
            g.setColor(Color.CYAN);
            g.fillRect(x + 470, y + 167, (int) 68 * organism.getSize() / MAX_SIZE, 20);
            //maturity
            g.setColor(Color.YELLOW);
            g.fillRect(x + 470, y + 224, (int) 68 * organism.getStrength() / MAX_STRENGTH, 20);
            //speed
            g.setColor(Color.MAGENTA);
            g.fillRect(x + 368, y + 112, (int) 68 * organism.getStealth() / MAX_STEALTH, 20);
            //size
            g.setColor(Color.WHITE);
            g.fillRect(x + 368, y + 167, (int) 68 * organism.getMaxHealth() / MAX_SIZE, 20);
            //strength
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x + 368, y + 224, (int) 68 * organism.getMaturity() / MAX_MATURITY, 20);

            // Edit
            g.setColor(Color.WHITE);
            g.setFont(fontEvolve);
            g.drawString(Integer.toString(organism.getGeneration()), x + 160, y + 277);
            g.drawString(Double.toString(organism.getTime().getSeconds() + organism.getTime().getMilliseconds()), x + 130, y + 305);

            g.setColor(Color.WHITE);
            g.setFont(fontEvolve);
            g.drawString(organism.getName(), x + 40, y + 57);

            for (int i = 0; i < buttons.size(); i++) {

                if (i != 4 || organism.isNeedOffspring()) {
                    buttons.get(i).render(g);
                }

            }

        }

    }
};
