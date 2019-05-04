package evolith.menus;

import evolith.database.GameP;
import evolith.database.JDBC;
import evolith.database.Ranking2P;
import evolith.game.Game;
import evolith.engine.Assets;
import evolith.helpers.Commons;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class MainMenu extends Menu implements Commons {

    private boolean active;
    private boolean clickPlay;
    private boolean clickIns;

    //private RankingPanel rankpanel;
    private StatisticsPanel stats;
    /**
     * Constructor of the main menu
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     */
    public MainMenu(int x, int y, int width, int height, Game game) throws SQLException {
        super(x, y, width, height, game);
        active = true;
        clickPlay = false;

        clickIns = false;

        buttons.add(new Button(BUTTON_PLAY_X, BUTTON_PLAY_Y, BUTTON_PLAY_WIDTH, BUTTON_PLAY_HEIGHT)); // Play button
        buttons.add(new Button(BUTTON_INSTRUCTIONS_X, BUTTON_INSTRUCTIONS_Y, BUTTON_INSTRUCTIONS_WIDTH, BUTTON_INSTRUCTIONS_HEIGHT)); // Instructions button
        //rankpanel = new RankingPanel(0,0,0,0,null);
         stats = new StatisticsPanel(600,400,0,0,null,active) ;
    }
    /**
     * To
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * To change the status of the active menu
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * To get the status of the play button
     *
     * @return clickPlay
     */
    public boolean isClickPlay() {
        return clickPlay;
    }

    /**
     * To set the status of the play button
     *
     * @param clickPlay
     */
    public void setClickPlay(boolean clickPlay) {
        this.clickPlay = clickPlay;
    }

    public boolean isClickIns() {
        return clickIns;
    }

    public void setClickIns(boolean clickIns) {
        this.clickIns = clickIns;
    }

    /**
     * To tick the buttons on the main menu
     */
    @Override
    public void tick() {

        //active status of the main menu
        if (active) {
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                    //if the mouse is over the button
                    buttons.get(i).setActive(true);
                    //if left click change mouse status
                    if (game.getMouseManager().isLeft()) {
                        buttons.get(i).setPressed(true);
                        game.getMouseManager().setLeft(false);
                    }
                } else {
                    buttons.get(i).setActive(false);
                }
                
            }
            
            if (buttons.get(0).isPressed()) {
                setClickPlay(true);
                setActive(false);
                buttons.get(0).setPressed(false);
                buttons.get(1).setPressed(false);
            }
            
            if (buttons.get(1).isPressed()) {
                setClickIns(true);
                setActive(false);
                buttons.get(0).setPressed(false);
                buttons.get(1).setPressed(false);
            }
        }
//        stats.setSpeed(stats.getSpeed()+1);
//        stats.setStealth(stats.getStealth()-1);
//        stats.setHealth(stats.getHealth()+1);
//        stats.setStrength(stats.getStrength()+1);
        stats.tick();
    }

    /**
     * To render the main menu
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {

        // if the main menu is active
        if (active && !buttons.get(0).isActive() && !buttons.get(1).isActive()) {
            g.drawImage(Assets.start, 0, 0, 1000, 700, null);
        } else if (active && buttons.get(0).isActive()) {
            g.drawImage(Assets.startPlay, 0, 0, 1000, 700, null);
        } else if (active && buttons.get(1).isActive()) {
            g.drawImage(Assets.startInstructions, 0, 0, 1000, 700, null);
        }
        //rankpanel.render(g);

        g.setColor(BLUE_GREEN_COLOR);
        stats.render(g);
}
    
}
