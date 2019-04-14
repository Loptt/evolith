/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hover;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author charles
 */
public class Player extends Item {
    
    private Game game;
    private Point point;
    private int maxVel;
    private int acc;
    private int xVel;
    private int yVel;

    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        point = new Point(500, 500);
        maxVel = 3;
        xVel = 0;
        yVel = 0;
        acc = 1;
    }

    @Override
    public void tick() {
        /*if (game.getMouseManager().isIzquierdo()) {
            point.setLocation(game.getCamera().getAbsX(game.getMouseManager().getX() - getWidth() / 2),
                    game.getCamera().getAbsY(game.getMouseManager().getY() - getHeight() / 2));
        }*/
        
        if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 25) {
            if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 15) {
                if (Math.abs((int) point.getX() - x) < 5 && Math.abs((int) point.getY() - y) < 5) {
                    maxVel = 0;
                } else {
                    maxVel = 1;
                }
            } else {
                maxVel = 2;
            }
        } else {
            maxVel = 3;
        }
        
        
        if ((int) point.getX() > x) {
            xVel += acc;
        } else {
            xVel -= acc; 
        }
        
        if ((int) point.getY() > y) {
            yVel += acc;
        } else {
            yVel -= acc; 
        }
        
        if (xVel > maxVel) {
            xVel = maxVel;
        }
        
        if (xVel < maxVel * -1) {
            xVel = maxVel * -1;
        }
        
        if (yVel > maxVel) {
            yVel = maxVel;
        }
        
        if (yVel < maxVel * -1) {
            yVel = maxVel * -1;
        }
        
        x += xVel;
        y += yVel;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
