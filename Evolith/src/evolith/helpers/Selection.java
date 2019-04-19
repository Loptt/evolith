/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.helpers;

import evolith.game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author charles
 */
public class Selection {
    public boolean active;
    public int currX;
    public int currY;
    
    public int startX;
    public int startY;
    
    public Rectangle sel;
    
    public Game game;

    public Selection(Game game) {
        active = false;
        this.game = game;
        sel = new Rectangle();
    }
    
    public void activate(int x, int y) {
        startX = x;
        startY = y;
        active = true;
    }
    
    public void deactivate() {
        sel.setRect(0,0,0,0);
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Rectangle getSel() {
        return sel;
    }

    public void setSel(Rectangle sel) {
        this.sel = sel;
    }

    public void tick() {
        if (!active) {
            return;
        }
        
        currX = game.getCamera().getAbsX(game.getMouseManager().getX());
        currY = game.getCamera().getAbsY(game.getMouseManager().getY());
        
        int originX = currX < startX ? currX : startX;
        int originY = currY < startY ? currY : startY;
        
        int newWidth = Math.abs(currX - startX);
        int newHeight = Math.abs(currY - startY);
        
        sel.setRect(originX, originY, newWidth, newHeight);
        //System.out.println("X:  " + currX  + "  Y:   " + currY);
    }

    public void render(Graphics g) {
        if (active) {
            g.setColor(Color.BLACK);
            g.drawRect(game.getCamera().getRelX(sel.x), game.getCamera().getRelY(sel.y), sel.width, sel.height);
        }
    }
    
    
}
