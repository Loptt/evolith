/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

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
        buttons.add(new Button(200, 450, 800, 100)); // Write text
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
                else{
                    buttons.get(i).setActive(false);
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
        
        
    }
}
    