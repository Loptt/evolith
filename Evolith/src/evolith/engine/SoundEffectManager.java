/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.engine;

import evolith.helpers.Time;
import java.util.ArrayList;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class SoundEffectManager {
    private Song predatorSound;
    private Song waterSound;
    private Song plantSound;
    private Song organismSound;
    private Song rainSound;
    private Song stormSound;
    private Song windSound;
    private Song backSound;
    private Song nextSound;
    private Song overSound;
    private Song winSound;
    private int secondStartAlien;
    private Time time;
    private boolean predator, water, plant, organism, rain, storm, snow, next, back, win, over;
    private Song rainsound;
    private Song stormsound;
    private Song snowsound;

    /**
     * Sound effects manager constructor
     * 
     */
    public SoundEffectManager() {
        time = new Time();
        predator = false;
        water = false;
        plant = false;
        organism = false;
        rain = false;
        storm = false;
        snow = false;
        next = false;
        back = false;
        win = false;
        over = false;
        predatorSound = new Song(Assets.aliensound, 5);
        waterSound = new Song(Assets.watersound, 1);
        plantSound = new Song(Assets.grasssound, 1); 
        nextSound = new Song(Assets.nextsound, 1);
        backSound = new Song(Assets.backsound, 1);
        rainsound = new Song(Assets.rainsound, 40);
        stormsound = new Song(Assets.stormsound, 40);
        snowsound = new Song(Assets.snowsound, 40);
        secondStartAlien = 0;
    }
    
    /**
     * To update the object each frame
     */
    public void tick() {
        time.tick();
        if(predator){
            if (time.getSeconds() >= secondStartAlien + predatorSound.duration) {
                predatorSound.play();
                secondStartAlien = (int) time.getSeconds();
                predator = false;
            }
        }
        if(water){
            waterSound.play();
            water = false;
        }
        if(plant){
            plantSound.play();
            plant  = false;
        }
        if(next){
            nextSound.play();
            next = false;
        }
        if(back){
            backSound.play();
            back = false;
        }
        if(win){
            winSound.play();
            win =false;
        }
        if(over){
            overSound.play();
            over = false;
        }
        if(rain){
            rainsound.play();
            rain = false;
        }
        if(storm){
            stormsound.play();
            storm = false;
        }
        if(snow){
            System.out.println("snow plays");
            snowsound.play();
            snow = false;
        }
        
    }
    
    /**
     * Play the predator's sound
     */
    public void playAlien() {
        predator = true;
    }
    
    /**
     * Play the water's sound
     */
    public void playWater() {
        water = true;
    }
    
    /**
     * Play the plant's sound
     */
    public void playPlant() {
        plant = true;
    }
    
    public void playNext(){
        next = true;
    }
    
    public void playBack(){
        back = true;
    }
    
    public void playWin(){
        win = true;
    }
    
    public void playOver(){
        over = true;
    }

    /**
     * To set the predator's status
     * 
     * @param predator 
     */
    public void setPredator(boolean predator) {
        this.predator = predator;
    }

    /**
     * To get the predator's status
     * 
     * @return predator
     */
    public boolean isPredator() {
        return predator;
    }
    
    public void playRain(){
        rain = true;
    }
    
    public void stopRain(){
        rain = false;
        rainsound.stop();
    }
    
    public void playStorm(){
        storm = true;
    }
    
    public void stopStorm(){
        storm = false;
        stormsound.stop();
    }
    
    public void playSnow(){
        snow = true;
    }
    
    public void stopSnow(){
        snow = false;
        snowsound.stop();
    }
    
    private class Song {
        private SoundClip clip;
        private int duration;
        
        public Song (SoundClip clip, int duration) {
            this.clip = clip;
            this.duration = duration;
        }
        
        public void play() {
            clip.play();
        }
        
        public void stop(){
            clip.stop();
        }
    }
    
    
}
