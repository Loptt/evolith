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
        buttons.add(new Button(340,530,350,110)); // Water button in pos 1
        buttons.add(new Button(340,530,350,110)); // Food  button in pos 2
        buttons.add(new Button(340,530,350,110)); // Fight  button in pos 3

    }
    
    public void activateWater(){
        
    }
        
    public void activateFood(){
        
    }
    
    public void activateFight(){
        
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        buttons.get(0).render(g, Assets.buttonBar.get(0).get(0));
    }

    
    
}
