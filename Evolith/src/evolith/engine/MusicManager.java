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
public class MusicManager {
    private ArrayList<Song> songs;  // Array of the songs of the game
    private int currentSong;        // The number of the song being played
    private int secondStart;        // The second at which a song starts
    private Time time;              // To manage the time while the songs are played

    /**
     * The constructor of the music manager
     */
    public MusicManager() {
        time = new Time();
        songs = new ArrayList<Song>();  
        songs.add(new Song(Assets.fatrat_afterlife, 95));
        songs.add(new Song(Assets.fatrat_threnody, 75));
        songs.add(new Song(Assets.originalsong, 29));
    }
    
    /**
     * Updates every frame
     */
    public void tick() {
        time.tick();
        
        // To play the next song when the time passed exceeds the current song's duration
        if (time.getSeconds() >= secondStart + songs.get(currentSong).duration) {
            currentSong++;
            
            currentSong = currentSong % songs.size();
            songs.get(currentSong).play();
            secondStart = (int) time.getSeconds();
        }
    }
    
    /**
     * To start playing the songs
     */
    public void play() {
        time.setTicker(0);
        currentSong = 0;
        secondStart = (int) time.getSeconds();
        
        for (int i = 0; i < songs.size(); i++) {
            songs.get(i).stop();
        }
        
        songs.get(currentSong).play();

    }
    
    /**
     * To stop playing the songs
     */
    public void stop() {
        time.setTicker(0);
        secondStart = 0;
        songs.get(currentSong).stop();
        
        for (int i = 0; i < songs.size(); i++) {
            songs.get(i).stop();
        }
    }
    
    private class Song {
        private SoundClip clip;     // A song
        private int duration;       // The duration of the song
        
        /**
         * The constructor of a song
         * 
         * @param clip
         * @param duration 
         */
        public Song (SoundClip clip, int duration) {
            this.clip = clip;
            this.duration = duration;
        }
        
        /**
         * Play a song
         */
        public void play() {
            clip.play();
        }
        
        /**
         * Stop a song
         */
        public void stop() {
            clip.stop();
        }
    }
}
