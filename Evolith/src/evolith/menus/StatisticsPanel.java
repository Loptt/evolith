/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.game.Game;
import evolith.helpers.Commons;
import static evolith.helpers.Commons.BLUE_GREEN_COLOR;
import evolith.helpers.FontLoader;
import evolith.menus.Menu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author ErickFrank
 */
public class StatisticsPanel extends Menu implements Commons {

    private boolean connected;
    private FontLoader f;
    ArrayList<Point> points;
    ArrayList<Point> pointsInner;

    private int pointsX[];
    private int pointsY[];

    private Game game;
    private int speed;
    private int stealth;
    private int health;
    private int strength;
    private Point pMiddle;

    public StatisticsPanel(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        try {
            f = new FontLoader();
            this.connected = true;
        } catch (Exception e) {
            this.connected = false;
            System.out.println("Statistics Panel Expection:" + e.getMessage());
        }
        this.speed = 50;
        this.stealth = 40;
        this.health = 20;
        this.strength = 30;
        this.pMiddle = new Point(x + STATISTICS_DIMENSION / 2 - STATISTICS_POINT_DIMENSION / 2, y + STATISTICS_DIMENSION / 2 - STATISTICS_POINT_DIMENSION / 2);
        this.points = new ArrayList<Point>(4);

        this.points.add(new Point(x - STATISTICS_POINT_DIMENSION / 2, y - STATISTICS_POINT_DIMENSION / 2));
        this.points.add(new Point(x + STATISTICS_DIMENSION - STATISTICS_POINT_DIMENSION / 2, y - STATISTICS_POINT_DIMENSION / 2));
        this.points.add(new Point(x + STATISTICS_DIMENSION - STATISTICS_POINT_DIMENSION / 2, y + STATISTICS_DIMENSION - STATISTICS_POINT_DIMENSION / 2));
        this.points.add(new Point(x - STATISTICS_POINT_DIMENSION / 2, y + STATISTICS_DIMENSION - STATISTICS_POINT_DIMENSION / 2));

        this.pointsX = new int[4];

        this.pointsX[0] = (int) ((-STATISTICS_DIMENSION * speed / MAX_SPEED) / 2 + x + STATISTICS_DIMENSION / 2);
        this.pointsX[1] = (int) ((STATISTICS_DIMENSION * stealth / MAX_STEALTH) / 2 + x + STATISTICS_DIMENSION / 2);
        this.pointsX[2] = (int) ((STATISTICS_DIMENSION * strength / MAX_STRENGTH) / 2 + x + STATISTICS_DIMENSION / 2);
        this.pointsX[3] = (int) ((-STATISTICS_DIMENSION * health / MAX_HEALTH) / 2 + x + STATISTICS_DIMENSION / 2);

        this.pointsY = new int[4];

        this.pointsY[0] = (int) ((-STATISTICS_DIMENSION * speed / MAX_SPEED) / 2 + y + STATISTICS_DIMENSION / 2);
        this.pointsY[1] = (int) ((-STATISTICS_DIMENSION * stealth / MAX_STEALTH) / 2 + y + STATISTICS_DIMENSION / 2);
        this.pointsY[2] = (int) ((STATISTICS_DIMENSION * strength / MAX_STRENGTH) / 2 + y + STATISTICS_DIMENSION / 2);
        this.pointsY[3] = (int) ((STATISTICS_DIMENSION * health / MAX_HEALTH) / 2 + y + STATISTICS_DIMENSION / 2);

        this.pointsInner = new ArrayList<Point>(4);
        for (int i = 0; i < 4; i++) {
            this.pointsInner.add(new Point(pointsX[i] - STATISTICS_POINT_DIMENSION / 2, pointsY[i] - STATISTICS_POINT_DIMENSION / 2));
        }
        //this.pointsInner.add(new Point(pointsX[0]-STATISTICS_POINT_DIMENSION/2, pointsY[0]-STATISTICS_POINT_DIMENSION/2));
        //this.pointsInner.add(new Point(pointsX[1]-STATISTICS_POINT_DIMENSION/2, pointsY[1]-STATISTICS_POINT_DIMENSION/2));
        //this.pointsInner.add(new Point(pointsX[2]-STATISTICS_POINT_DIMENSION/2, pointsY[2]-STATISTICS_POINT_DIMENSION/2));
        //this.pointsInner.add(new Point(pointsX[3]-STATISTICS_POINT_DIMENSION/2, pointsY[3]-STATISTICS_POINT_DIMENSION/2));

    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {

        g.setColor(BLUE_GREEN_COLOR);
        g.setFont(f.getFontEvolve().deriveFont(20f));

        //Right Top
        //g.fillOval( (int) points.get(0).getX(),(int)points.get(0).getY(), STATISTICS_POINT_DIMENSION,STATISTICS_POINT_DIMENSION);  
        //Left-Top
        //g.fillOval( (int) points.get(1).getX(),(int)points.get(1).getY(), STATISTICS_POINT_DIMENSION,STATISTICS_POINT_DIMENSION);  
        //Right-Bottom
        //g.fillOval( (int) points.get(2).getX(),(int)points.get(2).getY(), STATISTICS_POINT_DIMENSION,STATISTICS_POINT_DIMENSION);  
        //Left-Bottom
        //g.fillOval( (int) points.get(3).getX(),(int)points.get(3).getY(), STATISTICS_POINT_DIMENSION,STATISTICS_POINT_DIMENSION);  
        for (int i = 0; i < 4; i++) {
            g.fillOval((int) points.get(i).getX() - STATISTICS_POINT_DIMENSION / 2, (int) points.get(i).getY() - STATISTICS_POINT_DIMENSION / 2, STATISTICS_POINT_DIMENSION, STATISTICS_POINT_DIMENSION);
        }
        g.drawLine((int) points.get(0).getX(), (int) points.get(0).getY(), (int) points.get(1).getX(), (int) points.get(1).getY());
        g.drawLine((int) points.get(1).getX(), (int) points.get(1).getY(), (int) points.get(2).getX(), (int) points.get(2).getY());
        g.drawLine((int) points.get(2).getX(), (int) points.get(2).getY(), (int) points.get(3).getX(), (int) points.get(3).getY());
        g.drawLine((int) points.get(3).getX(), (int) points.get(3).getY(), (int) points.get(0).getX(), (int) points.get(0).getY());

        g.setColor(BLUE_GREEN_COLOR);

        //g.fillPolygon(pointsX, pointsY, 4);



        //Right Top Speed
        g.setColor(Color.MAGENTA);
        g.fillOval((int) pointsInner.get(0).getX(), (int) pointsInner.get(0).getY(), STATISTICS_POINT_DIMENSION, STATISTICS_POINT_DIMENSION);
        g.drawLine(pMiddle.x, pMiddle.y, pointsX[0], pointsY[0]);
        //Left-Top Stealth
        g.setColor(Color.ORANGE);
        g.fillOval((int) pointsInner.get(1).getX(), (int) pointsInner.get(1).getY(), STATISTICS_POINT_DIMENSION, STATISTICS_POINT_DIMENSION);
         g.drawLine(pMiddle.x, pMiddle.y, pointsX[1], pointsY[1]);
        //Right-Bottom Strength
        g.setColor(Color.YELLOW);
        g.fillOval((int) pointsInner.get(2).getX(), (int) pointsInner.get(2).getY(), STATISTICS_POINT_DIMENSION, STATISTICS_POINT_DIMENSION);
         g.drawLine(pMiddle.x, pMiddle.y, pointsX[2], pointsY[2]);
        //Left-Bottom Health
        g.setColor(Color.RED);
        g.fillOval((int) pointsInner.get(3).getX(), (int) pointsInner.get(3).getY(), STATISTICS_POINT_DIMENSION, STATISTICS_POINT_DIMENSION);
         g.drawLine(pMiddle.x, pMiddle.y, pointsX[3], pointsY[3]);
         
                 g.setColor(Color.BLACK);
        
        g.fillOval(pMiddle.x- STATISTICS_POINT_DIMENSION / 2, pMiddle.y- STATISTICS_POINT_DIMENSION / 2, STATISTICS_POINT_DIMENSION, STATISTICS_POINT_DIMENSION);

    }

}
