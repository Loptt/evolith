/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodandwater;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public class ButtonBar extends Menu{
    private ArrayList<Button> buttons;
    
    public ButtonBar(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        buttons = new ArrayList<Button>();
        buttons.add(new Button(x,y,width,height)); // Bar skeleton button in pos 0
        buttons.add(new Button(34,35,141,49)); // Water button in pos 1
        buttons.add(new Button(191,35,141,49)); // Food  button in pos 2
        buttons.add(new Button(348,35,141,49)); // Fight  button in pos 3

    }
    // organism's behaviour when Water button is active
    public void activateWater(){
        
    }
    // organism's behaviour when Food button is active 
    public void activateFood(){
        
    }
    // organism's behaviour when Fight button is active
    public void activateFight(){
        
    }

    @Override
    public void tick() {
        for(int i=1; i<buttons.size(); i++){
            if(buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())){
                //if the mouse is over the button
                buttons.get(i).setHover(true); //set the button hover status as true
                if(game.getMouseManager().isIzquierdo()){
                    //if left click
                    buttons.get(i).setPressed(true);
                    buttons.get(i).setActive(!buttons.get(i).isActive());
                    game.getMouseManager().setIzquierdo(false);
                }
            }
            else{
                buttons.get(i).setHover(false);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        buttons.get(0).render(g, Assets.buttonBar, 0,0);
        for(int i=1; i<buttons.size(); i++){
            if(buttons.get(i).isActive()||buttons.get(i).isHover()){
                buttons.get(i).render(g, Assets.buttonBar, i, 1);
            }
            else{
                buttons.get(i).render(g, Assets.buttonBar, i, 0);
            }
        }
    }
    
}
