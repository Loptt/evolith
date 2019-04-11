package evolith;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
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
    
    public Button(int x, int y, int width, int height, BufferedImage off) {
        super(x,y,width,height);
        pressed = false;
        active = false;
        imgOff = off;
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
        if (imgOff != null) {
            if (active || hover) {
                if (!enlarge) {
                    g.drawImage(imgOn, x, y, width, height, null);
                } else {
                    g.drawImage(imgOff, x-15, y-15, width+30, height+30, null);
                }
            } else if (pressed) {
                if (!enlarge) {
                    g.drawImage(imgOn, x, y, width, height, null);
                } else {
                    g.drawImage(imgOff, x-15, y-15, width+30, height+30, null);
                }
            } else {
                g.drawImage(imgOff, x, y, width, height, null);

            }
        } else {
            //no image to show
        }
    }
}