package evolith.engine;

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
    public static BufferedImage plant; //plant image
    public static BufferedImage water;

    public static BufferedImage start; // start image
    public static BufferedImage startInstructions; // start menu instruction image
    public static BufferedImage startPlay; // start play button image
    public static BufferedImage setupSpeciesBackground; // start play button image
    
    public static BufferedImage bluegreenOption;
    public static BufferedImage orangeOption;
    public static BufferedImage pinkOption;
    public static BufferedImage purpleOption;
    
    public static BufferedImage playOn;
    public static BufferedImage playOff;
    public static BufferedImage hoverImage;

    public static BufferedImage organismPanel_menu;
    public static BufferedImage organismPanel_close;

    public static BufferedImage minimapFrame;
    
    public static ArrayList<BufferedImage> orgColors;
    
    public static ArrayList<ArrayList<BufferedImage>> mutations = new ArrayList<ArrayList<BufferedImage>>();
    
    public static ArrayList<ArrayList<BufferedImage>> buttonBar = new ArrayList<ArrayList<BufferedImage>>();


    /**
     * Initalizes the assets and links to the image folder
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/backgrounds/bigbackground.png");
        plant = ImageLoader.loadImage("/images/playgraphics/food.png");
        water = ImageLoader.loadImage("/images/playgraphics/water.png");

        start = ImageLoader.loadImage("/images/mainmenu/mainmenu.png");
        startInstructions = ImageLoader.loadImage("/images/mainmenu/maininstructions.png");
        startPlay = ImageLoader.loadImage("/images/mainmenu/mainplay.png");
        setupSpeciesBackground = ImageLoader.loadImage("/images/setupmenu/setup_species_background.png");
        
        bluegreenOption = ImageLoader.loadImage("/images/setupmenu/blueone.png");
        orangeOption = ImageLoader.loadImage("/images/setupmenu/orangeone.png");
        pinkOption = ImageLoader.loadImage("/images/setupmenu/pinkone.png");
        purpleOption = ImageLoader.loadImage("/images/setupmenu/purpleone.png");
        playOn = ImageLoader.loadImage("/images/mainmenu/onplay.png");
        playOff = ImageLoader.loadImage("/images/mainmenu/offplay.png");
        hoverImage = ImageLoader.loadImage("/images/playgraphics/hover_bar.png");
        
        organismPanel_close = ImageLoader.loadImage("/images/panel/closedetails.png");
        organismPanel_menu = ImageLoader.loadImage("/images/panel/detailsmenu.png");
        
        //Top Bar in 0,0
        ArrayList<BufferedImage> a1 = new ArrayList<BufferedImage>();
        a1.add(ImageLoader.loadImage("/images/buttonbar/buttonbarnew.png"));
        buttonBar.add(a1);
        //Water buttons in 1,0 (off) and 1,1 (on)
        ArrayList<BufferedImage> a2 = new ArrayList<BufferedImage>();
        a2.add(ImageLoader.loadImage("/images/buttonbar/wateroff.png"));
        a2.add(ImageLoader.loadImage("/images/buttonbar/wateron.png"));
        buttonBar.add(a2);
        //Food buttons in 2,0 (off) and 2,1 (on)
        ArrayList<BufferedImage> a3 = new ArrayList<BufferedImage>();
        a3.add(ImageLoader.loadImage("/images/buttonbar/foodoff.png"));
        a3.add(ImageLoader.loadImage("/images/buttonbar/foodon.png"));
        buttonBar.add(a3);
        //Fight buttons in 3,0 (off) and 3,1 (on)
        ArrayList<BufferedImage> a4 = new ArrayList<BufferedImage>();
        a4.add(ImageLoader.loadImage("/images/buttonbar/fightoff.png"));
        a4.add(ImageLoader.loadImage("/images/buttonbar/fighton.png"));
        buttonBar.add(a4);
        
        orgColors = new ArrayList<>();
        
        orgColors.add(ImageLoader.loadImage("/images/organisms/pinkorganism.png"));
        orgColors.add(ImageLoader.loadImage("/images/organisms/purpleorganism.png"));
        orgColors.add(ImageLoader.loadImage("/images/organisms/bluegreenorganism.png"));
        orgColors.add(ImageLoader.loadImage("/images/organisms/orangeorganism.png"));
        
        //Strength
        ArrayList<BufferedImage> m1 = new ArrayList<BufferedImage>();
        m1.add(ImageLoader.loadImage("/images/Traits/spines.png"));
        m1.add(ImageLoader.loadImage("/images/Traits/sting.png"));
        m1.add(ImageLoader.loadImage("/images/Traits/claws.png"));
        m1.add(ImageLoader.loadImage("/images/Traits/horns.png"));
        mutations.add(m1);
        //Speed
        ArrayList<BufferedImage> m2 = new ArrayList<BufferedImage>();
        m2.add(ImageLoader.loadImage("/images/Traits/legs1.png"));
        m2.add(ImageLoader.loadImage("/images/Traits/legs2.png"));
        m2.add(ImageLoader.loadImage("/images/Traits/legs3.png"));
        m2.add(ImageLoader.loadImage("/images/Traits/wings.png"));
        mutations.add(m2);
        //Health
        ArrayList<BufferedImage> m3 = new ArrayList<BufferedImage>();
        m3.add(ImageLoader.loadImage("/images/Traits/shell.png"));
        mutations.add(m3);
        //Stealth
        ArrayList<BufferedImage> m4 = new ArrayList<BufferedImage>();
        m4.add(ImageLoader.loadImage("/images/Traits/ears.png"));
        m4.add(ImageLoader.loadImage("/images/Traits/stripes.png"));
        mutations.add(m4);

        minimapFrame = ImageLoader.loadImage("/images/playgraphics/minimap_frame.png");
    }
}
