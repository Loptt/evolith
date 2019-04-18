package evolith.game;

import evolith.engine.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Background {

    private BufferedImage imageDay;
    private BufferedImage imageNight;

    private int cameraWidth, cameraHeight;
    private int width, height;
    private boolean night;

    /**
     *
     * @param img
     * @param width
     * @param height
     * @param cameraWidth
     * @param cameraHeight
     */
    public Background(int width, int height, int cameraWidth, int cameraHeight) {
        imageDay = Assets.backgroundDay;
        imageNight = Assets.backgroundNight;
        this.width = width;
        this.height = height;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
    }

    /**
     * To get the background subimage depending on the camera height and width
     *
     * @param x
     * @param y
     * @return
     */
    public BufferedImage getBackground(int x, int y) {
        if (!night) { 
            return imageDay.getSubimage(x, y, cameraWidth, cameraHeight);
        } else {
            return imageNight.getSubimage(x, y, cameraWidth, cameraHeight);
        }
    }

    /**
     * To get the width of the background
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the background
     *
     * @return
     */
    public int getHeight() {
        return height;
    }
    
    public BufferedImage getCurrentFullBackground() {
        if (!night) {
            return imageDay;
        } else {
            return imageNight;
        }
    }
}
