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
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class RankingPanel extends Menu implements Commons {

    private FontLoader f;               //font loader
    private Game game;                  //game
    private JDBC mysql;                 //connection to the database
    private ArrayList<ArrayList<Object>> myRanking; //the matrix of the ranking

    /**
     * Constructor of the ranking panel
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param mysql 
     */
    public RankingPanel(int x, int y, int width, int height, Game game, JDBC mysql) {
        super(x, y, width, height, game);
        
        f = new FontLoader();
        this.mysql = mysql;
        //initialize the ranking panel with the database informations
        try {
            myRanking = mysql.getRanking();
        } catch (Exception e) {
            System.out.println("Ranking Panel Expection:" + e.getMessage());
        }
        this.game = game;
    }
    /**
     * To tick the ranking panel
     */
    @Override
    public void tick() {

    }
    /**
     * To render the graphics
     * @param g 
     */
    @Override
    public void render(Graphics g) {

        g.setColor(Color.white);
        g.setFont(f.getFontEvolve().deriveFont(20f));
        //displays the table or dataset of the information in the ranking
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
