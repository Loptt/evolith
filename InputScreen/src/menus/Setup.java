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
public class Setup extends Menu{
    private boolean active;
    /* private Button play = new Button(300,350);
    private Button instructions = new Button(300,350);
    */
    private ArrayList<Button> buttons;
    private boolean clickPlay;


    public Setup(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        active = true;
        clickPlay = false;

        buttons = new ArrayList<Button>();
        buttons.add(new Button(340, 555, 350, 110)); // Play button
        buttons.add(new Button(100, 210, 170, 185)); // Red option
        buttons.add(new Button(300, 210, 170, 185)); // Purple option
        buttons.add(new Button(500, 210, 170, 185)); // Blue option
        buttons.add(new Button(700, 210, 170, 185)); // Yellow option
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
        if(active && !buttons.get(0).isActive() && !buttons.get(1).isActive() 
                && !buttons.get(2).isActive() && !buttons.get(3).isActive()
                && !buttons.get(4).isActive()) { // Only in setup menu
            g.drawImage(Assets.setupSpeciesBackground, 0, 0, 1000, 700, null);
            g.drawImage(Assets.playOff, 340, 555, 350, 110, null);
            g.drawImage(Assets.redOption, 100, 210, 170, 185, null);
            g.drawImage(Assets.purpleOption, 300, 210, 170, 185, null);
            g.drawImage(Assets.blueOption, 500, 210, 170, 185, null);
            g.drawImage(Assets.yellowOption, 700, 210, 170, 185, null);
        }
        else if(active && buttons.get(0).isActive()) { // Setup menu hovering over play button
            g.drawImage(Assets.setupSpeciesBackground, 0, 0, 1000, 700, null);
            g.drawImage(Assets.playOn, 340, 555, 350, 110, null);
            g.drawImage(Assets.redOption, 100, 210, 170, 185, null);
            g.drawImage(Assets.purpleOption, 300, 210, 170, 185, null);
            g.drawImage(Assets.blueOption, 500, 210, 170, 185, null);
            g.drawImage(Assets.yellowOption, 700, 210, 170, 185, null);
        }
        else if(active && buttons.get(1).isActive()) { // Setup menu hovering over red option
            g.drawImage(Assets.setupSpeciesBackground, 0, 0, 1000, 700, null);
            g.drawImage(Assets.playOff, 340, 555, 350, 110, null);
            g.drawImage(Assets.redOption, 100, 210, 200, 215, null);
            g.drawImage(Assets.purpleOption, 300, 210, 170, 185, null);
            g.drawImage(Assets.blueOption, 500, 210, 170, 185, null);
            g.drawImage(Assets.yellowOption, 700, 210, 170, 185, null);
        }
        else if(active && buttons.get(2).isActive()) { // Setup menu hovering over purple option
            g.drawImage(Assets.setupSpeciesBackground, 0, 0, 1000, 700, null);
            g.drawImage(Assets.playOff, 340, 555, 350, 110, null);
            g.drawImage(Assets.redOption, 100, 210, 170, 185, null);
            g.drawImage(Assets.purpleOption, 300, 210, 200, 215, null);
            g.drawImage(Assets.blueOption, 500, 210, 170, 185, null);
            g.drawImage(Assets.yellowOption, 700, 210, 170, 185, null);
        }
        else if(active && buttons.get(3).isActive()) { // Setup menu hovering over blue option
            g.drawImage(Assets.setupSpeciesBackground, 0, 0, 1000, 700, null);
            g.drawImage(Assets.playOff, 340, 555, 350, 110, null);
            g.drawImage(Assets.redOption, 100, 210, 170, 185, null);
            g.drawImage(Assets.purpleOption, 300, 210, 170, 185, null);
            g.drawImage(Assets.blueOption, 500, 210, 200, 215, null);
            g.drawImage(Assets.yellowOption, 700, 210, 170, 185, null);
        }
        else if(active && buttons.get(4).isActive()) { // Setup menu hovering over yellow option
            g.drawImage(Assets.setupSpeciesBackground, 0, 0, 1000, 700, null);
            g.drawImage(Assets.playOff, 340, 555, 350, 110, null);
            g.drawImage(Assets.redOption, 100, 210, 170, 185, null);
            g.drawImage(Assets.purpleOption, 300, 210, 170, 185, null);
            g.drawImage(Assets.blueOption, 500, 210, 170, 185, null);
            g.drawImage(Assets.yellowOption, 700, 210, 200, 215, null);
        }
    }
}
    