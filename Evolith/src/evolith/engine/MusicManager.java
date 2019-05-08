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
 * @author moisesfernandez
 */
public class MusicManager {
    private ArrayList<Song> songs;
    private int currentSong;
    private int secondStart;
    private Time time;

    public MusicManager() {
        time = new Time();
        songs = new ArrayList<Song>();  
        songs.add(new Song(Assets.fatrat_afterlife, 95));
        songs.add(new Song(Assets.fatrat_threnody, 75));
        songs.add(new Song(Assets.originalsong, 29));
    }
    
    public void tick() {
        time.tick();
        
        System.out.println(time.getSeconds() + " " + secondStart + " " + songs.get(currentSong).duration);
        
        if (time.getSeconds() >= secondStart + songs.get(currentSong).duration) {
            currentSong++;
            
            currentSong = currentSong % songs.size();
            songs.get(currentSong).play();
            secondStart = (int) time.getSeconds();
        }
    }
    
    public void play() {
        time.setTicker(0);
        currentSong = 0;
        secondStart = (int) time.getSeconds();
        
        songs.get(currentSong).play();
    }
    
    public void stop() {
        time.setTicker(0);
        secondStart = 0;
        songs.get(currentSong).stop();
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
        
        public void stop() {
            clip.stop();
        }
    }
}
