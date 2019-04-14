/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author moisesfernandez
 */
public class SetupMenu extends Menu{
    private boolean active;
    /* private Button play = new Button(300,350);
    private Button instructions = new Button(300,350);
    */
    private ArrayList<Button> buttons;
    private boolean clickPlay;
    private String speciesName = "";

    public SetupMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        active = true;
        clickPlay = false;

        buttons = new ArrayList<Button>();
        buttons.add(new Button(340, 555, 350, 110, Assets.playOn, Assets.playOff)); // Play button
        buttons.add(new Button(100, 210, 170, 185, Assets.redOption)); // Red option
        buttons.add(new Button(300, 210, 170, 185, Assets.purpleOption)); // Purple option
        buttons.add(new Button(500, 210, 170, 185, Assets.blueOption)); // Blue option
        buttons.add(new Button(700, 210, 170, 185, Assets.yellowOption)); // Yellow option
        buttons.add(new Button(215, 480, 570, 65)); // Write text
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
    
    @Override
    public void tick() {
        if(active){
            for(int i=0; i<buttons.size(); i++){
                if(buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())){
                    //if the mouse is over the button
                    buttons.get(i).setActive(true);
                    
                    if(game.getMouseManager().isIzquierdo()){
                        buttons.get(i).setPressed(true);
                        game.getMouseManager().setIzquierdo(false);
                    }
                }
                else {
                    buttons.get(i).setActive(false);
                }
                
                if(!buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY()) && game.getMouseManager().isIzquierdo()){
                    buttons.get(i).setActive(false);
                    buttons.get(i).setPressed(false);
                }
                
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
        
        if (active && buttons.get(5).isPressed()) {
            int fontSize = 50;
            
            g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
            g.setColor(Color.black);
            
            if (game.getKeyManager().a) {
                speciesName += 'a';
            }
            
            if (game.getKeyManager().b) {
                speciesName += 'b';
            }
            
            if (game.getKeyManager().c) {
                speciesName += 'c';
            }
            
            if (game.getKeyManager().d) {
                speciesName += 'd';
            }
            
            if (game.getKeyManager().e) {
                speciesName += 'e';
            }
            
            if (game.getKeyManager().f) {
                speciesName += 'f';
            }
            
            if (game.getKeyManager().g) {
                speciesName += 'g';
            }
            
            
            if (game.getKeyManager().h) {
                speciesName += 'h';
            }
            
            
            if (game.getKeyManager().i) {
                speciesName += 'i';
            }
            
            
            if (game.getKeyManager().j) {
                speciesName += 'j';
            }
            
            
            if (game.getKeyManager().k) {
                speciesName += 'k';
            }
            
            
            if (game.getKeyManager().l) {
                speciesName += 'l';
            }
            
            
            if (game.getKeyManager().m) {
                speciesName += 'm';
            }
            
            
            if (game.getKeyManager().n) {
                speciesName += 'n';
            }
            
            
            if (game.getKeyManager().o) {
                speciesName += 'o';
            }
            
            
            if (game.getKeyManager().p) {
                speciesName += 'p';
            }
            
            
            if (game.getKeyManager().q) {
                speciesName += 'q';
            }
            
            
            if (game.getKeyManager().r) {
                speciesName += 'r';
            }
            
            
            if (game.getKeyManager().s) {
                speciesName += 's';
            }
            
            
            if (game.getKeyManager().t) {
                speciesName += 't';
            }
            
            
            if (game.getKeyManager().u) {
                speciesName += 'u';
            }
            
            
            if (game.getKeyManager().v) {
                speciesName += 'v';
            }
            
            
            if (game.getKeyManager().w) {
                speciesName += 'w';
            }
            
            
            if (game.getKeyManager().x) {
                speciesName += 'x';
            }
            
            
            if (game.getKeyManager().y) {
                speciesName += 'y';
            }
            
            
            if (game.getKeyManager().z) {
                speciesName += 'z';
            }
            
            if (game.getKeyManager().delete) {
                if (speciesName != null && speciesName.length() > 0) {
                    speciesName = speciesName.substring(0, speciesName.length() - 1);
                }
            }
            
            if (speciesName != null && speciesName.length() > 0) {
                if (speciesName.length() == 1) {
                    g.drawString(speciesName.toUpperCase(), 215, 525);
                }
                else {
                    String firstCharacter = speciesName.substring(0, 1).toUpperCase();
                    speciesName = firstCharacter + speciesName.substring(1);
                    g.drawString(speciesName, 215, 525);
                }
            }
        }
    }
}
    