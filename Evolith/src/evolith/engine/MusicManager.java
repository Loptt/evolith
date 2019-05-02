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
        songs.add(new Song(Assets.fatrat, 190));
        songs.add(new Song(Assets.ricardomilos, 256));
    }
    
    public void tick() {
        time.tick();
        
        if (time.getSeconds() >= secondStart + songs.get(currentSong).duration) {
            currentSong++;
            
            currentSong = currentSong % songs.size();
            songs.get(currentSong).play();
            secondStart = (int) time.getSeconds();
        }
    }
    
    public void play() {
        currentSong = 0;
        secondStart = (int) time.getSeconds();
        
        songs.get(currentSong).play();
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
