/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orgmove;

import java.awt.image.BufferedImage;

/**
 *
 * @author charles
 */
public class Assets {
    
    public static BufferedImage background; //background image
    public static BufferedImage player; 
    
    public static void init() {
        background = ImageLoader.loadImage("/images/background_beta.png");
        player = ImageLoader.loadImage("/images/purple organism.png");

    }
}
