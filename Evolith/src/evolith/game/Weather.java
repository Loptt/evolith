/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.game;

import evolith.engine.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Weather {
    
    private enum State {
        Clear, Rain, Hail, Snow, Dry, Storm
    }
    private State state;
    
    //private BufferedImage imageClear;
    private BufferedImage imageRain;
    private BufferedImage imageSnow;
    private BufferedImage imageDry;
    private BufferedImage imageStorm;
    
    private WeatherInstance clear = new WeatherInstance(imageRain, imageRain, 0, true);
    private WeatherInstance rain = new WeatherInstance(imageRain, imageRain, 0, false);
    private WeatherInstance dry = new WeatherInstance(imageDry, imageDry, 0, false);
    
    private int cameraWidth, cameraHeight;
    private int width, height;
    private boolean night;

    /**
     *
     * @param img
     * @param width
     * @param height
     * @param cameraWidth
     * @param cameraHeight
     */
    public Weather(int width, int height, int cameraWidth, int cameraHeight) {
        this.width = width;
        this.height = height;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
    }

    /**
     * To get the background subimage depending on the camera height and width
     *
     * @param x
     * @param y
     * @return
     */

    /**
     * To get the width of the background
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the background
     *
     * @return
     */
    public int getHeight() {
        return height;
    }
    

    public void setNight(boolean night) {
        this.night = night;
    }
    
    public void render(Graphics g){
        switch(state){
            case Clear:
                
                break;
            case Rain:
                break;
            case Hail:
                break;
            case Snow:
                break;
            case Dry:
                break;
            case Storm:
                break;
        }
    }
    
}
