/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.game.Game;
import evolith.engine.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public class ButtonBarMenu extends Menu {
    private ArrayList<Button> buttons;
    private BufferedImage background;
    
    public ButtonBarMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        buttons = new ArrayList<Button>();
        
        background = Assets.buttonBar.get(0).get(0);
        
        buttons.add(new Button(34,35,141,49, Assets.buttonBar.get(1).get(1), Assets.buttonBar.get(1).get(0))); // Water button in pos 1
        buttons.add(new Button(191,35,141,49, Assets.buttonBar.get(2).get(1), Assets.buttonBar.get(2).get(0))); // Food  button in pos 2
        buttons.add(new Button(348,35,141,49, Assets.buttonBar.get(3).get(1), Assets.buttonBar.get(3).get(0))); // Fight  button in pos 3

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
        for(int i=0; i<buttons.size(); i++){
            if(buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())){
                //if the mouse is over the button

                buttons.get(i).setHover(true); //set the button hover status as true
                if(game.getMouseManager().isIzquierdo()){
                    //if left click
                    System.out.println("IS PRESSED");
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
        g.drawImage(background, x, y, width, height, null);
        
        for(int i=0; i<buttons.size(); i++){
            buttons.get(i).render(g);
        }
    }
    
}
