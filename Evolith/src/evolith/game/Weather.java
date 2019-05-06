/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.game;

import evolith.engine.Animation;
import evolith.engine.Assets;
import evolith.helpers.Clock;
import static evolith.helpers.Commons.DAY_CYCLE_DURATION_SECONDS;
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
    
    public enum State {
        Clear, Dry, Rain, Hail, Snow, Storm
    }
    private State state;
    private ArrayList<ArrayList<State>>states;
    
    private WeatherInstance clear = new WeatherInstance(Assets.backgroundDay, Assets.backgroundNight, Assets.noBackground, 0, true);
    private WeatherInstance rain = new WeatherInstance(Assets.backgroundDay, Assets.backgroundRainNight, Assets.backgroundFilter, 0, false);
    private WeatherInstance dry = new WeatherInstance(Assets.dryBackground, Assets.dryBackgroundNight, Assets.dryLayer, 0, false);
    private WeatherInstance hail = new WeatherInstance(Assets.backgroundDay, Assets.backgroundRainNight, Assets.coldLayer, 0, false);
    private WeatherInstance storm = new WeatherInstance(Assets.backgroundDay, Assets.backgroundRainNight, Assets.backgroundFilter, 0, false);
    private WeatherInstance snow = new WeatherInstance(Assets.backgroundSnow, Assets.backgroundSnowNight, Assets.coldLayer, 0, false);
    
    private int width, height;
    
    private Clock clock;
    private int prevSecDayCycleChange;
    private Background background;
    private int prevWeather;
    
    private Animation raindrops;
    private Animation snowhail;

    /**
     *
     * @param img
     * @param width
     * @param height
     * @param cameraWidth
     * @param cameraHeight
     */
    public Weather(int width, int height, Background background) {
        this.width = width;
        this.height = height;
        this.background = background;
        
        state = State.Clear;
        
        prevSecDayCycleChange = 0;
        prevWeather = 0;
        
        states = new ArrayList<ArrayList<State>>();
        ArrayList<State>ClearList = new ArrayList<State>();
        states.add(ClearList);
        ArrayList<State>DryList = new ArrayList<State>();
        states.add(DryList);
        ArrayList<State>RainList = new ArrayList<State>();
        states.add(RainList);
        ArrayList<State>StormList = new ArrayList<State>();
        states.add(StormList);
        ArrayList<State>HailList = new ArrayList<State>();
        states.add(HailList);
        ArrayList<State>SnowList = new ArrayList<State>();
        states.add(SnowList);


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
        
        //Storm
        states.get(3).add(State.Clear);
        
        //Hail
        states.get(4).add(State.Hail);
        states.get(4).add(State.Clear);
        states.get(4).add(State.Snow);
        
        //Snow
        states.get(5).add(State.Clear);
        
        raindrops = new Animation(Assets.rainanimation, 60);
        snowhail = new Animation(Assets.snowanimation, 60);
        
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
    
    public void tick(){
        raindrops.tick();
        snowhail.tick();
    }

    public void changeWeather(){
        int newWeather = 0;
        newWeather = (int) Math.floor(Math.random()*states.get(prevWeather).size());
        setFalse();
        switch(states.get(prevWeather).get(newWeather)){
            case Clear:
                state = State.Clear;
                clear.setActive(true);
                System.out.println("clear");
                prevWeather = 0;
                background.setImageDay(clear.getDay());
                background.setImageNight(clear.getNight());
                
                break;
            case Dry:
                state = State.Dry;
                dry.setActive(true);
                System.out.println("dry");
                prevWeather = 1;
                background.setImageDay(dry.getDay());
                background.setImageNight(dry.getNight());
                break;
            case Rain:
                state = State.Rain;
                rain.setActive(true);
                System.out.println("rain");
                prevWeather = 2;
                background.setImageDay(rain.getDay());
                background.setImageNight(rain.getNight());
                break;
            case Storm:
                state = State.Storm;
                storm.setActive(true);
                System.out.println("storm");
                prevWeather = 3;
                background.setImageDay(storm.getDay());
                background.setImageNight(storm.getNight());
                break;
            case Hail:
                state = State.Hail;
                hail.setActive(true);
                System.out.println("hail");
                prevWeather = 4;
                background.setImageDay(hail.getDay());
                background.setImageNight(hail.getNight());
                break;
            case Snow:
                state = State.Snow;
                snow.setActive(true);
                System.out.println("snow");
                prevWeather = 5;
                background.setImageDay(snow.getDay());
                background.setImageNight(snow.getNight());
                break;
                
        }
    }
    
    public void setWeather(State s) {
        setFalse();
        switch(s){
            case Clear:
                state = State.Clear;
                clear.setActive(true);
                System.out.println("clear");
                prevWeather = 0;
                background.setImageDay(clear.getDay());
                background.setImageNight(clear.getNight());
                
                break;
            case Dry:
                state = State.Dry;
                dry.setActive(true);
                System.out.println("dry");
                prevWeather = 1;
                background.setImageDay(dry.getDay());
                background.setImageNight(dry.getNight());
                break;
            case Rain:
                state = State.Rain;
                rain.setActive(true);
                System.out.println("rain");
                prevWeather = 2;
                background.setImageDay(rain.getDay());
                background.setImageNight(rain.getNight());
                break;
            case Storm:
                state = State.Storm;
                storm.setActive(true);
                System.out.println("storm");
                prevWeather = 3;
                background.setImageDay(storm.getDay());
                background.setImageNight(storm.getNight());
                break;
            case Hail:
                state = State.Hail;
                hail.setActive(true);
                System.out.println("hail");
                prevWeather = 4;
                background.setImageDay(hail.getDay());
                background.setImageNight(hail.getNight());
                break;
            case Snow:
                state = State.Snow;
                snow.setActive(true);
                System.out.println("snow");
                prevWeather = 5;
                background.setImageDay(snow.getDay());
                background.setImageNight(snow.getNight());
                break;
                
        }
    }
    
    
    public void setFalse(){
        clear.setActive(false);
        rain.setActive(false);
        dry.setActive(false);
        storm.setActive(false);
        hail.setActive(false);
        snow.setActive(false);
    }
    
    public void render(Graphics g){
        switch(state){
            case Clear:
                g.drawImage(clear.getTopLayer(), 0, 0, width, height, null);
                break;
            case Dry:
                g.drawImage(dry.getTopLayer(), 0, 0, width, height, null);
                break;
            case Rain:
                g.drawImage(raindrops.getCurrentFrame(), 0, 0, width, height, null);
                g.drawImage(rain.getTopLayer(), 0, 0,width, height, null);
                break;
            case Storm:
                 g.drawImage(raindrops.getCurrentFrame(), 0, 0, width, height, null);
                g.drawImage(storm.getTopLayer(), 0, 0, width, height, null);
                break;
            case Hail:
                g.drawImage(snowhail.getCurrentFrame(), 0, 0, width, height, null);
                g.drawImage(hail.getTopLayer(), 0, 0, width, height, null);
                break;
            case Snow:
                g.drawImage(snowhail.getCurrentFrame(), 0, 0, width, height, null);
                g.drawImage(snow.getTopLayer(), 0, 0, width, height, null);
                break;
        }
    }
    
}
