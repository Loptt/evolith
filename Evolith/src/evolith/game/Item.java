package evolith.game;

import evolith.helpers.Circle;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public abstract class Item {

    protected int x; //x position
    protected int y; //y position
    protected int width;
    protected int height;
    
    protected Circle radius;

    /**
     * To create a new item
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Item(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        
        radius = new Circle(x + width/2, y + height /2, width/2);
    }

    /**
     * To get a rectangle object with current position and size
     *
     * @return
     */
    public Rectangle getPerimeter() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * To check if current item intersects with another item
     *
     * @param item
     * @return
     */
    public boolean intersects(Item item) {
        return getPerimeter().intersects(item.getPerimeter());
    }
    
    /**
     * TI check if the item intersects a rectangle object
     * @param r the rectangle
     * @return true if intersection occurs
     */
    public boolean intersects(Rectangle r) {
        return getPerimeter().intersects(r);
    }
    
    /**
     * Check if the item has the mouse over it
     * @param x mouse x position
     * @param y mouse y position
     * @return true if the item has the mouse
     */
    public boolean hasMouse(int x, int y) {
        return getPerimeter().contains(x, y);
    }

    /**
     * To get x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * To get y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * To set x
     *
     * @param x x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * to set y
     *
     * @param y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * to get width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * to set width
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * to set height
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * to set width
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * to get the radius
     * @return radius
     */
    public Circle getRadius() {
        return radius;
    }

    /**
     * To tick the item
     */
    public abstract void tick();

    /**
     * To render the item
     *
     * @param g graphics
     */
    public abstract void render(Graphics g);
}
