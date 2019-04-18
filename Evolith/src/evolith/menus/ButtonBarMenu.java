/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.game.Game;
import evolith.engine.Assets;
import evolith.entities.OrganismManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author victor
 */
public class ButtonBarMenu extends Menu {
    private BufferedImage background;
    private boolean foodActive;
    private boolean waterActive;
    private boolean fightActive;
    
    public ButtonBarMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        
        background = Assets.buttonBar.get(0).get(0);
        
        buttons.add(new Button(34,31,141,55, Assets.buttonBar.get(1).get(1), Assets.buttonBar.get(1).get(0))); // Water button in pos 1
        buttons.add(new Button(191,31,141,55, Assets.buttonBar.get(2).get(1), Assets.buttonBar.get(2).get(0))); // Food  button in pos 2
        buttons.add(new Button(348,31,141,55, Assets.buttonBar.get(3).get(1), Assets.buttonBar.get(3).get(0))); // Fight  button in pos 3
        
        foodActive = false;
        waterActive = false;
        fightActive = false;

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
        OrganismManager orgs = game.getOrganisms();
        
        foodActive = orgs.selectionHasActiveFood();
        waterActive = orgs.selectionHasActiveWater();
        fightActive = orgs.selectionHasAggressiveness();

        buttons.get(1).setActive(foodActive);
        buttons.get(0).setActive(waterActive);
        buttons.get(2).setActive(fightActive);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background, x, y, width, height, null);
        
        for(int i=0; i<buttons.size(); i++){
            buttons.get(i).render(g);
        }
    }

    public void applyMouse(int mouseX, int mouseY) {
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).hasMouse(mouseX, mouseY)) {
                buttons.get(i).setActive(!buttons.get(i).isActive());
            }
        }
        
        foodActive = buttons.get(1).isActive();
        waterActive = buttons.get(0).isActive();
        fightActive = buttons.get(2).isActive();
    }

    public boolean isFoodActive() {
        return foodActive;
    }

    public boolean isWaterActive() {
        return waterActive;
    }

    public boolean isFightActive() {
        return fightActive;
    }
}
