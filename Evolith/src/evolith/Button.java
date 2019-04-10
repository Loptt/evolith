package evolith;

import java.awt.Rectangle;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Button {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean pressed; // boolean to determine if paused
    private boolean active;  // boolean to determine if active

    /**
     * Initalizes the button with an specific x, y and width and height.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        pressed = false;
        active = false;
    }

    /**
     * If the mouse position is contained in the button
     *
     * @param x
     * @param y
     * @return
     */
    public boolean hasMouse(int x, int y) {
        return getPerimeter().contains(x, y);
    }

    /**
     * Returns a rectangle with the perimieter at that time
     *
     * @return Rectangle
     */
    private Rectangle getPerimeter() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * To get the status if it is pressed
     *
     * @return pressed
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * To get the status if it is active
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * To get the X
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * To get the Y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * To set the x
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * To set the y
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * To get the height of the button
     *
     * @return height
     */

    public int getHeight() {
        return height;
    }

    /**
     * To get the width of the button
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To set the height of the button
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * To set the width of the button
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * To set the button as pressed
     *
     * @param pressed
     */
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    /**
     * To set the button as active
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

}
