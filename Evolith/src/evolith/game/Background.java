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

    private BufferedImage imageDay;     //The day background image
    private BufferedImage imageNight;   //The night background image

    private int cameraWidth, cameraHeight; //The camera width and height
    private int width, height;              //The background width and height
    private boolean night;                  //The night state

    /**
     *
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
     * @param x starting x coordinate
     * @param y starting y coordinate
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
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the background
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * to get the current background
     * @return background image
     */
    public BufferedImage getCurrentFullBackground() {
        if (!night) {
            return imageDay;
        } else {
            return imageNight;
        }
    }

    /**
     * set the night state
     * @param night night boolean
     */
    public void setNight(boolean night) {
        this.night = night;
    }
    
    /**
     * set the day image
     * @param imageDay day buffered image
     */
    public void setImageDay(BufferedImage imageDay) {
        this.imageDay = imageDay;
    }
    
    /**
     * set the night image
     * @param imageNight night buffered image
     */
    public void setImageNight(BufferedImage imageNight) {
        this.imageNight = imageNight;
    }
}
