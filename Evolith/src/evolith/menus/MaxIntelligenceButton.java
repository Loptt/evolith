
package evolith.menus;

import evolith.entities.Organism;
import static evolith.helpers.Commons.MAX_INTELLIGENCE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class MaxIntelligenceButton extends Button {
    
    private Organism org;   // organism with the max intelligence
    private int yOff;       //intelligence bar y offset
    /**
     * Constructor of the maximum intelligence button with on and off state
     * @param x
     * @param y
     * @param width
     * @param height
     * @param off
     * @param on
     * @param org 
     */
    public MaxIntelligenceButton(int x, int y, int width, int height, BufferedImage off, BufferedImage on, Organism org) {
        super(x, y, width, height, off, on);
        this.org = org;
    }
    /**
     * Constructor with only the off status
     * @param x
     * @param y
     * @param width
     * @param height
     * @param off 
     */
    public MaxIntelligenceButton(int x, int y, int width, int height, BufferedImage off) {
        super(x, y, width, height, off);
    }
    /**
     * To control the mouse with the left and right click and set pressed to true
     */
    public void applyMouse() {
        pressed = true;
    }
    /**
     * To tick the button
     */
    @Override
    public void tick() {
        super.tick();
    }
    /**
     * To render the button
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        //to set the new color and background of the button
        g.setColor(new Color(255,215,0));
        g.fillRect(x + 21, y + yOff, (int) 109 * org.getIntelligence() / MAX_INTELLIGENCE, 20);
    }
    /**
     * To set the organism with the max intelligence
     * @param org 
     */
    public void setOrg(Organism org) {
        this.org = org;
    }
    /**
     * To get the organism with he max intelligence
     * @return 
     */
    public Organism getOrg() {
        return org;
    }
    /**
     * To set the y off
     * @param yOff 
     */
    public void setyOff(int yOff) {
        this.yOff = yOff;
    }
    /**
     * To get the status of y
     * @return 
     */
    public int getyOff() {
        return yOff;
    }
}
