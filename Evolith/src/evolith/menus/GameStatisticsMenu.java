package evolith.menus;

import evolith.game.Game;
import evolith.engine.Assets;
import evolith.entities.OrganismManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class GameStatisticsMenu extends Menu {
    private BufferedImage background;
    private boolean statsActive;
    private Game game;
    /**
     * Constructor of the Game statistics menu's position and dimension
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game 
     */
    public GameStatisticsMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        //sets the background of the ingame stats
        background = Assets.buttonBar.get(0).get(0);
        //button to show the menu
        buttons.add(new Button(18,643,34,34, Assets.statsiconOn,  Assets.statsiconOff)); 
        //starts as not active
        statsActive = false;
        this.game = game;
    }
    /**
     * To tick the statistics menu
     */
    @Override
    public void tick() {
        //Checks the mouse positon relative to the button
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                //if the mouse is over the button 
                buttons.get(i).setActive(true);
                //if left click change mouse status
                if (game.getMouseManager().isLeft()) {
                    //Sets the button to the pressed status
                    buttons.get(i).setPressed(true);
                    for (int j = 0; j < buttons.size(); j++) {
                        if (i != j) {
                            buttons.get(j).setPressed(false);
                        }
                    }
                    //Turns off mouse 
                    game.getMouseManager().setLeft(false);
                    break;
                }
            } else {
                //Sets the button to false if the button is hovered
                buttons.get(i).setActive(false);
            }
        }
        //shows the ingame statistics 
        if (buttons.get(0).isPressed()) {
            game.getOrganisms().getStatsPanel().setActive(true);
            buttons.get(0).setPressed(false);
        }
    }
    /**
     * To render the game statistics menu
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        //renders the background
        g.drawImage(background, 10, 635, 50, 50, null);
        //renders the buttons 
        for(int i=0; i<buttons.size(); i++){
            buttons.get(i).render(g);
        }
    }
    /**
     * To control the mouse with the left and right click
     * @param mouseX
     * @param mouseY 
     */
    public void applyMouse(int mouseX, int mouseY) {
        //changes the status if clicked
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).hasMouse(mouseX, mouseY)) {
                buttons.get(i).setActive(!buttons.get(i).isActive());
            }
        }
    }
}
