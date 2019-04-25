package evolith.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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

    public static BufferedImage backgroundDay; //background image
    public static BufferedImage backgroundNight; //background image
    public static BufferedImage backgroundFilter; //background image
    public static BufferedImage plant; //plant image
    public static BufferedImage water;
    public static BufferedImage predator;
    
    public static BufferedImage start; // start image
    public static BufferedImage startInstructions; // start menu instruction image
    public static BufferedImage startPlay; // start play button image
    public static BufferedImage setupSpeciesBackground; // start play button image
    
    public static BufferedImage bluegreenOptionOn;
    public static BufferedImage bluegreenOptionOff;
    public static BufferedImage yellowOptionOn;
    public static BufferedImage yellowOptionOff;
    public static BufferedImage pinkOptionOn;
    public static BufferedImage pinkOptionOff;
    public static BufferedImage purpleOptionOn;
    public static BufferedImage purpleOptionOff;
    
    public static BufferedImage playOn;
    public static BufferedImage playOff;
    public static BufferedImage hoverImage;

    public static BufferedImage organismPanel_menu;
    public static BufferedImage organismPanel_close;

    public static BufferedImage minimapFrame;
    public static BufferedImage rotatedPlant;
    
    public static ArrayList<BufferedImage> orgColors;
    
    public static ArrayList<ArrayList<BufferedImage>> mutations = new ArrayList<ArrayList<BufferedImage>>();
    
    public static ArrayList<ArrayList<BufferedImage>> buttonBar = new ArrayList<ArrayList<BufferedImage>>();

    public static BufferedImage organismPanel_prevArrow;
    public static BufferedImage organismPanel_nextArrow;
    public static BufferedImage organismPanel_reproduceButton_ON;
    public static BufferedImage organismPanel_reproduceButton_OFF;
    
    
    public static BufferedImage mutation_menu;
    public static BufferedImage mutation_max_tier;
    public static BufferedImage mutation_select;
    public static BufferedImage mutationPanel_evolveButton_ON;
    public static BufferedImage mutationPanel_evolveButton_OFF;
    public static BufferedImage mutationPanel_not_evolveButton_ON;
    public static BufferedImage mutationPanel_not_evolveButton_OFF;
    public static BufferedImage nextArrow;
    public static BufferedImage prevArrow;
    
    public static BufferedImage glow;

    /**
     * Initalizes the assets and links to the image folder
     */
    public static void init() {
        backgroundDay = ImageLoader.loadImage("/images/backgrounds/backgroundday.png");
        backgroundNight = ImageLoader.loadImage("/images/backgrounds/backgroundnight.png");
        backgroundFilter = ImageLoader.loadImage("/images/backgrounds/nightmode.png");
        plant = ImageLoader.loadImage("/images/playgraphics/food.png");
        water = ImageLoader.loadImage("/images/playgraphics/water.png");
        predator = ImageLoader.loadImage("/images/playgraphics/alien_enemy.png");
        
        start = ImageLoader.loadImage("/images/mainmenu/mainmenu.png");
        startInstructions = ImageLoader.loadImage("/images/mainmenu/maininstructions.png");
        startPlay = ImageLoader.loadImage("/images/mainmenu/mainplay.png");
        setupSpeciesBackground = ImageLoader.loadImage("/images/setupmenu/choosemenu.png");
        
        bluegreenOptionOff = ImageLoader.loadImage("/images/setupmenu/bluecolor.png");
        yellowOptionOff = ImageLoader.loadImage("/images/setupmenu/yellowcolor.png");
        pinkOptionOff = ImageLoader.loadImage("/images/setupmenu/pinkcolor.png");
        purpleOptionOff = ImageLoader.loadImage("/images/setupmenu/purplecolor.png");
        
        bluegreenOptionOn = ImageLoader.loadImage("/images/setupmenu/bigbluecolor.png");
        yellowOptionOn = ImageLoader.loadImage("/images/setupmenu/bigyellowcolor.png");
        pinkOptionOn = ImageLoader.loadImage("/images/setupmenu/bigpinkcolor.png");
        purpleOptionOn = ImageLoader.loadImage("/images/setupmenu/bigpurplecolor.png");
        
        
        
        playOn = ImageLoader.loadImage("/images/mainmenu/onplay.png");
        playOff = ImageLoader.loadImage("/images/mainmenu/offplay.png");
        hoverImage = ImageLoader.loadImage("/images/playgraphics/hover_bar.png");
        
        organismPanel_close = ImageLoader.loadImage("/images/panel/closedetails.png");
        organismPanel_menu = ImageLoader.loadImage("/images/panel/detailsmenu2.png");   
        organismPanel_prevArrow = ImageLoader.loadImage("/images/setupmenu/orangeone.png");
        organismPanel_nextArrow = ImageLoader.loadImage("/images/setupmenu/orangeone.png");
        organismPanel_reproduceButton_ON = ImageLoader.loadImage("/images/panel/reproduceon.png");
        organismPanel_reproduceButton_OFF = ImageLoader.loadImage("/images/panel/reproduceoff.png");
        
        
        

        mutation_menu = ImageLoader.loadImage("/images/panel/mutationmenu.png");
        mutation_max_tier = ImageLoader.loadImage("/images/panel/maxtier.png");
        mutation_select = ImageLoader.loadImage("/images/panel/mutselect.png");
        mutationPanel_evolveButton_ON  = ImageLoader.loadImage("/images/panel/evolveon.png");
        mutationPanel_evolveButton_OFF  = ImageLoader.loadImage("/images/panel/evolveoff.png");
        mutationPanel_not_evolveButton_ON  = ImageLoader.loadImage("/images/panel/notevolveon.png");
        mutationPanel_not_evolveButton_OFF  = ImageLoader.loadImage("/images/panel/notevolveoff.png");

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
        m3.add(ImageLoader.loadImage("/images/Traits/shell.png"));
        m3.add(ImageLoader.loadImage("/images/Traits/shell.png"));
        mutations.add(m3);
        //Stealth
        ArrayList<BufferedImage> m4 = new ArrayList<BufferedImage>();
        m4.add(ImageLoader.loadImage("/images/Traits/ears.png"));
        m4.add(ImageLoader.loadImage("/images/Traits/stripes.png"));
        mutations.add(m4);

        minimapFrame = ImageLoader.loadImage("/images/playgraphics/minimap_frame.png");
        
        glow = ImageLoader.loadImage("/images/organisms/glow.png");
        
        nextArrow = ImageLoader.loadImage("/images/panel/next.png");
        prevArrow = ImageLoader.loadImage("/images/panel/prev.png");
        
    }
    
    /**
     * SLOWS DOWN GAME
     * @param img
     * @param angle
     * @return 
     */
    public static BufferedImage rotateImage(BufferedImage img, double angle) {
        
        angle /= 2;
        
        System.out.println("ANGLE:   " + angle);

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);
        
        System.out.println("WIDTH: " + newWidth + "  HEIGHT:  " + newHeight);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, at, null);
        g2d.dispose();

        return rotated;
    }
}
