/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import java.awt.image.BufferedImage;

/**
 *
 * @author charles
 */
public class Assets {
    
    public static BufferedImage background; //background image
    public static BufferedImage player; 
    public static BufferedImage start;
    public static BufferedImage startInstructions;
    public static BufferedImage startPlay;
    public static BufferedImage setupSpeciesBackground;
    public static BufferedImage blueOption;
    public static BufferedImage yellowOption;
    public static BufferedImage redOption;
    public static BufferedImage purpleOption;
    public static BufferedImage playOn;
    public static BufferedImage playOff;
    
    public static BufferedImage mario;
    
    public static void init() {
        background = ImageLoader.loadImage("/images/background_beta.png");
        player = ImageLoader.loadImage("/images/purple organism.png");
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
        mario = ImageLoader.loadImage("/images/mario.png");
    }
}
