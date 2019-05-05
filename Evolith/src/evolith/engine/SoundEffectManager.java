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
 * @author victor
 */
public class SoundEffectManager {
    private Song predatorSound;
    private Song waterSound;
    private Song plantSound;
    private Song organismSound;
    private Song rainSound;
    private Song stormSound;
    private Song windSound;
    private int secondStartAlien;
    private Time time;
    private boolean predator, water, plant, organism, rain, storm, wind, start;

    public SoundEffectManager() {
        time = new Time();
        predator = false;
        water = false;
        plant = false;
        organism = false;
        rain = false;
        storm = false;
        wind = false;
        start = false;
        predatorSound = new Song(Assets.aliensound, 3);
        waterSound = new Song(Assets.watersound, 1);
        plantSound = new Song(Assets.grasssound, 1); 
        secondStartAlien = 0;
    }
    
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
        
    }
    
    public void playAlien() {
        predator = true;
    }
    
    public void playWater() {
        water = true;
    }
    
    public void playPlant() {
        plant = true;
    }

    public void setPredator(boolean predator) {
        this.predator = predator;
    }

    public boolean isPredator() {
        return predator;
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
    }
    
    
}
