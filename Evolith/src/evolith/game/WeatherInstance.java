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
    private BufferedImage day;
    private BufferedImage night;

    private BufferedImage topLayer;
    
    private boolean active;
    
    private int animation;
    
    public WeatherInstance(BufferedImage day, BufferedImage night, BufferedImage topLayer, int animation, boolean active){
        this.day = day;
        this.night = night;
        this.topLayer = topLayer;
        this.animation = animation;
        this.active = active;
    }

    public int getAnimation() {
        return animation;
    }


    public BufferedImage getTopLayer() {
        return topLayer;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public BufferedImage getDay() {
        return day;
    }

    public BufferedImage getNight() {
        return night;
    }

    public void setDay(BufferedImage day) {
        this.day = day;
    }

    public void setNight(BufferedImage night) {
        this.night = night;
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
