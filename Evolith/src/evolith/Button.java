
package evolith;

import java.awt.Rectangle;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
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
    
}
