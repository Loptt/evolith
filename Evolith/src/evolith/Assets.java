package evolith;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    public static BufferedImage setupSpeciesBackground; // start play button image
    
    public static BufferedImage blueOption;
    public static BufferedImage yellowOption;
    public static BufferedImage redOption;
    public static BufferedImage purpleOption;
    
    public static BufferedImage playOn;
    public static BufferedImage playOff;
    public static BufferedImage hoverImage;
    
    public static ArrayList<BufferedImage> orgColors;
    
    public static ArrayList<ArrayList<BufferedImage>> buttonBar = new ArrayList<ArrayList<BufferedImage>>();


    /**
     * Initalizes the assets and links to the image folder
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/bigbackground.png");
        player = ImageLoader.loadImage("/images/purple organism.png");
        plant = ImageLoader.loadImage("/images/food.png");

        start = ImageLoader.loadImage("/images/start.png");
        startInstructions = ImageLoader.loadImage("/images/start instructions.png");
        startPlay = ImageLoader.loadImage("/images/start play.png");
        setupSpeciesBackground = ImageLoader.loadImage("/images/setup_species_background.png");
        
        blueOption = ImageLoader.loadImage("/images/blueoption.png");
        yellowOption = ImageLoader.loadImage("/images/yellowoption.png");
        redOption = ImageLoader.loadImage("/images/redoption.png");
        purpleOption = ImageLoader.loadImage("/images/purpleoption.png");
        playOn = ImageLoader.loadImage("/images/playon.png");
        playOff = ImageLoader.loadImage("/images/playoff.png");
        hoverImage = ImageLoader.loadImage("/images/STATS BARV1.png");
        
        //Top Bar in 0,0
        ArrayList<BufferedImage> a1 = new ArrayList<BufferedImage>();
        a1.add(ImageLoader.loadImage("/images/topbar.png"));
        buttonBar.add(a1);
        //Water buttons in 1,0 (off) and 1,1 (on)
        ArrayList<BufferedImage> a2 = new ArrayList<BufferedImage>();
        a2.add(ImageLoader.loadImage("/images/wateroff.png"));
        a2.add(ImageLoader.loadImage("/images/wateron.png"));
        buttonBar.add(a2);
        //Food buttons in 2,0 (off) and 2,1 (on)
        ArrayList<BufferedImage> a3 = new ArrayList<BufferedImage>();
        a3.add(ImageLoader.loadImage("/images/foodoff.png"));
        a3.add(ImageLoader.loadImage("/images/foodon.png"));
        buttonBar.add(a3);
        //Fight buttons in 3,0 (off) and 3,1 (on)
        ArrayList<BufferedImage> a4 = new ArrayList<BufferedImage>();
        a4.add(ImageLoader.loadImage("/images/fightoff.png"));
        a4.add(ImageLoader.loadImage("/images/fighton.png"));
        buttonBar.add(a4);
        
        orgColors = new ArrayList<>();
        
        orgColors.add(ImageLoader.loadImage("/images/pinkorganism.png"));
        orgColors.add(ImageLoader.loadImage("/images/purpleorganism.png"));
        orgColors.add(ImageLoader.loadImage("/images/bluegreenorganism.png"));
        orgColors.add(ImageLoader.loadImage("/images/orangeorganism.png"));

    }
}
