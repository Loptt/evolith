/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author USUARIO
 */
public class WeatherInstance {
    private BufferedImage ground;
    private BufferedImage topLayer;
    
    private boolean active;
    
    private int animation;
    
    public WeatherInstance(BufferedImage ground, BufferedImage topLayer, int animation, boolean active){
        this.ground = ground;
        this.topLayer = topLayer;
        this.animation = animation;
        this.active = active;
    }

    public int getAnimation() {
        return animation;
    }

    public BufferedImage getGround() {
        return ground;
    }

    public BufferedImage getTopLayer() {
        return topLayer;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public void setGround(BufferedImage ground) {
        this.ground = ground;
    }

    public void setTopLayer(BufferedImage topLayer) {
        this.topLayer = topLayer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void render(Graphics g){
        
    }
}
