/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.game.Game;
import evolith.engine.Assets;
import evolith.helpers.Commons;
import evolith.helpers.InputReader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moisesfernandez
 */
public class PauseMenu extends Menu {

    private boolean active;
    /* private Button play = new Button(300,350);
    private Button instructions = new Button(300,350);
     */
    private boolean clickPlay;
    private int option;

    private String fontPath;
    private Font fontEvolve;
    private InputStream is;
    
    private boolean mainMenuDisplayed;

    public PauseMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        active = true;
        clickPlay = false;
        
        mainMenuDisplayed = false;
        
        buttons.add(new Button(x + 25, y + 25, width - 50, height / 4 - 30, Assets.PMResumeButtonOn, Assets.PMResumeButtonOff)); // Resume botton
        buttons.add(new Button(x + 25, y + 90, width - 50, height / 4 - 30, Assets.PMSaveButtonOn, Assets.PMSaveButtonOff)); // Save button
        buttons.add(new Button(x + 25, y + 155, width - 50, height / 4 - 30, Assets.PMLoadButtonOn, Assets.PMLoadButtonOff)); // Load button
        buttons.add(new Button(x + 25, y + 220, width - 50, height / 4 - 30, Assets.PMMainMenuButtonOn, Assets.PMMainMenuButtonOff)); // Main Menu button

        option = 1;

        fontPath = "/Fonts/MADE-Evolve-Sans-Regular.ttf";
        this.is = OrganismPanel.class.getResourceAsStream(fontPath);
        try {
            fontEvolve = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isClickPlay() {
        return clickPlay;
    }

    public void setClickPlay(boolean clickPlay) {
        this.clickPlay = clickPlay;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public boolean isMainMenuDisplayed() {
        return mainMenuDisplayed;
    }

    public void setMainMenuDisplayed(boolean mainMenuDisplayed) {
        this.mainMenuDisplayed = mainMenuDisplayed;
    }

    @Override
    public void tick() {

        if (!active) {
            return;
        }
        
        if (buttons.get(0).isPressed()) { // Resume button
            setMainMenuDisplayed(false);
            buttons.get(0).setPressed(false);
            buttons.get(0).setActive(false);
        }
        
        if (buttons.get(1).isPressed()) { // Save button
            
        }
        
        if (buttons.get(2).isPressed()) { // Load button
            
        }
        
        if (buttons.get(3).isPressed()) { // Main Menu button
            
        }

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                //if the mouse is over the button
                buttons.get(i).setActive(true);

                if (game.getMouseManager().isLeft()) {
                    buttons.get(i).setPressed(true);
                    game.getMouseManager().setLeft(false);

                    if (i != 0) {
                        option = i - 1;
                    }

                    for (int j = 0; j < buttons.size(); j++) {
                        if (i != j) {
                            buttons.get(j).setPressed(false);
                        }
                    }
                }
            } else {
                buttons.get(i).setActive(false);
            }
        }
    }

    @Override
    public void render(Graphics g) { 
        g.drawImage(Assets.PMPauseMenu, x, y, width, height, null);
        //g.fillRect(0, 0, game.getWidth(), game.getHeight());
        

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).render(g);
        }

    }
}
