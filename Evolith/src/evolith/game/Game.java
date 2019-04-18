
package evolith.game;

import evolith.menus.MainMenu;
import evolith.menus.SetupMenu;
import evolith.menus.ButtonBarMenu;
import evolith.helpers.Clock;
import evolith.helpers.Commons;
import evolith.entities.ResourceManager;
import evolith.entities.OrganismManager;
import evolith.entities.PredatorManager;
import evolith.engine.*;
import evolith.entities.Resource;
import evolith.helpers.InputReader;
import evolith.helpers.Selection;
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
    
    private PredatorManager predators;

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
    
    private Selection selection;
    
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
        minimap = new Minimap(MINIMAP_X,MINIMAP_Y,MINIMAP_WIDTH,MINIMAP_HEIGHT, this);

        state = States.MainMenu;
        selection = new Selection(this);
        
    }

    /**
     * start main game thread
     */
    @Override
    public void run() {
        init();
        
        int fps = 60;//Current game requirements demand 60 fps
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
        //minimap = new Minimap(MINIMAP_X,MINIMAP_Y,MINIMAP_WIDTH,MINIMAP_HEIGHT, this);
        organisms = new OrganismManager(this);
        predators = new PredatorManager(this);
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
            System.out.println(setupMenu.getOption());
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
        predators.tick();
        buttonBar.tick();
        inputKeyboard.tick();
        selection.tick();
        
        manageMouse();
        checkEntitiesInteraction();
    }
    
    /**
     * Handle the mouse while in game
     */
    private void manageMouse() {
        //Check for click
        if (mouseManager.isLeft()) {
            manageLeftClick();
        } else if (mouseManager.isRight()){
            manageRightClick();
        } else {
            //Dragging is false
            organisms.checkHover();
            selection.deactivate();
            //System.out.println("DEACTIVATING SELECTION");
        }
    }
    
    public void manageLeftClick() {
        int mouseX = mouseManager.getX();
        int mouseY = mouseManager.getY();
        
        //System.out.println("LEFT CLICKED");

        /**
         * This set of if-else statements allows for processing the mouse in the screen only once per frame
         * This prevents the mouse triggering multiple events where elements in the screen may overlap
         * For example, it prevents the organisms to move when the player clicks on the button bar
         */
        //First in hierarchy is the buttonbar
        if (buttonBar.hasMouse(mouseX, mouseY)) {
            //Process the mouse in the button bar
            buttonBar.applyMouse(mouseX, mouseY);
            organisms.setSelectedSearchFood(buttonBar.isFoodActive());
            organisms.setSelectedSearchWater(buttonBar.isWaterActive());
            organisms.emptySelectedTargets();
            mouseManager.setLeft(false);
        //Second in hierarchy is the minimap
        } else if(minimap.hasMouse(mouseX,mouseY)){
            minimap.applyMouse(mouseX, mouseY, camera);
            mouseManager.setLeft(false);
        //Third in hierarchy is the background   
        } else if (organisms.checkPanel()){
            mouseManager.setLeft(false);
        } else {
            //System.out.println("START DRAGGING");

            if (!selection.isActive()) {
                selection.activate(camera.getAbsX(mouseX), camera.getAbsY(mouseY));
            }

            checkOrganismsInSelection();
        }
    }
    
    public void manageRightClick() {
        int mouseX = mouseManager.getX();
        int mouseY = mouseManager.getY();
        
        //System.out.println("RIGHT CLICKED");

        if (buttonBar.hasMouse(mouseX, mouseY)) {
            mouseManager.setRight(false);
        //Second in hierarchy is the minimap
        } else if(minimap.hasMouse(mouseX,mouseY)){
            mouseManager.setRight(false);
        //Third in hierarchy is the background   
        } else if (organisms.checkPanel()){
            mouseManager.setRight(false);
        } else {
            selection.deactivate();
            Resource clickedResource = resources.containsResource(camera.getAbsX(mouseX), camera.getAbsY(mouseY));

            //if clicked is not null, a resource has been clicked
            if (clickedResource != null) {
                organisms.emptySelectedTargets();
                //Set the resource to the selected organisms
                organisms.setSelectedResource(clickedResource);
                if (clickedResource.getType() == Resource.ResourceType.Plant) {
                    organisms.setSelectedSearchFood(true);
                    organisms.setSelectedSearchWater(false);
                } else {
                    organisms.setSelectedSearchWater(true);
                    organisms.setSearchFood(false);
                }
            } else {
                //Else move the swarm to desired position and deactivate all searching

                organisms.moveSelectedSwarm(camera.getAbsX(mouseX), camera.getAbsY(mouseY));
                organisms.emptySelectedTargets();
                organisms.setSelectedResource(null);
                organisms.setSelectedSearchFood(false);
                organisms.setSelectedSearchWater(false);
            }
        }
        
        mouseManager.setRight(false);
    }
    
    public void checkEntitiesInteraction() {
        organisms.checkArrivalOnResource();
        organisms.checkOrganismResourceStatus();
    }
    
    public void checkOrganismsInSelection() {
        organisms.checkSelection(selection.getSel());
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
                    resources.render(g);
                    organisms.render(g);
                    predators.render(g);
                    minimap.render(g);
                    buttonBar.render(g);
                    if (selection.isActive()) {
                        selection.render(g);
                    }
                    break;
            }
            /*g.drawString(Integer.toString(camera.getAbsX(mouseManager.getX())), 30, 650);
            g.drawString(Integer.toString(camera.getAbsY(mouseManager.getY())), 80, 650);*/
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
    
    public PredatorManager getPredators(){
        return predators;
    }
    
    
    public OrganismManager getOrganisms() {
        return organisms;
    }

    public ResourceManager getResources() {
        return resources;
    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
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
