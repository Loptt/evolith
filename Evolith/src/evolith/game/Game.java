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
import evolith.menus.InstructionMenu;
import evolith.menus.ModeMenu;
import evolith.menus.OverMenu;
import evolith.menus.PauseMenu;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private MusicManager musicManager;
    private NetworkManager network;

    private Background background;              // background of the game engine
    private Camera camera;                      // camera of the game engine

    private OrganismManager organisms;                //organisms in the game
    private OrganismManager otherOrganisms;
    
    private ResourceManager resources;

    private PredatorManager predators;

    public enum States {
        MainMenu, Paused, GameOver, Play, Instructions, SetupMenu, Multi, ModeMenu
    } // status of the flow of the game once running
    private States state;

    private MainMenu mainMenu;                  // main menu
    private ButtonBarMenu buttonBar;
    private SetupMenu setupMenu;
    private PauseMenu pauseMenu;
    private OverMenu overMenu;
    private InstructionMenu instructionMenu;
    private ModeMenu modeMenu;

    private Clock clock;                        // the time of the game
    private InputReader inputReader;            //To read text from keyboard
    private Minimap minimap;
    private Selection selection;

    private boolean night;
    private int prevSecDayCycleChange;
    
    private boolean win;
    private boolean server;

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
        minimap = new Minimap(MINIMAP_X, MINIMAP_Y, MINIMAP_WIDTH, MINIMAP_HEIGHT, this);

        state = States.MainMenu;
        selection = new Selection(this);

        night = false;
        server = true;
        prevSecDayCycleChange = 0;
        win = false;
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

        background = new Background(5000, 5000, width, height);
        buttonBar = new ButtonBarMenu(10, 10, 505, 99, this);
        setupMenu = new SetupMenu(0, 0, width, height, this);
        pauseMenu = new PauseMenu(width / 2 - 250 / 2, height / 2 - 300 / 2, 250, 300, this);
        
        musicManager = new MusicManager();
        //minimap = new Minimap(MINIMAP_X,MINIMAP_Y,MINIMAP_WIDTH,MINIMAP_HEIGHT, this);
        organisms = new OrganismManager(this, false);
        predators = new PredatorManager(this);
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

        switch (state) {
            case MainMenu:
                mainMenuTick();
                break;
            case Instructions:
                instructionsTick();
                break;
            case ModeMenu:
                modeTick();
                break;
            case SetupMenu:
                setupMenuTick();
                break;
            case Play:
                playTick();
                break;
            case Multi:
                multiTick();
                break;
            case Paused:
                pausedTick();
                break;
            case GameOver:
                overTick();
        }

    }

    /**
     * Tick the main menu
     */
    private void mainMenuTick() {
        mainMenu.setActive(true);
        mainMenu.tick();
        if (mainMenu.isClickPlay()) {
            mainMenu.setActive(false);
            state = States.ModeMenu;
            mainMenu.setClickPlay(false);
            modeMenu = new ModeMenu(0, 0, width, height, this);
        }
        
        if (mainMenu.isClickIns()) {
            instructionMenu = new InstructionMenu(0, 0, width, height, this);
            mainMenu.setActive(false);
            state = States.Instructions;
            mainMenu.setClickIns(false);
        }
    }
    
    private void modeTick() {
        modeMenu.tick();
        inputKeyboard.tick();
        
        if (modeMenu.isSingle()) {
            resetGame();
            state = States.SetupMenu;
            modeMenu.setSingle(false);
        }
        
        if (modeMenu.isLoad()) {
            loadGame();
            state = States.Play;
            modeMenu.setLoad(false);
        }
        
        if (modeMenu.isHost()) {
            state = States.Multi;
            resetGameMutli();
            modeMenu.setHost(false);
        }
        
        if (modeMenu.isJoin()) {
            state = States.Multi;
            resetGameMutli();
            modeMenu.setJoin(false);
        }
    }
    
    private void instructionsTick() {
        instructionMenu.tick();
        
        if (instructionMenu.isOver()) {
            state = States.MainMenu;
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
            initSinglePlayer();
            setupMenu.setClickPlay(false);
        }
    }

    /**
     * Tick the main game
     */
    private void playTick() {
        clock.tick();
        
        organisms.tick();
        resources.tick();
        predators.tick();
        buttonBar.tick();
        inputKeyboard.tick();
        selection.tick();
        
        keyManager.tick();
        musicManager.tick();
        
        if (!organisms.getOrgPanel().isInputActive()) {
            camera.tick();
        }

        manageMouse();
        manageKeyboard();
        
        if (clock.getSeconds() >= prevSecDayCycleChange + DAY_CYCLE_DURATION_SECONDS) {
            night = !night;
            background.setNight(night);
            prevSecDayCycleChange = clock.getSeconds();
        }
        
        organisms.checkKill();
        checkGameOver();
    }
    
    
    
    private void multiTick() {
        clock.tick();
        organisms.tick();
        otherOrganisms.tick();
        resources.tick();
        buttonBar.tick();
        inputKeyboard.tick();
        selection.tick();
        
        if (server) {
            resources.respawnResources(); 
        }
        
        network.sendDataPlants(resources);
        network.sendDataWaters(resources);
        network.sendData(organisms);
        
        if (network.isOtherExtinct()) {
            state = States.GameOver;
            win = true;
            overMenu = new OverMenu(0, 0, width, height, this, win);
        }
        
        if (network.isOtherWon()) {
            state = States.GameOver;
            win = false;
            overMenu = new OverMenu(0, 0, width, height, this, win);
        }

        keyManager.tick();
        musicManager.tick();
        
        if (!organisms.getOrgPanel().isInputActive()) {
            camera.tick();
        }

        manageMouse();
        manageKeyboard();
        
        if (server) {
            if (clock.getSeconds() >= prevSecDayCycleChange + DAY_CYCLE_DURATION_SECONDS) {
                night = !night;
                background.setNight(night);
                prevSecDayCycleChange = clock.getSeconds();
            }
        }
        
        organisms.checkKill();
        otherOrganisms.checkKill();
        
        if (server) {
            resources.deleteResources();
        }
        
        checkGameOver();
    }
    
    private void pausedTick() {
        pauseMenu.tick();
        keyManager.tick();
        
        if (keyManager.p) {
            state = States.Play;
        }
        
        if (keyManager.esc) {
            state = States.Play;
        }
        
        if (!pauseMenu.isMainMenuDisplayed()) {
            state = States.Play;
        }
        
        if (pauseMenu.isClickSave()) {
            saveGame();
            pauseMenu.setClickSave(false);
        }
        
        if (pauseMenu.isClickLoad()) {
            loadGame();
            pauseMenu.setClickLoad(false);
        }
        
        if (pauseMenu.isClickExit()) {
            pauseMenu.setClickExit(false);
            state = States.MainMenu;
            resetGame();
        }
    }
    
    private void overTick() {
        overMenu.tick();
        
        if (overMenu.isMainMenu()) {
            overMenu.setMainMenu(false);
            state = States.MainMenu;
            resetGame();
        }
        
        if (overMenu.isStats()) {
            overMenu.setStats(false);
            System.out.println("STATS NOT READY");
        }
    }
    
    private void initSinglePlayer() {
        organisms.setSpeciesName(setupMenu.getName());
        setupMenu.setName("");
        organisms.setSkin(setupMenu.getOption());
        state = States.Play;
        musicManager.play();
        resources.init();
    }
    
    private void mutliInit() {
        Scanner sc = new Scanner(System.in);
        System.out.print("SERVER?:  ");
        int i = sc.nextInt();
        server = i == 1;
        
        if (server) {
            network = new NetworkManager(true, otherOrganisms, resources, predators);
            network.initServer();
            organisms.setSkin(0);
            otherOrganisms.setSkin(2);
            resources.init();
        } else {
            network = new NetworkManager(false, otherOrganisms, resources, predators);
            network.initClient("localhost", 5000);
            organisms.setSkin(2);
            otherOrganisms.setSkin(0);
        }
        
        Thread myThread = new Thread(network);
        
        myThread.start();
    }
    
    public void mutliInitServer() {
        server = true;
        otherOrganisms = new OrganismManager(this, true);
        network = new NetworkManager(true, otherOrganisms, resources, predators);
        network.initServer();
        organisms.setSkin(0);
        otherOrganisms.setSkin(2);
        resources.init();

        Thread myThread = new Thread(network);
        
        myThread.start();
    }

    public void multiInitClient(String address) {
        server = false;
        otherOrganisms = new OrganismManager(this, true);
        network = new NetworkManager(false, otherOrganisms, resources, predators);
        network.initClient(address, 5000);
        organisms.setSkin(2);
        
        otherOrganisms.setSkin(0);
        
        Thread myThread = new Thread(network);
        
        myThread.start();
    }

    /**
     * Handle the mouse while in game
     */
    private void manageMouse() {
        //Check for click
        if (mouseManager.isLeft()) {
            manageLeftClick();
        } else if (mouseManager.isRight()) {
            manageRightClick();
        } else {
            selection.deactivate();
        }
    }
    
    private void manageKeyboard() {
        if (!organisms.getOrgPanel().isActive()) {
            if (keyManager.esc) {
                pauseMenu.setMainMenuDisplayed(true);
                state = States.Paused;
            }
        } else {
            if (keyManager.esc) {
                organisms.getOrgPanel().setActive(false);
            }
        }
        
        if (keyManager.p && !organisms.getOrgPanel().isInputActive()) {
            pauseMenu.setMainMenuDisplayed(true);
            state = States.Paused;
        }
        
        if (keyManager.num1) {
            buttonBar.setWaterActive(!buttonBar.isWaterActive());
            organisms.setSelectedSearchFood(buttonBar.isFoodActive());
            organisms.setSelectedSearchWater(buttonBar.isWaterActive());
            organisms.setSelectedAggressiveness(buttonBar.isFightActive());
            organisms.emptySelectedTargets();
        }
        
        if (keyManager.num2) {
            buttonBar.setFoodActive(!buttonBar.isFoodActive());
            organisms.setSelectedSearchFood(buttonBar.isFoodActive());
            organisms.setSelectedSearchWater(buttonBar.isWaterActive());
            organisms.setSelectedAggressiveness(buttonBar.isFightActive());
            organisms.emptySelectedTargets();
        }
        
        if (keyManager.num3) {
            buttonBar.setFightActive(!buttonBar.isFightActive());
            organisms.setSelectedSearchFood(buttonBar.isFoodActive());
            organisms.setSelectedSearchWater(buttonBar.isWaterActive());
            organisms.setSelectedAggressiveness(buttonBar.isFightActive());
            organisms.emptySelectedTargets();
        }
    }

    public void manageLeftClick() {
        int mouseX = mouseManager.getX();
        int mouseY = mouseManager.getY();

        /**
         * This set of if-else statements allows for processing the mouse in the
         * screen only once per frame This prevents the mouse triggering
         * multiple events where elements in the screen may overlap For example,
         * it prevents the organisms to move when the player clicks on the
         * button bar
         */
        //First in hierarchy is the buttonbar
        if (organisms.isOrgPanelActive() || organisms.isMutPanelActive()) {
            //Let the panels handle mouse activity
        } else if (selection.isActive()) {
            checkOrganismsInSelection();
        } else if (organisms.checkPanel()) {
            mouseManager.setLeft(false);
        } else if (buttonBar.hasMouse(mouseX, mouseY)) {
            //Process the mouse in the button bar
            buttonBar.applyMouse(mouseX, mouseY);
            organisms.setSelectedSearchFood(buttonBar.isFoodActive());
            organisms.setSelectedSearchWater(buttonBar.isWaterActive());
            organisms.setSelectedAggressiveness(buttonBar.isFightActive());
            organisms.emptySelectedTargets();
            mouseManager.setLeft(false);
            //Second in hierarchy is the minimap
        } else if (minimap.hasMouse(mouseX, mouseY)) {
            minimap.applyMouse(mouseX, mouseY, camera);
            mouseManager.setLeft(false);
            //Third in hierarchy is the background   
        } else {
            selection.activate(camera.getAbsX(mouseX), camera.getAbsY(mouseY));
        }
    }

    public void manageRightClick() {
        int mouseX = mouseManager.getX();
        int mouseY = mouseManager.getY();

        if (buttonBar.hasMouse(mouseX, mouseY)) {
            mouseManager.setRight(false);
            //Second in hierarchy is the m6000inimap
        } else if (minimap.hasMouse(mouseX, mouseY)) {
            mouseManager.setRight(false);
            //Third in hierarchy is the background   
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
                organisms.setSelectedGodCommand(true);
                organisms.moveSelectedSwarm(camera.getAbsX(mouseX), camera.getAbsY(mouseY));
                organisms.emptySelectedTargets();
                organisms.setSelectedResource(null);
                organisms.setSelectedSearchFood(false);
                organisms.setSelectedSearchWater(false);
            }
        }

        mouseManager.setRight(false);
    }
    
    public void checkGameOver() {
        if (organisms.getAmount() <= 0) {
            network.sendDataExtinct();
            state = States.GameOver;
            win = false;
            System.out.println("OVER");
            overMenu = new OverMenu(0, 0, width, height, this, win);
        }
        
        if (organisms.isMaxIntelligence()) {
            network.sendDataWin();
            state = States.GameOver;
            win = true;
            overMenu = new OverMenu(0, 0, width, height, this, win);
        }
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
                case Instructions:
                    instructionMenu.render(g);
                case ModeMenu:
                    modeMenu.render(g);
                    break;
                case SetupMenu:
                    setupMenu.render(g);
                    break;
                case Paused:
                    g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);

                    resources.render(g);
                    organisms.render(g);
                    predators.render(g);

                    if (night) {
                        g.drawImage(Assets.backgroundFilter, 0, 0, width, height, null);
                    }
                    minimap.render(g);
                    buttonBar.render(g);

                    if (selection.isActive()) {
                        selection.render(g);
                    }

                    if (organisms.isOrgPanelActive()) {
                        organisms.getOrgPanel().render(g);
                    } else if (organisms.isMutPanelActive()) {
                        organisms.getMutPanel().render(g);
                    }
                    
                    pauseMenu.render(g);
                    break;
                case Play:
                    g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);

                    resources.render(g);
                    organisms.render(g);
                    predators.render(g);

                    if (night) {
                        g.drawImage(Assets.backgroundFilter, 0, 0, width, height, null);
                    }
                    minimap.render(g);
                    buttonBar.render(g);

                    if (selection.isActive()) {
                        selection.render(g);
                    }

                    if (organisms.isOrgPanelActive()) {
                        organisms.getOrgPanel().render(g);
                    } else if (organisms.isMutPanelActive()) {
                        organisms.getMutPanel().render(g);
                    }

                    break;
                    
                case Multi:
                    if (!server) {
                        background.setNight(night);
                    }
                    g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);

                    resources.render(g);
                    organisms.render(g);
                    otherOrganisms.render(g);
                    predators.render(g);

                    if (night) {
                        g.drawImage(Assets.backgroundFilter, 0, 0, width, height, null);
                    }
                    minimap.render(g);
                    buttonBar.render(g);

                    if (selection.isActive()) {
                        selection.render(g);
                    }

                    if (organisms.isOrgPanelActive()) {
                        organisms.getOrgPanel().render(g);
                    } else if (organisms.isMutPanelActive()) {
                        organisms.getMutPanel().render(g);
                    } else if (organisms.getH() != null && organisms.isHover()) {
                        organisms.getH().render(g);
                    }

                    break;
                case GameOver:
                    g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);

                    resources.render(g);
                    organisms.render(g);
                    predators.render(g);

                    if (night) {
                        g.drawImage(Assets.backgroundFilter, 0, 0, width, height, null);
                    }
                    
                    overMenu.render(g);

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
        try {
            //Open text file
            PrintWriter pw = new PrintWriter(new FileWriter("game.txt"));
            
            //Save camera position
            pw.println(Integer.toString(camera.getX()));
            pw.println(Integer.toString(camera.getY()));
            
            //Save time
            pw.println(Integer.toString(clock.getTicker()));
            
            //Save organisms
            organisms.save(pw);
            
            //Save resources
            resources.save(pw);
            
            //Save predators
            predators.save(pw);
            
            pw.close();
            
            System.out.println("SAVED!");
        } catch(IOException e) {
            System.out.println("BEEP BEEP");
            System.out.println(e.toString());
        }
    }

    /**
     * Load game from text file This method open the designated text file and
     * reads its contents and assigns them to their designated variables
     */
    private void loadGame() {
        try {
            //Open file to load game
            BufferedReader br = new BufferedReader(new FileReader("game.txt"));
            
            //Load camera positions
            camera.setX(Integer.parseInt(br.readLine()));
            camera.setY(Integer.parseInt(br.readLine()));
            
            //Load time
            clock.setTicker(Integer.parseInt(br.readLine()));
            
            organisms.load(br);
            
            resources.load(br);
            
            predators.load(br);

           
        } catch (IOException e) {
            System.out.println("BEEP BEEP");
            System.out.println(e.toString());
        }
    }
    
    private void resetGameMutli() {
        if (server) {
            camera.setX(INITIAL_POINT_HOST - width / 2);
            camera.setY(INITIAL_POINT_HOST - height / 2);
            resources.reset();
        } else {
            camera.setX(INITIAL_POINT_CLIENT - width / 2);
            camera.setY(INITIAL_POINT_CLIENT - height / 2);
        }
        
        clock.setTicker(0);
        organisms.reset();
        otherOrganisms.reset();
    }

    public void resetGame() {
        server = true;
        camera.setX(INITIAL_POINT - width / 2);
        camera.setY(INITIAL_POINT - height / 2);
        
        clock.setTicker(0);
        
        organisms.reset();
        predators.reset();
        resources.reset();
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
    public PredatorManager getPredators() {
        return predators;
    }

    public Graphics getG() {
        return g;
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

    public ButtonBarMenu getButtonBar() {
        return buttonBar;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public OrganismManager getOtherOrganisms() {
        return otherOrganisms;
    }
    
    public boolean isServer() {
        return server;
    }

    public NetworkManager getNetwork() {
        return network;
    }

    public States getState() {
        return state;
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
