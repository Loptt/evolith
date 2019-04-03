/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camerapanningtest;

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
    
    public int getTransX(int absX) {
        return absX - x;
    }
    
    public int getTransY(int absY) {
        return absY - y;
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
