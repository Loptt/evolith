package evolith.menus;

import evolith.game.Item;
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

    protected boolean pressed;
    protected boolean active;
    protected boolean hover;
    protected boolean enlarge;
    protected final int enlargement = 40;

    protected BufferedImage imgOn;
    protected BufferedImage imgOff;
    
    public Button(int x, int y, int width, int height, BufferedImage on, BufferedImage off, boolean enlarge) {
        super(x,y,width,height);
        pressed = false;
        active = false;
        imgOn = on;
        imgOff = off;
        this.enlarge = enlarge;
    }

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
                    g.drawImage(imgOn, x -4, y - 4, width + 8, height + 8, null);
                } else if (imgOn != null) {
                    g.drawImage(imgOn, x-enlargement/2, y-enlargement/2, width+enlargement, height+enlargement, null);
                } else {
                    g.drawImage(imgOff, x-enlargement/2, y-enlargement/2, width+enlargement, height+enlargement, null);
                }
            } else if (pressed) {
                if (!enlarge) {
                    g.drawImage(imgOn, x, y, width, height, null);
                } else if (imgOn != null) {
                    g.drawImage(imgOn, x-enlargement/2, y-enlargement/2, width+enlargement, height+enlargement, null);
                } else {
                    g.drawImage(imgOff, x-enlargement/2, y-enlargement/2, width+enlargement, height+enlargement, null);
                }
            } else {
                g.drawImage(imgOff, x, y, width, height, null);
            }
        } else {
            //no image to show
        }
    }
}