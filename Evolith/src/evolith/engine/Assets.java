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
    public static SoundClip ricardomilos;
    public static SoundClip fatrat_afterlife;
    public static SoundClip fatrat_threnody;
    public static SoundClip originalsong;

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
    public static BufferedImage egg;
    
    // Pause Menu
    public static BufferedImage PMLoadButtonOff;
    public static BufferedImage PMLoadButtonOn;
    public static BufferedImage PMMainMenuButtonOff;
    public static BufferedImage PMMainMenuButtonOn;
    public static BufferedImage PMPauseMenu;
    public static BufferedImage PMResumeButtonOff;
    public static BufferedImage PMResumeButtonOn;
    public static BufferedImage PMSaveButtonOff;
    public static BufferedImage PMSaveButtonOn;
    
    public static BufferedImage overWin;
    public static BufferedImage overLose;
    
    public static BufferedImage overMenuButtonOn;
    public static BufferedImage overMenuButtonOff;
    public static BufferedImage statsMenuButtonOn;
    public static BufferedImage statsMenuButtonOff;
    
    public static ArrayList<BufferedImage> instructions;
    
    public static BufferedImage statsiconOff;
    public static BufferedImage statsiconOn;
    public static BufferedImage rankPanel;
    public static BufferedImage statsPanel;


    public static ArrayList<BufferedImage> modes;
    public static BufferedImage singlePlayerOn;
    public static BufferedImage singlePlayerOff;
    public static BufferedImage loadModeOn;
    public static BufferedImage loadModeOff;
    public static BufferedImage multiplayerOn;
    public static BufferedImage multiplayerOff;
    public static BufferedImage backOn;
    public static BufferedImage backOff;
    public static BufferedImage hostOn;
    public static BufferedImage hostOff;
    public static BufferedImage joinOn;
    public static BufferedImage joinOff;

    public static BufferedImage coldLayer;
    public static BufferedImage noBackground;
    public static BufferedImage dryLayer;
    public static BufferedImage dryBackground;
    public static BufferedImage dryBackgroundNight;
    public static BufferedImage backgroundRainNight;
    public static BufferedImage backgroundRain;
    public static BufferedImage backgroundSnow;
    public static BufferedImage backgroundSnowNight;
    
    public static BufferedImage rain0;
    public static BufferedImage rain1;
    public static BufferedImage rain2;
    public static BufferedImage rain3;
    public static BufferedImage rain4;
    public static BufferedImage rain5;
    public static BufferedImage rain6;
    public static BufferedImage rain7;
    
    public static BufferedImage rainanimation[];
    
    public static BufferedImage snow0;
    public static BufferedImage snow1;
    public static BufferedImage snow2;
    public static BufferedImage snow3;
    public static BufferedImage snow4;
    public static BufferedImage snow5;
    public static BufferedImage snow6;
    public static BufferedImage snow7;
    public static BufferedImage snow8;
    public static BufferedImage snow9;
    public static BufferedImage snow10;
    public static BufferedImage snow11;
    public static BufferedImage snow12;
    public static BufferedImage snow13;
    public static BufferedImage snow14;
    public static BufferedImage snow15;
    public static BufferedImage snow16;
    public static BufferedImage snow17;
    public static BufferedImage snow18;
    public static BufferedImage snow19;
    
    public static BufferedImage snowanimation[];
    
    public static BufferedImage repClock;
    
    public static BufferedImage clearIcon;
    public static BufferedImage dryIcon;
    public static BufferedImage rainIcon;
    public static BufferedImage stormIcon;
    public static BufferedImage snowIcon;
    public static BufferedImage hailIcon;
    
    public static SoundClip watersound;
    public static SoundClip grasssound;
    public static SoundClip aliensound;

    public static BufferedImage campfire;
    public static BufferedImage setCampfireOn;
    public static BufferedImage setCampfireOff;
    public static BufferedImage leg1prev;
    public static BufferedImage leg2prev;
    public static BufferedImage leg3prev;
    
    public static BufferedImage maxIntButtonOn;
    public static BufferedImage maxIntButtonOff;
    public static BufferedImage maxIntButtonOpp;


    /**
     * Initializes the assets and links to the image folder
     */
    public static void init() {
        ricardomilos = new SoundClip("/sounds/ricardomilos.wav");
        fatrat_afterlife = new SoundClip("/sounds/fatrat_afterlife.wav");
        fatrat_threnody = new SoundClip("/sounds/fatrat_threnody.wav");
        originalsong = new SoundClip("/sounds/originalsong.wav");
        
        // Pause Menu
        PMLoadButtonOff = ImageLoader.loadImage("/images/pausemenu/loadbutton.png");
        PMLoadButtonOn = ImageLoader.loadImage("/images/pausemenu/loadbuttonon.png");
        PMMainMenuButtonOff = ImageLoader.loadImage("/images/pausemenu/mainmenubutton.png");
        PMMainMenuButtonOn = ImageLoader.loadImage("/images/pausemenu/mainmenubuttonon.png");
        PMPauseMenu = ImageLoader.loadImage("/images/pausemenu/pausemenu.png");
        PMResumeButtonOff = ImageLoader.loadImage("/images/pausemenu/resumebutton.png");
        PMResumeButtonOn = ImageLoader.loadImage("/images/pausemenu/resumebuttonon.png");
        PMSaveButtonOff = ImageLoader.loadImage("/images/pausemenu/savebutton.png");
        PMSaveButtonOn = ImageLoader.loadImage("/images/pausemenu/savebuttonon.png");
        
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
        organismPanel_menu = ImageLoader.loadImage("/images/panel/detailsintname.png");   
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
        
        egg = ImageLoader.loadImage("/images/organisms/egg.png");
        
        overWin = ImageLoader.loadImage("/images/over/winscreen.png");
        overLose = ImageLoader.loadImage("/images/over/gameover.png");
        overMenuButtonOn = ImageLoader.loadImage("/images/over/endmainbuttonon.png");
        overMenuButtonOff = ImageLoader.loadImage("/images/over/endmainbuttonoff.png");
        statsMenuButtonOn = ImageLoader.loadImage("/images/over/statisticsbuttonon.png");
        statsMenuButtonOff = ImageLoader.loadImage("/images/over/statisticsbuttonoff.png");
        
        instructions = new ArrayList<>();
        
        instructions.add(ImageLoader.loadImage("/images/instructions/instructions1.png"));
        instructions.add(ImageLoader.loadImage("/images/instructions/instructions2.png"));
        instructions.add(ImageLoader.loadImage("/images/instructions/instructions3.png"));
        instructions.add(ImageLoader.loadImage("/images/instructions/instructions4.png"));
        instructions.add(ImageLoader.loadImage("/images/instructions/instructions5.png"));
        instructions.add(ImageLoader.loadImage("/images/instructions/instructions6.png"));
   
        statsiconOff = ImageLoader.loadImage("/images/ranking/statsoff.png");
        statsiconOn = ImageLoader.loadImage("/images/ranking/statson.png");
        statsPanel = ImageLoader.loadImage("/images/ranking/statspanel.png");
        rankPanel = ImageLoader.loadImage("/images/ranking/rankingpanel.png");

        modes = new ArrayList<>();
        modes.add(ImageLoader.loadImage("/images/multiplayer/modescreen.png"));
        modes.add(ImageLoader.loadImage("/images/multiplayer/multiplayerscreen.png"));
        modes.add(ImageLoader.loadImage("/images/multiplayer/hostingscreen.png"));
        modes.add(ImageLoader.loadImage("/images/multiplayer/joingamescreen.png"));
        
        singlePlayerOn = ImageLoader.loadImage("/images/multiplayer/singleplayeron.png");
        singlePlayerOff = ImageLoader.loadImage("/images/multiplayer/singleplayeroff.png");
        loadModeOn = ImageLoader.loadImage("/images/multiplayer/loadgameon.png");
        loadModeOff = ImageLoader.loadImage("/images/multiplayer/loadgameoff.png");
        multiplayerOn = ImageLoader.loadImage("/images/multiplayer/multiplayeron.png");
        multiplayerOff = ImageLoader.loadImage("/images/multiplayer/multiplayeroff.png");
        backOn = ImageLoader.loadImage("/images/multiplayer/backon.png");
        backOff = ImageLoader.loadImage("/images/multiplayer/backoff.png");
        backOff = ImageLoader.loadImage("/images/multiplayer/backoff.png");
        hostOn = ImageLoader.loadImage("/images/multiplayer/hostgameon.png");
        hostOff = ImageLoader.loadImage("/images/multiplayer/hostgameoff.png");
        joinOn = ImageLoader.loadImage("/images/multiplayer/joingameon.png");
        joinOff = ImageLoader.loadImage("/images/multiplayer/joingameoff.png");

        coldLayer = ImageLoader.loadImage("/images/backgrounds/whitelayer.png");
        noBackground = ImageLoader.loadImage("/images/backgrounds/nolayer.png");
        dryLayer = ImageLoader.loadImage("/images/backgrounds/drylayer.png");
        dryBackground = ImageLoader.loadImage("/images/backgrounds/drybackground.png");
        dryBackgroundNight = ImageLoader.loadImage("/images/backgrounds/drybackgroundnight.png");
        backgroundRainNight = ImageLoader.loadImage("/images/backgrounds/rainbackground.png");
        backgroundRain = ImageLoader.loadImage("/images/backgrounds/backgroundrainday.png");
        backgroundSnow = ImageLoader.loadImage("/images/backgrounds/backgroundsnow.png");
        backgroundSnowNight = ImageLoader.loadImage("/images/backgrounds/backgroundsnownight.png");
        
        rainanimation = new BufferedImage[8];
        rain0 = ImageLoader.loadImage("/images/weatheranimations/rain0.png");
        rainanimation[0] = rain0;
        rain1 = ImageLoader.loadImage("/images/weatheranimations/rain1.png");
        rainanimation[1] = rain1;
        rain2 = ImageLoader.loadImage("/images/weatheranimations/rain2.png");
        rainanimation[2] = rain2;
        rain3 = ImageLoader.loadImage("/images/weatheranimations/rain3.png");
        rainanimation[3] = rain3;
        rain4 = ImageLoader.loadImage("/images/weatheranimations/rain4.png");
        rainanimation[4] = rain4;
        rain5 = ImageLoader.loadImage("/images/weatheranimations/rain5.png");
        rainanimation[5] = rain5;
        rain6 = ImageLoader.loadImage("/images/weatheranimations/rain6.png");
        rainanimation[6] = rain6;
        rain7 = ImageLoader.loadImage("/images/weatheranimations/rain7.png");
        rainanimation[7] = rain7;

        snowanimation = new BufferedImage[20];
        snow0 = ImageLoader.loadImage("/images/weatheranimations/snow0.png");
        snowanimation[0] = snow0;
        snow1 = ImageLoader.loadImage("/images/weatheranimations/snow1.png");
        snowanimation[1] = snow1;
        snow2 = ImageLoader.loadImage("/images/weatheranimations/snow2.png");
        snowanimation[2] = snow2;
        snow3 = ImageLoader.loadImage("/images/weatheranimations/snow3.png");
        snowanimation[3] = snow3;
        snow4 = ImageLoader.loadImage("/images/weatheranimations/snow4.png");
        snowanimation[4] = snow4;
        snow5 = ImageLoader.loadImage("/images/weatheranimations/snow5.png");
        snowanimation[5] = snow5;
        snow6 = ImageLoader.loadImage("/images/weatheranimations/snow6.png");
        snowanimation[6] = snow6;
        snow7 = ImageLoader.loadImage("/images/weatheranimations/snow7.png");
        snowanimation[7] = snow7;
        snow8 = ImageLoader.loadImage("/images/weatheranimations/snow8.png");
        snowanimation[8] = snow8;
        snow9 = ImageLoader.loadImage("/images/weatheranimations/snow9.png");
        snowanimation[9] = snow9;
        snow10 = ImageLoader.loadImage("/images/weatheranimations/snow10.png");
        snowanimation[10] = snow10;
        snow11 = ImageLoader.loadImage("/images/weatheranimations/snow11.png");
        snowanimation[11] = snow11;
        snow12 = ImageLoader.loadImage("/images/weatheranimations/snow12.png");
        snowanimation[12] = snow12;
        snow13 = ImageLoader.loadImage("/images/weatheranimations/snow13.png");
        snowanimation[13] = snow13;
        snow14 = ImageLoader.loadImage("/images/weatheranimations/snow14.png");
        snowanimation[14] = snow14;
        snow15 = ImageLoader.loadImage("/images/weatheranimations/snow15.png");
        snowanimation[15] = snow15;
        snow16 = ImageLoader.loadImage("/images/weatheranimations/snow16.png");
        snowanimation[16] = snow16;
        snow17 = ImageLoader.loadImage("/images/weatheranimations/snow17.png");
        snowanimation[17] = snow17;
        snow18 = ImageLoader.loadImage("/images/weatheranimations/snow18.png");
        snowanimation[18] = snow18;
        snow19 = ImageLoader.loadImage("/images/weatheranimations/snow19.png");
        snowanimation[19] = snow19;
        
        clearIcon = ImageLoader.loadImage("/images/weatheranimations/icons/sun.png");
        dryIcon = ImageLoader.loadImage("/images/weatheranimations/icons/sunny.png");
        rainIcon = ImageLoader.loadImage("/images/weatheranimations/icons/rain.png");
        stormIcon = ImageLoader.loadImage("/images/weatheranimations/icons/storm.png");
        hailIcon = ImageLoader.loadImage("/images/weatheranimations/icons/hail.png");
        snowIcon = ImageLoader.loadImage("/images/weatheranimations/icons/snow.png");
        
        watersound = new SoundClip("/sounds/watersound.wav");
        grasssound = new SoundClip("/sounds/grasssound.wav");
        aliensound = new SoundClip("/sounds/aliensound.wav");
        
        repClock = ImageLoader.loadImage("/images/organisms/clock.png");

        campfire = ImageLoader.loadImage("/images/organisms/campfire.png");
        setCampfireOn = ImageLoader.loadImage("/images/panel/campfirebuttonon.png");
        setCampfireOff = ImageLoader.loadImage("/images/panel/campfirebuttonoff.png");

        leg1prev = ImageLoader.loadImage("/images/Traits/legs1prev.png");
        leg2prev = ImageLoader.loadImage("/images/Traits/legs2prev.png");
        leg3prev = ImageLoader.loadImage("/images/Traits/legs3prev.png");
        
        maxIntButtonOn = ImageLoader.loadImage("/images/buttonbar/maxinton.png");
        maxIntButtonOff = ImageLoader.loadImage("/images/buttonbar/maxintoff.png");
        maxIntButtonOpp = ImageLoader.loadImage("/images/buttonbar/oppmaxint.png");
    }
    
    /**
     * SLOWS DOWN GAME
     * 
     * @param img
     * @param angle
     * @return rotated
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
    
    /**
     * Set alpha
     * 
     * @param alpha
     * @param img
     * @return img
     */
    public static BufferedImage setAlpha(byte alpha, BufferedImage img) {
        alpha %= 0xff; 
        for (int cx=0;cx<img.getWidth();cx++) {          
            for (int cy=0;cy<img.getHeight();cy++) {
                int color = img.getRGB(cx, cy);

                int mc = (alpha << 24) | 0x00ffffff;
                int newcolor = color & mc;
                img.setRGB(cx, cy, newcolor);            

            }

        }
        
        return img;
    }
}
