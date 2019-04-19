/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodandwater;

import java.awt.image.BufferedImage;

/**
 *
 * @author charles
 */
public class Animation {
    private int speed; //animation speed
    private int index; // number of the animation frame
    private long lastTime; //last ticked time
    private long timer; //curent time
    
    private boolean done;
    
    private BufferedImage[] frames;
    
    /**
     * To create a new animation object
     * @param frames
     * @param speed 
     */
    public Animation(BufferedImage[] frames, int speed) {
        this.frames = frames;
        this.speed = speed;
        
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
        done = false;
    }
    
    /**
     * To get the current frame of the animation
     * @return 
     */
    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
    
    /**
     * to update the animation every frame
     */
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        
        if (timer > speed) {
            index++;
            timer = 0;
            
            if (index >= frames.length) {
                done = true;
                index = 0;
            }
        }
    }
    
    /**
     * to set done
     * @param done 
     */
    public void setDone(boolean done) {
        this.done = done;
    }
    
    /**
     * To get done
     * @return 
     */
    public boolean isDone() {
        return done;
    }
}
