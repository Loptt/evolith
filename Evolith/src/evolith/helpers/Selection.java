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
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Selection {
    public boolean active;  //acitve flag
    public int currX;       //current mouse x
    public int currY;       //current mouse y
    
    public int startX;      //selection start x
    public int startY;      //selection start y
    
    public Rectangle sel;   //Selection rectangle
    
    public Game game;       //Game object
    
    /**
     * Selection constructor
     * @param game game object
     */
    public Selection(Game game) {
        active = false;
        this.game = game;
        sel = new Rectangle();
    }
    
    /**
     * Activate selection at coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public void activate(int x, int y) {
        startX = x;
        startY = y;
        active = true;
    }
    
    /**
     * Turn off selection
     */
    public void deactivate() {
        sel.setRect(0,0,0,0);
        active = false;
    }
    
    /**
     * to check if active
     * @return 
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     * To set active
     * @param active 
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * to get the rectangle selection
     * @return 
     */
    public Rectangle getSel() {
        return sel;
    }
    
    /**
     * to set the rectangle selection
     * @param sel new rectangle
     */
    public void setSel(Rectangle sel) {
        this.sel = sel;
    }
    
    /**
     * to tick the selection
     */
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
    
    /**
     * to render the selection
     * @param g graphics
     */
    public void render(Graphics g) {
        if (active) {
            g.setColor(Color.white);
            g.drawRect(game.getCamera().getRelX(sel.x), game.getCamera().getRelY(sel.y), sel.width, sel.height);
        }
    }    
    
}
