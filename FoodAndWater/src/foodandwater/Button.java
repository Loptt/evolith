/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodandwater;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author victor
 */
public class Button{

   private int x;
   private int y;
   private int width;
   private int height;
   private boolean pressed;
   private boolean active;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        pressed = false;
        active = false;
    }
    
    public boolean hasMouse(int x, int y) {
        return getPerimeter().contains(x, y);
    }
    
    private Rectangle getPerimeter() {
        return new Rectangle(x, y, width, height);
    }
   
    public boolean isPressed(){
        return pressed;
    }
 
    public boolean isActive(){
        return active;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setActive(boolean active){
        this.active = active;
    }
    
    public void tick(){
        
    }
    
    public void render(Graphics g, BufferedImage img) {
        g.drawImage(img, x, y, width, height, null);
    }
}
