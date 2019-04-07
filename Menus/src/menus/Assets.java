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
    public static BufferedImage player; //player sprite
    public static BufferedImage bomb; //alien's bomb sprite
    public static BufferedImage explosion; //alien explosion sprite
    public static BufferedImage alien[]; //aliens sprites
    
    public static SoundClip laser; //sound of the laser being shot
    public static SoundClip expSound; //sound of the explosion when an alien is destroyed
    
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpg");
        player = ImageLoader.loadImage("/images/player.png");
        bomb = ImageLoader.loadImage("/images/bomb.png");
        explosion = ImageLoader.loadImage("/images/explosion.png");
        
        alien = new BufferedImage[4];
        
        laser = new SoundClip("/sounds/laser.wav");
        expSound = new SoundClip("/sounds/explosion.wav");
        //load the 4 different alien sprites 
        alien[0] = ImageLoader.loadImage("/images/alienorange.png");
        alien[1] = ImageLoader.loadImage("/images/alienred.png");
        alien[2] = ImageLoader.loadImage("/images/alienblue.png");
        alien[3] = ImageLoader.loadImage("/images/alienpink.png");
    }
}
