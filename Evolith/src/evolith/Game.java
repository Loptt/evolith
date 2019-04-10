package evolith;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Game implements Runnable, Commons {

    private BufferStrategy bs;                  //buffer strategy to render
    private Graphics g;                         //graphics to render
    private Display display;                    //main display of the game

    String title;                               //title of the game
    private int width;                          // width dimension of the game
    private int height;                         // height dimension of the game

    private Thread thread;                      //thread running the game
    private boolean running;                    // to determine if the game is running

    private KeyManager keyManager;              // manages the keyboard 
    private MouseManager mouseManager;          // manages the mouse

    private Background background;              // background of the game engine
    private Camera camera;                      // camera of the game engine

    private Organisms organisms;                //organisms in the game
    private Plants plants;                      // resources of plants in the game

    private enum States {
        MainMenu, Paused, GameOver, Play, Instructions
    } // status of the flow of the game once running
    private States state;

    private MainMenu mainMenu;                  // main menu 

    private Clock clock;                        // the time of the game

    /**
     * to create title, width and height and set the game is still not running
     *
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
        camera = new Camera(10, 10, width, height, this);
        mainMenu = new MainMenu(0, 0, width, height, this);

        state = States.MainMenu;
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

        clock = new Clock(0, 0, 100, 100);
        display = new Display(title, width, height);
        Assets.init();

        background = new Background(Assets.background, 5000, 5000, width, height);

        organisms = new Organisms(this);
        plants = new Plants(this);

        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }

    /**
     * updates all objects on a frame
     */
    private void tick() {
        clock.tick();
        switch (state) {
            case MainMenu:
                mainMenu.tick();
                mainMenu.setActive(true);
                if (mainMenu.isClickPlay()) {
                    mainMenu.setActive(false);
                    state = States.Play;
                }
                break;
            case Play:
                keyManager.tick();
                camera.tick();
                organisms.tick();
                plants.tick();
                break;
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
        } else {
            g = bs.getDrawGraphics();
            g.clearRect(0, 0, width, height);

            switch (state) {
                case MainMenu:
                    mainMenu.render(g);
                    break;
                case Play:
                    g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);
                    plants.render(g);
                    organisms.render(g);
                    break;
            }
            bs.show();
            g.dispose();
        }
    }

    /**
     * Saves current game status into a text file Each important variable to
     * define the current status of the game is stored in the file in a specific
     * order
     */
    private void saveGame() {
    }

    /**
     * Load game from text file This method open the designated text file and
     * reads its contents and assigns them to their designated variables
     */
    private void loadGame() {
    }

    public void resetGame() {
    }

    /**
     * to get width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * to get height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * to get key manager
     *
     * @return keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * to get mouseManager
     *
     * @return mouseManager
     */
    public MouseManager getMouseManager() {
        return mouseManager;
    }

    /**
     * to get camera
     *
     * @return camera
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * to get the background
     *
     * @return background
     */
    public Background getBackground() {
        return background;
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
