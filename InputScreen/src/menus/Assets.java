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
    public static BufferedImage nameSpecies;
    
    public static void init() {
        background = ImageLoader.loadImage("/images/background_beta.png");
        player = ImageLoader.loadImage("/images/purple organism.png");
        start = ImageLoader.loadImage("/images/start.png");
        startInstructions = ImageLoader.loadImage("/images/start instructions.png");
        startPlay = ImageLoader.loadImage("/images/start play.png");
        nameSpecies = ImageLoader.loadImage("/images/name_species.png");
    }
}
