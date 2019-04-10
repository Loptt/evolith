package evolith;

import java.awt.image.BufferedImage;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Background {
    BufferedImage image;
    
    int cameraWidth, cameraHeight;
    int width, height;
    
    /**
     * 
     * @param img
     * @param width
     * @param height
     * @param cameraWidth
     * @param cameraHeight 
     */
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
