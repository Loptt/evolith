/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.game.Game;
import evolith.engine.Assets;
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
public class SetupMenu extends Menu {
    private boolean active;
    /* private Button play = new Button(300,350);
    private Button instructions = new Button(300,350);
    */
    private boolean clickPlay;
    private int option;
    
    private InputReader inputReader;
    
    private String fontPath;
    private String name;
    private Font fontEvolve;
    private InputStream is;

    public SetupMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        active = true;
        clickPlay = false;

        buttons.add(new Button(340, 555, 350, 110, Assets.playOn, Assets.playOff)); // Play button
        buttons.add(new Button(100, 210, 170, 185, Assets.pinkOption)); // Red option
        buttons.add(new Button(300, 210, 170, 185, Assets.purpleOption)); // Purple option
        buttons.add(new Button(500, 210, 170, 185, Assets.bluegreenOption)); // Blue option
        buttons.add(new Button(700, 210, 170, 185, Assets.orangeOption)); // Yellow option
        buttons.add(new Button(215, 480, 570, 65)); // Write text
        
        option = 1;
        
        inputReader = new InputReader(game);
        
        
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
    
    public boolean isActive(){
        return active;
    }
    
    public void setActive(boolean active){
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
    
    @Override
    public void tick() {
        inputReader.readInput();
        
        if(active){
            for(int i=0; i<buttons.size(); i++){
                if(buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())){
                    //if the mouse is over the button
                    buttons.get(i).setActive(true);
                    
                    if(game.getMouseManager().isLeft()){
                        buttons.get(i).setPressed(true);
                        game.getMouseManager().setLeft(false);
                        
                        if (i != 0) {   
                            option = i-1;
                        }
                        
                        for (int j = 0; j < buttons.size(); j++) {
                            if (i != j) {
                                buttons.get(j).setPressed(false);
                            }
                        }
                    }
                }
                else {
                    buttons.get(i).setActive(false);
                }
                
                /****Check if we really want this
                if(!buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY()) && game.getMouseManager().isIzquierdo()){
                    buttons.get(i).setActive(false);
                    buttons.get(i).setPressed(false);
                }*/
                
                if(buttons.get(0).isPressed()){
                    setClickPlay(true);
                    setActive(false);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.setupSpeciesBackground, 0, 0, 1000, 700, null);

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).render(g);
        }
        g.setColor(Color.WHITE);
        g.setFont(fontEvolve.deriveFont(20f));
        if (inputReader.getSpeciesName() != null && inputReader.getSpeciesName().length() > 0) {
            g.drawString(inputReader.getSpeciesName().toUpperCase(), 215, 525);
        }
    }
}
    