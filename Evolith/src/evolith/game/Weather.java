/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.game;

import evolith.engine.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Weather {
    
    private enum State {
        Clear, Dry, Rain, Hail, Snow, Storm
    }
    private State state;
    private ArrayList<ArrayList<State>>states;
    
    //private BufferedImage imageClear;
    private BufferedImage imageRain;
    private BufferedImage imageSnow;
    private BufferedImage imageDry;
    private BufferedImage imageStorm;
    
    
    private WeatherInstance clear = new WeatherInstance(imageRain, imageRain, 0, true);
    private WeatherInstance rain = new WeatherInstance(imageRain, imageRain, 0, false);
    private WeatherInstance dry = new WeatherInstance(imageDry, imageDry, 0, false);
    
    private int width, height;


    /**
     *
     * @param img
     * @param width
     * @param height
     * @param cameraWidth
     * @param cameraHeight
     */
    public Weather(int width, int height) {
        this.width = width;
        this.height = height;
        
        //Clear
        states.get(0).add(State.Clear);
        states.get(0).add(State.Rain);
        states.get(0).add(State.Dry);
        states.get(0).add(State.Hail);
        
        //Dry
        states.get(1).add(State.Dry);
        states.get(1).add(State.Rain);
        
        //Rain
        states.get(2).add(State.Rain);
        states.get(2).add(State.Clear);
        states.get(2).add(State.Storm);
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

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
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
