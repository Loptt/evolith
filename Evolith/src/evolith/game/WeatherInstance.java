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
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class WeatherInstance {
    private BufferedImage day;      //Day background
    private BufferedImage night;    //Night background

    private BufferedImage topLayer; //Weather filter
    
    private boolean active;         //Active state
    
    private int animation;          //Animation
    
    /**
     * WeatherInstance constructor
     * @param day day background
     * @param night night background
     * @param topLayer weather filter
     * @param animation animation duration
     * @param active active state
     */
    public WeatherInstance(BufferedImage day, BufferedImage night, BufferedImage topLayer, int animation, boolean active){
        this.day = day;
        this.night = night;
        this.topLayer = topLayer;
        this.animation = animation;
        this.active = active;
    }

    /**
     * to get animation duration
     * @return animation
     */
    public int getAnimation() {
        return animation;
    }

    /**
     * to get top layer filter
     * @return topLayer
     */
    public BufferedImage getTopLayer() {
        return topLayer;
    }

    /**
     * to set animation
     * @param animation animation duration
     */
    public void setAnimation(int animation) {
        this.animation = animation;
    }

    /**
     * to get day background
     * @return day
     */
    public BufferedImage getDay() {
        return day;
    }

    /**
     * to get night background
     * @return night
     */
    public BufferedImage getNight() {
        return night;
    }

    /**
     * to set day background
     * @param day day background
     */
    public void setDay(BufferedImage day) {
        this.day = day;
    }

    /**
     * to set night background
     * @param night night background
     */
    public void setNight(BufferedImage night) {
        this.night = night;
    }

    /**
     * to get the top layer filter
     * @param topLayer filter
     */
    public void setTopLayer(BufferedImage topLayer) {
        this.topLayer = topLayer;
    }

    /**
     * to check if it is active
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * to set active state
     * @param active active state
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * To render
     * @param g graphics
     */
    public void render(Graphics g){
        
    }
}
