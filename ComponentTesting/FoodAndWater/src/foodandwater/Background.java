/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodandwater;

import java.awt.image.BufferedImage;

/**
 *
 * @author charles
 */
public class Background {
    BufferedImage image;
    
    int cameraWidth, cameraHeight;
    int width, height;
    
    public Background(BufferedImage img, int width, int height, int cameraWidth, int cameraHeight) {
        image = img;
        this.width = width;
        this.height = height;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
    }
    
    BufferedImage getBackground(int x, int y) {
        return image.getSubimage(x, y, cameraWidth, cameraHeight);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
