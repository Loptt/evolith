/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author charles
 */
public class Assets {
    
    public static BufferedImage background; //background image
    public static BufferedImage player; 
    public static BufferedImage plant;
    
    public static BufferedImage start;
    public static BufferedImage startInstructions;
    public static BufferedImage startPlay;
    
    public static ArrayList<ArrayList<BufferedImage>> buttonBar = new ArrayList<ArrayList<BufferedImage>>();
    
    public static void init() {
        background = ImageLoader.loadImage("/images/background_beta.png");
        player = ImageLoader.loadImage("/images/purple organism.png");
        plant = ImageLoader.loadImage("/images/food.png");
        
        //Main Menu Assets
        start = ImageLoader.loadImage("/images/start.png");
        startInstructions = ImageLoader.loadImage("/images/start instructions.png");
        startPlay = ImageLoader.loadImage("/images/start play.png");
        
        /*
            Top Button Bar Assets
        */    
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
        

        
    }
}

