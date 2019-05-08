/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.database.JDBC;
import evolith.game.Game;
import evolith.helpers.Commons;
import evolith.helpers.FontLoader;
import evolith.menus.Menu;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author ErickFrank
 */
public class RankingPanel extends Menu implements Commons {

    private FontLoader f;
    private Game game;
    private JDBC mysql;
    private ArrayList<ArrayList<Object>> myRanking;

    public RankingPanel(int x, int y, int width, int height, Game game, JDBC mysql) {
        super(x, y, width, height, game);
        f = new FontLoader();
        this.mysql = mysql;
        try {
            myRanking = mysql.getRanking();
        } catch (Exception e) {
            System.out.println("Ranking Panel Expection:" + e.getMessage());
        }
        this.game = game;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.white);
        g.setFont(f.getFontEvolve().deriveFont(20f));

        for (int i = 0; i < myRanking.size(); i++) {
            for (int j = 0; j < myRanking.get(i).size(); j++) {
                g.drawString((String) myRanking.get(i).get(j) , 475 + j * 145, 257 + i * 54);
            }
        }

        g.drawString("Species", 495, 192);
        g.drawString("Duration", 633, 192);
        g.drawString("Intelligence", 755, 192);

    }

}
