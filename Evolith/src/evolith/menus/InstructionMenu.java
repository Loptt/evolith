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
 * @author charles
 */
public class InstructionMenu extends Menu {
    
    private ArrayList<BufferedImage> images;
    private int currentImage;
    private boolean over;

    public InstructionMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        
        currentImage = 0;
        over = false;
        images = new ArrayList<>();
        
        images.add(Assets.instructions.get(0));
        images.add(Assets.instructions.get(1));
        images.add(Assets.instructions.get(2));
        images.add(Assets.instructions.get(3));
        images.add(Assets.instructions.get(4));
        images.add(Assets.instructions.get(5));
        
        buttons.add(new Button(920, 625, 55, 50, Assets.nextArrow, Assets.nextArrow)); //Next
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    @Override
    public void tick() {
        if (buttons.get(0).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
            //if the mouse is over the button
            buttons.get(0).setActive(true);

            if (game.getMouseManager().isLeft()) {
                game.getMouseManager().setLeft(false);
                currentImage++;
            }
        } else {
            buttons.get(0).setActive(false);
        }
        
        if (currentImage >= images.size()) {
            over = true;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(images.get(currentImage), x, y, width, height, null);
        
        buttons.get(0).render(g);
    }
    
}
