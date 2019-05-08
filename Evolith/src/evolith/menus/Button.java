package evolith.menus;

import evolith.game.Item;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Button extends Item {

    protected boolean pressed;          // to check if the button is pressed
    protected boolean active;           // to check if the button is active
    protected boolean hover;            // to check if the mouse is hover
    protected boolean enlarge;          // to check if the button is enlarged
    protected final int enlargement = 40;   // to set the enlargement in pixels

    protected BufferedImage imgOn;      // asset of the button when is on
    protected BufferedImage imgOff;     // asset of the button when is off
    
    /**
     * Constructor of the button with both on and off states with enlargement
     * @param x
     * @param y
     * @param width
     * @param height
     * @param on
     * @param off
     * @param enlarge 
     */
    public Button(int x, int y, int width, int height, BufferedImage on, BufferedImage off, boolean enlarge) {
        super(x,y,width,height);
        pressed = false;
        active = false;
        imgOn = on;
        imgOff = off;
        this.enlarge = enlarge;
    }
    /**
     * Constructor of the button with both on and off states with enlargement
     * @param x
     * @param y
     * @param width
     * @param height
     * @param on
     * @param off 
     */
    public Button(int x, int y, int width, int height, BufferedImage on, BufferedImage off) {
        super(x,y,width,height);
        pressed = false;
        active = false;
        imgOn = on;
        imgOff = off;
        enlarge = false;
    }
    /**
     * Constructor with only the off status
     * @param x
     * @param y
     * @param width
     * @param height
     * @param off 
     */
    public Button(int x, int y, int width, int height, BufferedImage off) {
        super(x,y,width,height);
        pressed = false;
        active = false;
        imgOff = off;
        enlarge = true;
    }
    /**
     * Basic Constructor
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public Button(int x, int y, int width, int height) {
        super(x,y,width,height);
        pressed = false;
        active = false;
        enlarge = false;

    }
    /**
     * To check if the button is pressed
     * @return pressed
     */
    public boolean isPressed(){
        return pressed;
    }
    /**
     * To check if the button is active
     * @return active
     */
    public boolean isActive(){
        return active;
    }
    /**
     * To check if the hover is active
     * @return hover
     */
    public boolean isHover() {
        return hover;
    }
    /**
     * To set the button to pressed
     * @param pressed 
     */
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
    /**
     * To set if the active 
     * @param active 
     */
    public void setActive(boolean active){
        this.active = active;
    }
    /**
     * To set the hover to the status
     * @param hover 
     */
    public void setHover(boolean hover) {
        this.hover = hover;
    }
    /**
     * To tick the button
     */
    public void tick(){
        
    }
    /**
     * To render the button
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        //If the button has an asset assigned verify if it is active or hovered
        if (imgOff != null) {
            if (active || hover) {
                //enlarge when active or hovered
                if (!enlarge) {
                    g.drawImage(imgOn, x -4, y - 4, width + 8, height + 8, null);
                } else if (imgOn != null) {
                    g.drawImage(imgOn, x-enlargement/2, y-enlargement/2, width+enlargement, height+enlargement, null);
                } else {
                    g.drawImage(imgOff, x-enlargement/2, y-enlargement/2, width+enlargement, height+enlargement, null);
                }
            } else if (pressed) {
                //if the button is pressed also enlarge it
                if (!enlarge) {
                    g.drawImage(imgOn, x, y, width, height, null);
                } else if (imgOn != null) {
                    g.drawImage(imgOn, x-enlargement/2, y-enlargement/2, width+enlargement, height+enlargement, null);
                } else {
                    g.drawImage(imgOff, x-enlargement/2, y-enlargement/2, width+enlargement, height+enlargement, null);
                }
            } else {
                //just render the image
                g.drawImage(imgOff, x, y, width, height, null);
            }
        } else {
            //no image to show
        }
    }
}