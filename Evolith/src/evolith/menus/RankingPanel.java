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

    private boolean connected;
    private FontLoader f;
    private Game game;
    private JDBC mysql;
    private ArrayList<ArrayList<Object>> myRanking;

    public RankingPanel(int x, int y, int width, int height, Game game, JDBC mysql) {
        super(x, y, width, height, game);
        try {
            f = new FontLoader();
            mysql = new JDBC();
            myRanking = mysql.getRanking();
            //rank = new  Ranking2P();
            //ranking = rank.getRanking2P();
            this.connected = true;
        } catch (Exception e) {
            this.connected = false;
            System.out.println("Ranking Panel Expection:" + e.getMessage());
        }
        this.game = game;
        this.mysql = mysql;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

        if (!connected) {
            return;
        }

        g.setColor(Color.white);
        g.setFont(f.getFontEvolve().deriveFont(20f));
        /* 
       for(int i = 0; i < ranking.size(); i++)
       {
                g.drawString(ranking.get(i).getPlayerName(), 10, 50+20*i);
                g.drawString(ranking.get(i).getSpeciesName(), 100, 50+20*i);
                g.drawString(Integer.toString(ranking.get(i).getGameDuration()), 200, 50+20*i);
                g.drawString(Integer.toString(ranking.get(i).getGameScore()), 300, 50+20*i);      
        }
 }*/

        for (int i = 0; i < myRanking.size(); i++) {

            for (int j = 0; j < myRanking.get(i).size(); j++) {
                g.drawString((String) myRanking.get(i).get(j), 300 + j * 100, 200 + i * 50);
            }
        }

    }

}
