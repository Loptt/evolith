/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camerapanningtest;

import java.awt.image.BufferedImage;

/**
 *
 * @author charles
 */
public class Background {
    BufferedImage image;
    
    int width, height;
    
    public Background(BufferedImage img, int width, int height) {
        image = img;
        this.width = width;
        this.height = height;
    }
    
    BufferedImage getBackground(int x, int y) {
        return image.getSubimage(x, y, width, height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    
}
