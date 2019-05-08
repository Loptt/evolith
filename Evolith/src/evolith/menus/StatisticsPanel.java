/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.database.JDBC;
import evolith.engine.Assets;
import evolith.game.Game;
import evolith.helpers.Commons;
import static evolith.helpers.Commons.BLUE_GREEN_COLOR;
import evolith.helpers.FontLoader;
import evolith.menus.Menu;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author ErickFrank
 */
public class StatisticsPanel extends Menu implements Commons {

    private boolean ingame;
    private boolean active;
    private FontLoader f;
    
    private int pointsX[];
    private int pointsY[];
    private int pointsYAvg[];
    private int pointsXAvg[];
    
    private int centerX;
    private int centerY;

    private Game game;
    private int speed;
    private int stealth;
    private int health;
    private int strength;
    private int avg[];
    private JDBC mysql;

    public StatisticsPanel(int x, int y, int width, int height, Game game, boolean active, boolean ingame,int centerX, int centerY) {
        super(x, y, width, height, game);
        
        f = new FontLoader();
        this.mysql = game.getMysql();
        this.active = active;
        this.game = game;
        this.speed = 100;
        this.stealth = 100;
        this.health = 100;
        this.strength = 100;
        this.avg = new int[4];
        
        this.pointsX = new int[4];
        this.pointsY = new int[4];
        this.pointsXAvg = new int[4];
        this.pointsYAvg = new int[4];
        this.ingame = ingame;
        
        this.centerX = centerX;
        this.centerY = centerY;
        
        if(!ingame){
            try {
                avg = mysql.getAverage();

                this.pointsXAvg[0] = (int) ((-STATISTICS_DIMENSION_OVER * avg[0] / MAX_SPEED) / 2 + x+centerX );
                this.pointsXAvg[1] = (int) ((STATISTICS_DIMENSION_OVER  * avg[1] / MAX_STEALTH) / 2 + x+centerX );
                this.pointsXAvg[2] = (int) ((STATISTICS_DIMENSION_OVER  * avg[2] / MAX_STRENGTH) / 2 + x+centerX );
                this.pointsXAvg[3] = (int) ((-STATISTICS_DIMENSION_OVER * avg[3] / MAX_HEALTH) / 2 + x+centerX );

                this.pointsYAvg[0] = (int) ((-STATISTICS_DIMENSION_OVER * avg[0]/ MAX_SPEED) / 2 + y + centerY);
                this.pointsYAvg[1] = (int) ((-STATISTICS_DIMENSION_OVER * avg[1]/ MAX_STEALTH) / 2 + y + centerY);
                this.pointsYAvg[2] = (int) ((STATISTICS_DIMENSION_OVER  * avg[2]/ MAX_STRENGTH) / 2 + y+ centerY);
                this.pointsYAvg[3] = (int) ((STATISTICS_DIMENSION_OVER  * avg[3] / MAX_HEALTH) / 2 + y + centerY);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }else{
        //Close Button
        buttons.add(new Button(PANEL_STATS_X + PANEL_STATS_WIDTH -BUTTON_CLOSE_DIMENSION/2, PANEL_STATS_Y-BUTTON_CLOSE_DIMENSION/2, BUTTON_CLOSE_DIMENSION, BUTTON_CLOSE_DIMENSION,Assets.organismPanel_close));
        
        }
    }

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
    if(ingame){    
        
        if (buttons.get(0).isPressed()) {
            active = false;
            buttons.get(0).setPressed(false);
        }
        this.pointsX[0] = (int) ((-STATISTICS_DIMENSION * speed / MAX_SPEED) / 2 + x+centerX );
        this.pointsX[1] = (int) ((STATISTICS_DIMENSION * stealth / MAX_STEALTH) / 2 + x+centerX );
        this.pointsX[2] = (int) ((STATISTICS_DIMENSION * strength / MAX_STRENGTH) / 2 + x+centerX );
        this.pointsX[3] = (int) ((-STATISTICS_DIMENSION * health / MAX_HEALTH) / 2 + x+centerX );
        
        this.pointsY[0] = (int) ((-STATISTICS_DIMENSION * speed / MAX_SPEED) / 2 + y + centerY);
        this.pointsY[1] = (int) ((-STATISTICS_DIMENSION * stealth / MAX_STEALTH) / 2 + y+centerY);
        this.pointsY[2] = (int) ((STATISTICS_DIMENSION * strength / MAX_STRENGTH) / 2 + y+centerY);
        this.pointsY[3] = (int) ((STATISTICS_DIMENSION * health / MAX_HEALTH) / 2 + y +centerY);
    }else
    {
        this.pointsX[0] = (int) ((-STATISTICS_DIMENSION_OVER * speed / MAX_SPEED) / 2 + x+centerX );
        this.pointsX[1] = (int) ((STATISTICS_DIMENSION_OVER * stealth / MAX_STEALTH) / 2 + x+centerX );
        this.pointsX[2] = (int) ((STATISTICS_DIMENSION_OVER * strength / MAX_STRENGTH) / 2 + x+centerX );
        this.pointsX[3] = (int) ((-STATISTICS_DIMENSION_OVER * health / MAX_HEALTH) / 2 + x+centerX );
        
        this.pointsY[0] = (int) ((-STATISTICS_DIMENSION_OVER * speed / MAX_SPEED) / 2 + y + centerY);
        this.pointsY[1] = (int) ((-STATISTICS_DIMENSION_OVER * stealth / MAX_STEALTH) / 2 + y+centerY);
        this.pointsY[2] = (int) ((STATISTICS_DIMENSION_OVER * strength / MAX_STRENGTH) / 2 + y+centerY);
        this.pointsY[3] = (int) ((STATISTICS_DIMENSION_OVER * health / MAX_HEALTH) / 2 + y +centerY);

    }

    }
 
    @Override
    public void render(Graphics g) {
        if(!active)
            return;
        
       Graphics2D g2 = (Graphics2D) g;
       
       if(ingame){
            g.drawImage(Assets.statsPanel, PANEL_STATS_X, PANEL_STATS_Y, PANEL_STATS_WIDTH, PANEL_STATS_HEIGHT, null);

            g.setColor(BLUE_GREEN_COLOR);
            g.setFont(f.getFontEvolve().deriveFont(17f));
            g.setColor(Color.WHITE);
            g.setFont(f.getFontEvolve().deriveFont(17f));
            g.drawString("Speed", x +60, y + 157 );
            g.drawString("Strength", x +290 ,y +157);
            g.drawString("Stealth",  x +65, y + 340  );
            g.drawString("Max Health",  x +310, y + 340 );
            g.setFont(f.getFontEvolve().deriveFont(30f));
            g.drawString(game.getOrganisms().getSpeciesName(), x +465, y+100);

            g.setFont(f.getFontEvolve().deriveFont(25f));
            g.drawString("Game Duration", x +430, y+175);
            g.drawString(Integer.toString(game.getClock().getSeconds()) , x +430, y+220);
            g.drawString("Maximum Generation", x +430, y+277);
            g.drawString(Integer.toString(game.getOrganisms().getMaxGeneration()), x +430, y+322);
            g.drawString("Maximum Intelligence", x +430, y+379);
            g.drawString(Integer.toString(game.getOrganisms().getMaxIntelligence()), x +430, y+424);
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).render(g);
            }
                   
            g.setColor(new Color(9,255,200, 170));
            g.fillPolygon(pointsX, pointsY, 4);

            g2.setStroke(new BasicStroke(2));

            g2.drawLine(pointsX[0],pointsY[0],pointsX[1],pointsY[1]);
            g2.drawLine(pointsX[1],pointsY[1],pointsX[2],pointsY[2]);
            g2.drawLine(pointsX[2],pointsY[2],pointsX[3],pointsY[3]);
            g2.drawLine(pointsX[3],pointsY[3],pointsX[0],pointsY[0]);
        
       } else{
            g.setColor(new Color(255,211,0, 170));
            g.fillPolygon(pointsXAvg, pointsYAvg, 4);
            g2.setColor(new Color(255,211,0));
            g2.setStroke(new BasicStroke(2));
            
            g2.drawLine(pointsXAvg[0],pointsYAvg[0],pointsXAvg[1],pointsYAvg[1]);
            g2.drawLine(pointsXAvg[1],pointsYAvg[1],pointsXAvg[2],pointsYAvg[2]);
            g2.drawLine(pointsXAvg[2],pointsYAvg[2],pointsXAvg[3],pointsYAvg[3]);
            g2.drawLine(pointsXAvg[3],pointsYAvg[3],pointsXAvg[0],pointsYAvg[0]);
            
            g.setColor(new Color(9,255,200, 170));
            g.fillPolygon(pointsX, pointsY, 4);

            g2.setStroke(new BasicStroke(2));

            g2.drawLine(pointsX[0],pointsY[0],pointsX[1],pointsY[1]);
            g2.drawLine(pointsX[1],pointsY[1],pointsX[2],pointsY[2]);
            g2.drawLine(pointsX[2],pointsY[2],pointsX[3],pointsY[3]);
            g2.drawLine(pointsX[3],pointsY[3],pointsX[0],pointsY[0]);
            
            g.setFont(f.getFontEvolve().deriveFont(17f));
            g.setColor(Color.WHITE);
            g.setFont(f.getFontEvolve().deriveFont(17f));
            g.drawString("Speed", 55, 248 );
            g.drawString("Strength", 335 , 248);
            g.drawString("Stealth", 49 , 467);
            g.drawString("Max Health", 333 , 467);
            
            
            
            
            
            g2.setStroke(new BasicStroke(2));
            
            g.setColor(new Color(255,211,0, 170));
            g.fillOval(73, 523, 20, 20);
            g2.setColor(new Color(255,211,0));
            g2.drawOval(72, 522, 21, 21);
            
            g.setColor(Color.WHITE);
            g.drawString("Average", 101, 538 );
            
            g.setColor(new Color(9,255,200, 170));
            g.fillOval(252, 523, 20, 20);
            
            g2.setColor(new Color(9,255,200));
            g2.drawOval(251, 522, 21, 21);
            
            g.setColor(Color.WHITE);
            g.drawString("Your Species", 280, 538 );
       }


        
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStealth() {
        return stealth;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
