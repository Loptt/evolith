
package evolith;

import java.awt.image.BufferedImage;


/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Assets {
    
    public static BufferedImage background; //background image
    public static BufferedImage player; // player image
    public static BufferedImage plant; //plant image
    
    public static BufferedImage start; // start image
    public static BufferedImage startInstructions; // start menu instruction image
    public static BufferedImage startPlay; // start play button image
    
    /**
     * Initalizes the assets and links to the image folder
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/background2.png");
        player = ImageLoader.loadImage("/images/purple organism.png");
        plant = ImageLoader.loadImage("/images/food.png");
        
        start = ImageLoader.loadImage("/images/start.png");
        startInstructions = ImageLoader.loadImage("/images/start instructions.png");
        startPlay = ImageLoader.loadImage("/images/start play.png");

    }
}

