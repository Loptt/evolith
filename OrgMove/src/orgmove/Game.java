/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orgmove;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

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
    private MouseManager mouseManager;
    
    private Background background;
    private Camera camera;
    
    private ArrayList<Player> playerSwarm;
    
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
        
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        camera = new Camera(10, 10, width, height);
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
        
        background = new Background(Assets.background , 3200, 3200, width, height);
        
        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        
        SwarmMovement.init();
        
        playerSwarm = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            Player player = new Player(50 * i, 300, 100, 100, this);
            playerSwarm.add(player);
        }
    }
    
    /**
     * updates all objects on a frame
     */
    private void tick() {
        ArrayList<Point> points = new ArrayList<>();
        keyManager.tick();
        
        if (keyManager.w) {
            if(camera.getY() - 5 <= 10){
                camera.setY(10);
            }
            else{
                camera.setY(camera.getY() - 5);
            }
        }
        if (keyManager.a) {
            if(camera.getX() - 5 <= 10){
                camera.setX(10);
            }
            else{
                camera.setX(camera.getX() - 5);
            }
        }
        if (keyManager.s) {
            if(camera.getY() + 5 >= background.getHeight() - getHeight() - 10){
                camera.setY(background.getHeight() - getHeight() - 10);
            }
            else{
                camera.setY(camera.getY() + 5);
            }                                    
        }
        if (keyManager.d) {
            if(camera.getX() + 5 >= background.getWidth() - getWidth() - 10){
                camera.setX(background.getWidth() - getWidth() - 10);
            }
            else{
                camera.setX(camera.getX() + 5);
            }
        }
        
        if (mouseManager.isIzquierdo()) {
            points = SwarmMovement.getPositions(camera.getAbsX(mouseManager.getX()),
                    camera.getAbsY(mouseManager.getY()), playerSwarm.size());
            for (int i = 0; i < playerSwarm.size(); i++) {
                playerSwarm.get(i).setPoint(points.get(i));
                System.out.println(points.get(i));
            }
        }
        
        for (int i = 0; i < playerSwarm.size(); i++) {
            playerSwarm.get(i).tick();
        }
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
            g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);
            
            for (int i = 0; i < playerSwarm.size(); i++) {
                playerSwarm.get(i).render(g);
            }
            
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

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public Camera getCamera() {
        return camera;
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
