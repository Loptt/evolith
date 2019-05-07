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
public class GameStatisticsMenu extends Menu {
    private BufferedImage background;
    private boolean statsActive;
    
    public GameStatisticsMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        
        background = Assets.buttonBar.get(0).get(0);
        buttons.add(new Button(18,643,34,34, Assets.statsiconOn, Assets.statsiconOff)); 
        statsActive = false;

    }

    @Override
    public void tick() {
        OrganismManager orgs = game.getOrganisms();
    }

    @Override
    public void render(Graphics g) {
        
        g.drawImage(background, 10, 635, 50, 50, null);
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
    }
}
