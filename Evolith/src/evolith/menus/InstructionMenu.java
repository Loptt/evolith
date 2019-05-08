package evolith.menus;

import evolith.engine.Assets;
import evolith.engine.SoundEffectManager;
import evolith.game.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class InstructionMenu extends Menu {
    
    private SoundEffectManager sounds;
    private ArrayList<BufferedImage> images;            //contains the instructions assets
    private int currentImage;                           //index of the current image
    private boolean over;                               // to check if the instructions are over
    
    /**
     * Contructor with the dimension and position of the instructions menu
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game 
     */
    public InstructionMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        
        currentImage = 0;                   
        over = false;
        images = new ArrayList<>();
        
        images.add(Assets.instructions.get(0));     //First instructions image
        images.add(Assets.instructions.get(1));     //Second instructions image
        images.add(Assets.instructions.get(2));     //Third instructions image
        images.add(Assets.instructions.get(3));     //Fourth instructions image
        images.add(Assets.instructions.get(4));     //Fifth instructions image
        images.add(Assets.instructions.get(5));     //Sixth instructions image
        
        buttons.add(new Button(920, 625, 55, 50, Assets.nextArrow, Assets.nextArrow)); //Next
        sounds = game.getSfx();

    }
    /**
     * To check if the instructions are over
     * @return over
     */
    public boolean isOver() {
        return over;
    }
    /**
     * To change the status of the instructions
     * @param over 
     */
    public void setOver(boolean over) {
        this.over = over;
    }
    /**
     * Ticks the instruction menu
     */
    @Override
    public void tick() {
        //Checks the mouse positon relative to the button
        if (buttons.get(0).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
            //if the mouse is over the button
            buttons.get(0).setActive(true);
            //if left click change mouse status and changes the image
            if (game.getMouseManager().isLeft()) {
                game.getSfx().playNext();
                game.getMouseManager().setLeft(false);
                currentImage++;
            }
        } else {
            buttons.get(0).setActive(false);
        }
        //if there are no more instructions end the menu
        if (currentImage >= images.size()) {
            over = true;
        }
    }
    /**
     * To render the components of the instruction menu
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        //to render the current instruction image
        g.drawImage(images.get(currentImage), x, y, width, height, null);
        //to render the next button arrow
        buttons.get(0).render(g);
    }
    
}
