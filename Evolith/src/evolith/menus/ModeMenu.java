/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.engine.Assets;
import evolith.game.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Carlos Estrada
 */
public class ModeMenu extends Menu {
    
    private ArrayList<BufferedImage> backgrounds;
    private int currentPage;

    public ModeMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        currentPage = 0;
        backgrounds.add(Assets.instructions.get(0));
        backgrounds.add(Assets.instructions.get(0));
        backgrounds.add(Assets.instructions.get(0));
        backgrounds.add(Assets.instructions.get(0));
    }

    @Override
    public void tick() {
        switch(currentPage) {
            
        }
    }

    @Override
    public void render(Graphics g) {
        
    }
    
}
