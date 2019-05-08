package evolith.menus;

import evolith.engine.Assets;
import evolith.game.Game;
import evolith.helpers.Commons;
import java.awt.Graphics;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class OverMenu extends Menu {
    
    private boolean win;                //to determine the win
    
    private boolean mainMenu;
    private boolean stats;
    private String message;
    /**
     * Constructor of the over menu with a win
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param win 
     */
    public OverMenu(int x, int y, int width, int height, Game game, boolean win) {
        super(x, y, width, height, game);
        this.win = win;
        
        buttons.add(new Button(width / 2 - 340 / 2, 390, 340, 71, Assets.overMenuButtonOn, Assets.overMenuButtonOff));
        buttons.add(new Button(width / 2 - 340 / 2, 480, 340, 71, Assets.statsMenuButtonOn, Assets.statsMenuButtonOff));
        message = "";
        
    }   
    /**
     * Constructor of the over menu with the message if won
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param win
     * @param message 
     */
    public OverMenu(int x, int y, int width, int height, Game game, boolean win, String message) {
        super(x, y, width, height, game);
        this.win = win;
        
        buttons.add(new Button(width / 2 - 340 / 2, 390, 340, 71, Assets.overMenuButtonOn, Assets.overMenuButtonOff));
        buttons.add(new Button(width / 2 - 340 / 2, 480, 340, 71, Assets.statsMenuButtonOn, Assets.statsMenuButtonOff));
        this.message = message;
        
    }

    /**
     * To check if the main menu will be active
     * @return mainMenu
     */
    public boolean isMainMenu() {
        return mainMenu;
    }
    /**
     * To set the status of the main menu
     * @param mainMenu 
     */
    public void setMainMenu(boolean mainMenu) {
        this.mainMenu = mainMenu;
    }
    /**
     * To check if the stats panel will be active
     * @return 
     */
    public boolean isStats() {
        return stats;
    }
    /**
     * To set the status of the panel
     * @param stats 
     */
    public void setStats(boolean stats) {
        this.stats = stats;
    }
    /**
     * To tick the over menu
     */
    @Override
    public void tick() {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                //if the mouse is over the button
                buttons.get(i).setActive(true);

                if (game.getMouseManager().isLeft()) {
                    buttons.get(i).setPressed(true);
                    game.getMouseManager().setLeft(false);

                    for (int j = 0; j < buttons.size(); j++) {
                        if (i != j) {
                            buttons.get(j).setPressed(false);
                        }
                    }
                }
            } else {
                buttons.get(i).setActive(false);
            }
        }
        
        if (buttons.get(0).isPressed()) {
            mainMenu = true;
            stats = false;
        }
        
        if (buttons.get(1).isPressed()) {
            mainMenu = false;
            stats = true;
        }
    }
    /**
     * To render the graphics
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        if (win) {
            g.drawImage(Assets.overWin, x, y, width, height, null);
        } else {
            g.drawImage(Assets.overLose, x, y, width, height, null);
        }
        
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).render(g);
        }
        
        int widthMessage = g.getFontMetrics().stringWidth(message);
        
        g.setColor(Commons.FONT_COLOR);
        g.drawString(message, width / 2 - widthMessage / 2, 360);
    }
    
}
