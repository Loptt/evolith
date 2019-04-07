/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orgmove;

/**
 *
 * @author charles
 */
public class Camera {
    private int width, height, x, y;
    
    public Camera(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public int getRelX(int absX) {
        return absX - x;
    }
    
    public int getRelY(int absY) {
        return absY - y;
    }
    
    public int getAbsX(int relX) {
        return relX + x;
    }
    
    public int getAbsY(int relY) {
        return relY + y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
