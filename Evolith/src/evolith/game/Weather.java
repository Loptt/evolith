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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
    private State prevState;
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
        prevState = State.Clear;
        
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

    public State getPrevState() {
        return prevState;
    }
    
    public void tick(){
        raindrops.tick();
        snowhail.tick();
    }

    public void changeWeather(){
        int newWeather = 0;
        newWeather = (int) Math.floor(Math.random()*states.get(prevWeather).size());
        setFalse();
        prevState = state;
        switch(states.get(prevWeather).get(newWeather)){
            case Clear:
                state = State.Clear;
                clear.setActive(true);
                prevWeather = 0;
                background.setImageDay(clear.getDay());
                background.setImageNight(clear.getNight());
                
                break;
            case Dry:
                state = State.Dry;
                dry.setActive(true);
                prevWeather = 1;
                background.setImageDay(dry.getDay());
                background.setImageNight(dry.getNight());
                break;
            case Rain:
                state = State.Rain;
                rain.setActive(true);
                prevWeather = 2;
                background.setImageDay(rain.getDay());
                background.setImageNight(rain.getNight());
                break;
            case Storm:
                state = State.Storm;
                storm.setActive(true);
                prevWeather = 3;
                background.setImageDay(storm.getDay());
                background.setImageNight(storm.getNight());
                break;
            case Hail:
                state = State.Hail;
                hail.setActive(true);
                prevWeather = 4;
                background.setImageDay(hail.getDay());
                background.setImageNight(hail.getNight());
                break;
            case Snow:
                state = State.Snow;
                snow.setActive(true);
                prevWeather = 5;
                background.setImageDay(snow.getDay());
                background.setImageNight(snow.getNight());
                break;
                
        }
    }
    
    public void setWeather(State s) {
        prevState = state;
        setFalse();
        switch(s){
            case Clear:
                state = State.Clear;
                clear.setActive(true);
                prevWeather = 0;
                background.setImageDay(clear.getDay());
                background.setImageNight(clear.getNight());
                
                break;
            case Dry:
                state = State.Dry;
                dry.setActive(true);
                prevWeather = 1;
                background.setImageDay(dry.getDay());
                background.setImageNight(dry.getNight());
                break;
            case Rain:
                state = State.Rain;
                rain.setActive(true);
                prevWeather = 2;
                background.setImageDay(rain.getDay());
                background.setImageNight(rain.getNight());
                break;
            case Storm:
                state = State.Storm;
                storm.setActive(true);
                prevWeather = 3;
                background.setImageDay(storm.getDay());
                background.setImageNight(storm.getNight());
                break;
            case Hail:
                state = State.Hail;
                hail.setActive(true);
                prevWeather = 4;
                background.setImageDay(hail.getDay());
                background.setImageNight(hail.getNight());
                break;
            case Snow:
                state = State.Snow;
                snow.setActive(true);
                prevWeather = 5;
                background.setImageDay(snow.getDay());
                background.setImageNight(snow.getNight());
                break;
                
        }
    }

    public WeatherInstance getRain() {
        return rain;
    }

    public WeatherInstance getStorm() {
        return storm;
    }
    
    
    
    
    public void setFalse(){
        clear.setActive(false);
        rain.setActive(false);
        dry.setActive(false);
        storm.setActive(false);
        hail.setActive(false);
        snow.setActive(false);
    }
    
    public void save(PrintWriter pw) {
        switch(state) {
            case Clear:
                pw.println(Integer.toString(1));
                break;
            case Dry:
                pw.println(Integer.toString(2));
                break;
            case Rain:
                pw.println(Integer.toString(3));
                break;
            case Storm:
                pw.println(Integer.toString(4));
                break;
            case Hail:
                pw.println(Integer.toString(5));
                break;
            case Snow:
                pw.println(Integer.toString(6));
                break;
        }
    }
    
    public void load(BufferedReader br) throws IOException {
        int s = Integer.parseInt(br.readLine());
        
        switch(s) {
            case 1:
                setWeather(State.Clear);
                break;
            case 2:
                setWeather(State.Dry);
                break;
            case 3:
                setWeather(State.Rain);
                break;
            case 4:
                setWeather(State.Storm);
                break;
            case 5:
                setWeather(State.Hail);
                break;
            case 6:
                setWeather(State.Snow);
                break;
        }
    }
    
    public void render(Graphics g){
        switch(state){
            case Clear:
                g.drawImage(clear.getTopLayer(), 0, 0, width, height, null);
                g.drawImage(Assets.clearIcon, 750, 25, 42, 42, null);
                break;
            case Dry:
                g.drawImage(dry.getTopLayer(), 0, 0, width, height, null);
                g.drawImage(Assets.dryIcon, 750, 25, 42, 42, null);
                break;
            case Rain:
                g.drawImage(raindrops.getCurrentFrame(), 0, 0, width, height, null);
                g.drawImage(rain.getTopLayer(), 0, 0,width, height, null);
                g.drawImage(Assets.rainIcon, 750, 25, 42, 42, null);
                break;
            case Storm:
                 g.drawImage(raindrops.getCurrentFrame(), 0, 0, width, height, null);
                g.drawImage(storm.getTopLayer(), 0, 0, width, height, null);
                g.drawImage(Assets.stormIcon, 750, 25, 42, 42, null);
                break;
            case Hail:
                g.drawImage(snowhail.getCurrentFrame(), 0, 0, width, height, null);
                g.drawImage(hail.getTopLayer(), 0, 0, width, height, null);
                g.drawImage(Assets.hailIcon, 750, 25, 42, 42, null);
                break;
            case Snow:
                g.drawImage(snowhail.getCurrentFrame(), 0, 0, width, height, null);
                g.drawImage(snow.getTopLayer(), 0, 0, width, height, null);
                g.drawImage(Assets.snowIcon, 750, 25, 42, 42, null);
                break;
        }
    }
    
}
