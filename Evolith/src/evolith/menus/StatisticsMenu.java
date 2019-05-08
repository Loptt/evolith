/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.database.JDBC;
import evolith.engine.Assets;
import evolith.game.Game;
import static evolith.helpers.Commons.BLUE_GREEN_COLOR;
import evolith.helpers.FontLoader;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author charles
 */
public class StatisticsMenu extends Menu {

    private boolean win;
    private RankingPanel rankPanel;
    private StatisticsPanel statsPanel;
    private boolean mainMenu;
    private FontLoader f;

    private boolean back;

    public StatisticsMenu(int x, int y, int width, int height, Game game, boolean win, JDBC mysql) {
        super(x, y, width, height, game);
        this.win = win;
        f = new FontLoader();
        rankPanel = new RankingPanel(x + 200, y + 200, 0, 0, game, mysql);
        
        statsPanel = new StatisticsPanel(x, y, 100, 100, game, true, false, 212, 355);
        
        buttons.add(new Button(70, 611, 340, 71, Assets.overMenuButtonOn, Assets.overMenuButtonOff));
    }

    public boolean isMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(boolean mainMenu) {
        this.mainMenu = mainMenu;
    }

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
            buttons.get(0).setPressed(false);
        }
        statsPanel.tick();
    }

    @Override
    public void render(Graphics g) {

        g.setColor(BLUE_GREEN_COLOR);
        g.setFont(f.getFontEvolve().deriveFont(17f));
        g.drawImage(Assets.rankPanel, x, y, width, height, null);

        
        statsPanel.render(g);
        rankPanel.render(g);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).render(g);
        }
        
        g.setFont(f.getFontEvolve().deriveFont(30f));
        if (win) {
            g.setColor(Color.GREEN);
           
            g.drawString("Trascended", 165, 180);
        } else {
            g.setColor(Color.RED);
            
            g.drawString("Extinct", 165, 180);
        }
    }

    public void setWin(boolean win) {
        this.win = win;
    }
    
}