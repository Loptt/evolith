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
    private int timeOpen;
    private boolean tickToWrite;

    public SetupMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        active = true;
        clickPlay = false;

        buttons.add(new Button(340, 620, 330, 70, Assets.playOn, Assets.playOff)); // Play button
        buttons.add(new Button(500, 330, 120, 120, Assets.pinkOptionOn, Assets.pinkOptionOff, true)); // Pink option
        buttons.add(new Button(370, 200, 120, 120, Assets.purpleOptionOn, Assets.purpleOptionOff, true)); // Purple option
        buttons.add(new Button(500, 200, 120, 120, Assets.bluegreenOptionOn, Assets.bluegreenOptionOff, true)); // Blue option
        buttons.add(new Button(370, 330, 120, 120, Assets.yellowOptionOn, Assets.yellowOptionOff, true)); // Yellow option
        buttons.add(new Button(215, 480, 570, 65)); // Write text
        
        option = 1;
        this.name = "";
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
        this.timeOpen = 0;
        this.tickToWrite = false;
        
    }
    
    public boolean isActive(){
        return active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(int timeOpen) {
        this.timeOpen = timeOpen;
    }

    public boolean isTickToWrite() {
        return tickToWrite;
    }

    public void setTickToWrite(boolean tickToWrite) {
        this.tickToWrite = tickToWrite;
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
        
        if(!active){
         return;
        }
        timeOpen++;
            
        if (game.getG().getFontMetrics().stringWidth(inputReader.getSpeciesName()) > 383) {
            inputReader.setOnlyDelete(true);
        }
        else {
            inputReader.setOnlyDelete(false);
        }

        inputReader.readInput();
        name = inputReader.getSpeciesName();

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

        }
        
        if (option >= 4) {
            option = 3;
        } 
            
        if(buttons.get(0).isPressed()){
            setClickPlay(true);
            setActive(false);
            buttons.get(0).setPressed(false);
            buttons.get(1).setPressed(false);
            buttons.get(2).setPressed(false);
            buttons.get(3).setPressed(false);
            buttons.get(4).setPressed(false);
            buttons.get(5).setPressed(false);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.setupSpeciesBackground, 0, 0, 1000, 700, null);

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).render(g);
        }
        g.setColor(Commons.FONT_COLOR);
        g.setFont(fontEvolve.deriveFont(40f));
        
        if (timeOpen % 60 == 0) {
            timeOpen = 0;

            tickToWrite = !tickToWrite;
        }
        
    
        g.drawString(name, x + 407, y + 567);
        int width = g.getFontMetrics().stringWidth(name);

        if (tickToWrite && !inputReader.isOnlyDelete()) {
            g.drawString("l", x + 407 + width, y + 567);
        }
    }
}
    