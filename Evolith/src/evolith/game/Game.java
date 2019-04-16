
package evolith.game;

import evolith.menus.MainMenu;
import evolith.menus.SetupMenu;
import evolith.menus.ButtonBarMenu;
import evolith.helpers.Clock;
import evolith.helpers.Commons;
import evolith.entities.ResourceManager;
import evolith.entities.OrganismManager;
import evolith.engine.*;
import evolith.entities.Resource;
import evolith.helpers.InputReader;
import java.awt.Graphics;
import java.awt.Point;
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
    private InputKeyboard inputKeyboard;        // manages the input of the keyboard of the setup menu

    private Background background;              // background of the game engine
    private Camera camera;                      // camera of the game engine

    private OrganismManager organisms;                //organisms in the game
    //private Plants plants;                      // resources of plants in the game
    //private Waters waters;
    private ResourceManager resources;

    private enum States {
        MainMenu, Paused, GameOver, Play, Instructions, SetupMenu
    } // status of the flow of the game once running
    private States state;

    private MainMenu mainMenu;                  // main menu
    private ButtonBarMenu buttonBar;
    private SetupMenu setupMenu;

    private Clock clock;                        // the time of the game
    
    private InputReader inputReader;

    private Minimap minimap;
    
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
        camera = new Camera(INITIAL_POINT - width / 2, INITIAL_POINT - height / 2, width, height, this);
        mainMenu = new MainMenu(0, 0, width, height, this);
        inputKeyboard = new InputKeyboard();
        minimap = new Minimap(this);

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
        buttonBar = new ButtonBarMenu(10, 10, 505, 99, this);
        setupMenu = new SetupMenu(0, 0, width, height, this);

        organisms = new OrganismManager(this);
        //plants = new Plants(this);
        //waters = new Waters(this);
        resources = new ResourceManager(this);
        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addKeyListener(inputKeyboard);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }

    /**
     * updates all objects on a frame
     */
    private void tick() {
        //Every single case is separated in its own function
        
        clock.tick();
        switch (state) {
            case MainMenu:
                mainMenuTick();
                break;
            case SetupMenu:
                setupMenuTick();
                break;
            case Play:
                playTick();
                break;
        }

    }
    
    /**
     * Tick the main menu
     */
    private void mainMenuTick() {
        mainMenu.tick();
        mainMenu.setActive(true);
        if (mainMenu.isClickPlay()) {
            mainMenu.setActive(false);
            state = States.SetupMenu;
        }
    }
    
    /**
     * Tick the setup menu
     */
    private void setupMenuTick() {
        setupMenu.tick();
        setupMenu.setActive(true);
        inputKeyboard.tick();

        if (setupMenu.isClickPlay()) {
            setupMenu.setActive(false);
            organisms.setSkin(setupMenu.getOption());
            state = States.Play;
        }
    }
    
    /**
     * Tick the main game
     */
    private void playTick() {
        keyManager.tick();
        camera.tick();
        organisms.tick();
        resources.tick();
        buttonBar.tick();
        inputKeyboard.tick();
        
        manageMouse();
        checkEntitiesInteraction();
    }
    
    /**
     * Handle the mouse while in game
     */
    private void manageMouse() {
        //Check for click
        if (mouseManager.isLeft()) {
            int mouseX = mouseManager.getX();
            int mouseY = mouseManager.getY();
            
            /**
             * This set of if-else statements allows for processing the mouse in the screen only once per frame
             * This prevents the mouse triggering multiple events where elements in the screen may overlap
             * For example, it prevents the organisms to move when the player clicks on the button bar
             */
            //Check if the mouse is over the buttonbar
            if (buttonBar.hasMouse(mouseX, mouseY)) {
                //Process the mouse in the button bar
                buttonBar.applyMouse(mouseX, mouseY);
            } else {
                //System.out.println("Removing targets in game");
                organisms.emptyTargets();
                Resource clickedResource = resources.containsResource(camera.getAbsX(mouseX), camera.getAbsY(mouseY));
                
                //If the x value is greater than 0, then a plant has been clicked
                if (clickedResource != null) {
                    
                    //In this case, move the selected swarm to the selected resource
                    //organisms.moveSwarmToPoint(clickedResource.getX(), clickedResource.getY(), 1);
                    organisms.setResource(clickedResource);
                    if (clickedResource.getType() == Resource.ResourceType.Plant) {
                        organisms.setSearchFood(true);
                    } else {
                        organisms.setSearchWater(true);
                    }
                } else {
                    //Else move the swarm to desired position
                    organisms.moveSwarm(camera.getAbsX(mouseX), camera.getAbsY(mouseY));
                    organisms.setResource(null);
                    organisms.setSearchFood(false);
                    organisms.setSearchWater(false);
                    //organisms.applyMouse(camera.getAbsX(mouseX), camera.getAbsY(mouseY));
                }
                
            }
            
            mouseManager.setLeft(false);
        } else {
            //Check for hover
        }
    }
    
    public void checkEntitiesInteraction() {
        organisms.checkArrivalOnResource();
        organisms.checkOrganismResourceStatus();
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
                case SetupMenu:
                    setupMenu.render(g);
                    break;
                case Play:
                    g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);
                    //plants.render(g);
                    resources.render(g);
                    organisms.render(g);
                    minimap.render(g);
                    buttonBar.render(g);
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
     * to get input keyboard
     *
     * @return keyManager
     */
    public InputKeyboard getInputKeyboard() {
        return inputKeyboard;
    } 

    /**
     * to get input of the keyboard in the setup menu
     *
     * @return keyManager
     */
    public InputReader getInputReader() {
        return inputReader;
    }
    
    /**
     * to get the skin of the organism
     *
     * @return organismsSkin
     */
    public int getOrganismsSkin() {
        return organisms.getSkin();
    }
    
    /**
     * to get the organisms
     *
     * @return organisms
     */
    public OrganismManager getOrganisms() {
        return organisms;
    }

    public ResourceManager getResources() {
        return resources;
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
