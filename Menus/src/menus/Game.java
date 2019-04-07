/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author charles
 */
public class Game implements Runnable, Commons {
    
    private BufferStrategy bs;
    private Graphics g;
    private Display display;
    
    String title;
    private int width;
    private int height;
    
    private Thread thread;
    private boolean running;
    
    private KeyManager keyManager;
    
    /**
    * to create title, width and height and set the game is still not running
    * @param title to set the title of the window
    * @param width to set the width of the window
    * @param height to set the height of the window
    */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }
    
     /**
     * start main game thread
     */
    @Override
    public void run() {
        init();
        
        int fps = 60; //Current game requirements demand 60 fps
        double timeTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        
        while (running) {
            
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        
        stop();
    }
    
    /**
    * initializing the display window of the game
    */
    private void init() {
        display = new Display(title, width, height);
        Assets.init();
        
        display.getJframe().addKeyListener(keyManager);
    }
    
    /**
     * updates all objects on a frame
     */
    private void tick() {
    }
    
    /**
     * renders all objects in a frame
     */
    private void render() {
        Toolkit.getDefaultToolkit().sync(); //Linux
        bs = display.getCanvas().getBufferStrategy();
        
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        }
        else {
            g = bs.getDrawGraphics();
            g.clearRect(0, 0, width, height);
            
            bs.show();
            g.dispose();     
        }
    }
    
    /**
     * Saves current game status into a text file
     * Each important variable to define the current status of the game is
     * stored in the file in a specific order
     */
    private void saveGame() {
    }
    
    /**
     * Load game from text file
     * This method open the designated text file and reads its contents
     * and assigns them to their designated variables
     */
    private void loadGame() {
    }
    
    public void resetGame() {
    }
    
    /**
     * to get width
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * to get height
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * to get key manager
     * @return keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    /**
     * start game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stop game
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
