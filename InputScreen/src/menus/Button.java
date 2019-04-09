/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public class Button extends Item {

    private boolean pressed;
    private boolean active;
    private boolean hover;
    private boolean enlarge;

    private BufferedImage imgOn;
    private BufferedImage imgOff;

    public Button(int x, int y, int width, int height, BufferedImage on, BufferedImage off) {
        super(x,y,width,height);
        pressed = false;
        active = false;
        imgOn = on;
        imgOff = off;
        enlarge = false;
    }
    
    public Button(int x, int y, int width, int height, BufferedImage on) {
        super(x,y,width,height);
        pressed = false;
        active = false;
        imgOn = on;
        enlarge = true;
    }
    
    public Button(int x, int y, int width, int height) {
        super(x,y,width,height);
        pressed = false;
        active = false;
        enlarge = false;

    }
    
    public boolean hasMouse(int x, int y) {
        return getPerimeter().contains(x, y);
    }
   
    public boolean isPressed(){
        return pressed;
    }
 
    public boolean isActive(){
        return active;
    }

    public boolean isHover() {
        return hover;
    }
    
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }
    
    public void tick(){
        
    }

    @Override
    public void render(Graphics g) {
    System.out.println("ASDJASDOIJ");
        if (hover || active) { 
            g.drawImage(imgOn, x, y, width, height, null);
        } else if (!enlarge) {
            g.drawImage(imgOff, x, y, width, height, null);
        } else if (imgOn != null){
            g.drawImage(imgOn, x, y, width+30, height+30, null);
        } else {
            g.fillRect(x, y, width, height);
        }
    }
}
