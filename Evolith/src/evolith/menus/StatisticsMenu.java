package evolith.menus;

import evolith.database.JDBC;
import evolith.engine.Assets;
import evolith.game.Game;
import static evolith.helpers.Commons.BLUE_GREEN_COLOR;
import evolith.helpers.FontLoader;
import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class StatisticsMenu extends Menu {

    private boolean win;                    //to determine if the game is won
    private RankingPanel rankPanel;        // to check the panel of the top ranking
    private StatisticsPanel statsPanel;    // to check the panel of the global statistics
    private boolean mainMenu;              // to reset the menu
    private FontLoader f;                  // handles the font

    /**
     * Constructor of the Statistics Menu
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param win
     * @param mysql 
     */
    public StatisticsMenu(int x, int y, int width, int height, Game game, boolean win, JDBC mysql) {
        super(x, y, width, height, game);
        this.win = win;
        f = new FontLoader();
        //to initalize the ranking panel
        rankPanel = new RankingPanel(x + 200, y + 200, 0, 0, game, mysql);
        //initalize the panel
        statsPanel = new StatisticsPanel(x, y, 100, 100, game, true, false, 212, 355);
        //add the over button main menu
        buttons.add(new Button(70, 611, 340, 71, Assets.overMenuButtonOn, Assets.overMenuButtonOff));
    }
    /**
     * To check if the main menu is active
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
     * To tick the statistics menu
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
        //change the state to main menu
        if (buttons.get(0).isPressed()) {
            mainMenu = true;
            buttons.get(0).setPressed(false);
        }
        statsPanel.tick();
    }
    /**
     * To render the statistics menu
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        //renders the background
        g.setColor(BLUE_GREEN_COLOR);
        g.setFont(f.getFontEvolve().deriveFont(17f));
        g.drawImage(Assets.rankPanel, x, y, width, height, null);

        statsPanel.render(g);
        rankPanel.render(g);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).render(g);
        }
        //displays the message if won
        g.setFont(f.getFontEvolve().deriveFont(30f));
        if (win) {
            g.setColor(Color.GREEN);
           
            g.drawString("Trascended", 165, 180);
        } else {
            g.setColor(Color.RED);
            
            g.drawString("Extinct", 165, 180);
        }
    }
    /**
     * To set the win
     * @param win 
     */
    public void setWin(boolean win) {
        this.win = win;
    }
    
}
