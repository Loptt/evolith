/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.game.Game;
import evolith.helpers.Commons;
import evolith.helpers.FontLoader;
import evolith.menus.Menu;
import java.awt.Graphics;

/**
 *
 * @author ErickFrank
 */
public class StatisticsPanel extends Menu implements Commons{
    private boolean connected;
    private FontLoader f;
    
    
    private Game game;
    public StatisticsPanel(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
           try{
         f= new FontLoader();
         this.connected = true;
          } catch(Exception e)
          {
              this.connected = false;
              System.out.println("Statistics Panel Expection:" + e.getMessage());
          }
    }

    @Override
    public void tick() {
 }

    @Override
    public void render(Graphics g) {
        g.setColor(BLUE_GREEN_COLOR );
        g.setFont(f.getFontEvolve().deriveFont(20f));
        g.drawRect(x, y, STATISTICS_DIMENSION, STATISTICS_DIMENSION);
        g.fillOval(x, y, 5, 5);  
        g.fillOval(x+STATISTICS_DIMENSION, y, 5, 5);  
        g.fillOval(x, y+STATISTICS_DIMENSION, 5, 5);  
        g.fillOval(x+STATISTICS_DIMENSION, y+STATISTICS_DIMENSION, 5, 5);  
        
        
}
    
}
