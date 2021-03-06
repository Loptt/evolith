package evolith.game;

import evolith.database.JDBC;
import evolith.menus.MainMenu;
import evolith.menus.SetupMenu;
import evolith.menus.ButtonBarMenu;
import evolith.helpers.Clock;
import evolith.helpers.Commons;
import evolith.entities.ResourceManager;
import evolith.entities.OrganismManager;
import evolith.entities.PredatorManager;
import evolith.engine.*;
import evolith.entities.CampfireManager;
import evolith.entities.Resource;
import evolith.helpers.InputReader;
import evolith.helpers.Selection;
import evolith.menus.GameStatisticsMenu;
import evolith.menus.Button;
import evolith.menus.InstructionMenu;
import evolith.menus.MaxIntelligenceButton;
import evolith.menus.ModeMenu;
import evolith.menus.MultiPauseMenu;
import evolith.menus.OverMenu;
import evolith.menus.PauseMenu;
import evolith.menus.StatisticsMenu;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Scanner;

import java.sql.SQLException;

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
    private MusicManager musicManager;          // Manages background music
    private NetworkManager network;             // Manages connections between computers
    private SoundEffectManager sfx;             // Manages sounds in game

    private Background background;              // background of the game engine
    private Camera camera;                      // camera of the game engine

    private OrganismManager organisms;          //organisms in the game
    private OrganismManager otherOrganisms;     //Opponent organisms in multiplayer

    private ResourceManager resources;          //Food and water sources in game

    private PredatorManager predators;          //Main enemies

    public enum States {
        MainMenu, Paused, GameOver, Play, Instructions, SetupMenu, Statistics, Multi, ModeMenu
    } // status of the flow of the game once running
    private States state;

    private MainMenu mainMenu;                  // main menu

    private ButtonBarMenu buttonBar;            // Top bar which indicates what organisms should do
    private GameStatisticsMenu gameStats;
    private SetupMenu setupMenu;                //Menu to choose color and 
    private PauseMenu pauseMenu;                // Menu in pause
    private MultiPauseMenu multiPauseMenu;                // Menu in pause
    private OverMenu overMenu;                  // Game over menu
    private InstructionMenu instructionMenu;    // Instructions menu
    private ModeMenu modeMenu;                  // Menu to choose game mode
    private StatisticsMenu statsMenu;

    private MaxIntelligenceButton maxIntButton; //Button to show the most intelligent organism
    private MaxIntelligenceButton maxIntButtonOpp; //Button to show the most intelligent organism

    private Clock clock;                        // the time of the game
    private InputReader inputReader;            //To read text from keyboard
    private Minimap minimap;                    //Game minimap
    private Selection selection;                //Selection object to select organisms

    private boolean night;                      // Decides if it is night
    private int prevSecDayCycleChange;          //Time to change day cycle
    private int prevWeatherChange;              //Time to change weather

    private Weather weather;                    //Weather manager

    private CampfireManager campfires;
    
    private int gameID;                         //Id of the game
    private JDBC mysql;                         //MySql connection object

    private boolean win;                        // To decide if the player has won
    private boolean server;                     // Decides if it is a server or not
    private boolean paused;                     // Decides if the game is paused

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     * @throws java.sql.SQLException
     */
    public Game(String title, int width, int height) throws SQLException {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        camera = new Camera(INITIAL_POINT - width / 2, INITIAL_POINT - height / 2, width, height, this);
        mainMenu = new MainMenu(0, 0, width, height, this);
        inputKeyboard = new InputKeyboard();
        minimap = new Minimap(MINIMAP_X, MINIMAP_Y, MINIMAP_WIDTH, MINIMAP_HEIGHT, this);
        campfires = new CampfireManager(this);

        state = States.MainMenu;
        selection = new Selection(this);

        night = false;
        server = true;
        prevSecDayCycleChange = 0;
        win = false;

        this.mysql = new JDBC();
        this.gameID = mysql.getLastGameID() + 1;

        prevWeatherChange = 0;

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

        mysql.updateBackup();

        clock = new Clock(0, 0, 100, 100);
        display = new Display(title, width, height);
        Assets.init();
        sfx = new SoundEffectManager();

        background = new Background(5000, 5000, width, height);
        buttonBar = new ButtonBarMenu(10, 10, 505, 99, this);
        gameStats = new GameStatisticsMenu(0,0,0,0,this);
        setupMenu = new SetupMenu(0, 0, width, height, this,mysql);
        pauseMenu = new PauseMenu(width / 2 - 250 / 2, height / 2 - 300 / 2, 250, 300, this);
        multiPauseMenu = new MultiPauseMenu(width / 2 - 250 / 2, height / 2 - 200 / 2, 250, 200, this);

        statsMenu = new StatisticsMenu(0,0,width,height,this,false,mysql);

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
        
        mysql.insertGame(gameID, clock.getSeconds());
        mysql.insertSpecies(gameID);
        organisms.setSpeciesID(mysql.getSpeciesID(gameID));
        mysql.insertOrganism(organisms.getSpeciesID() , 1 ,organisms.getOrganism(0).getGeneration(),organisms.getOrganism(0).getSpeed(),organisms.getOrganism(0).getStealth() , organisms.getOrganism(0).getStrength(),organisms.getOrganism(0).getMaxHealth());
        mysql.getAverage();

        weather = new Weather(width, height, background, this);

        paused = false;

        maxIntButton = new MaxIntelligenceButton(825, 210, 150, 70, Assets.maxIntButtonOn, Assets.maxIntButtonOff, organisms.getOrganism(0));
        maxIntButtonOpp = new MaxIntelligenceButton(825, 290, 150, 70, Assets.maxIntButtonOpp, Assets.maxIntButtonOpp, organisms.getOrganism(0));
        
        maxIntButton.setyOff(36);
        maxIntButtonOpp.setyOff(45);
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
                if(clock.getTicker() % 600 == 0)
                    mysql.updateTimeGame(gameID, clock.getSeconds());
                break;
            case Multi:
                multiTick();
                break;
            case GameOver:
                overTick();
            case Statistics:
                statisticsTick();
        }
        

    }

    /**
     * Tick the main menu
     */
    private void mainMenuTick() {
        musicManager.stop();    //Stop game music
        mainMenu.setActive(true);
        mainMenu.tick();

        sfx.tick();


        //If play is clicked, go to mode menu

        if (mainMenu.isClickPlay()) {
            mainMenu.setActive(false);
            state = States.ModeMenu;
            mainMenu.setClickPlay(false);
            modeMenu = new ModeMenu(0, 0, width, height, this);
        }

        //If instructions are clicked, go to instructions menu
        if (mainMenu.isClickIns()) {
            instructionMenu = new InstructionMenu(0, 0, width, height, this);
            mainMenu.setActive(false);
            state = States.Instructions;
            mainMenu.setClickIns(false);
        }
        
    }

    /**
     * Tick the mode menu
     */
    private void modeTick() {
        modeMenu.tick();
        inputKeyboard.tick();
        sfx.tick();
        

        //If single player is chosen, go to setup menu
        if (modeMenu.isSingle()) {
            resetGame();
            state = States.SetupMenu;
            modeMenu.setSingle(false);
            server = true;
        }

        //If Load is chosen, load last game and switch to play state
        if (modeMenu.isLoad()) {
            loadGame();
            state = States.Play;
            modeMenu.setLoad(false);
            musicManager.play();
        }

        //If host is chosen, go to multiplayer mode
        if (modeMenu.isHost()) {
            state = States.Multi;
            resetGameMutli();
            modeMenu.setHost(false);
        }

        //If join is chosen, go to multiplayer mode
        if (modeMenu.isJoin()) {
            state = States.Multi;
            resetGameMutli();
            modeMenu.setJoin(false);
        }

        //If back is chosen, go to main menu mode
        if (modeMenu.isToMainMenu()) {
            state = States.MainMenu;
            modeMenu.setToMainMenu(false);
        }
    }

    /**
     * Tick instructions menu
     */
    private void instructionsTick() {
        instructionMenu.tick();
        sfx.tick();
        

        //If instructions screens are over, return to main menu
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
        sfx.tick();

        //If play is clicked, go to single player play state
        if (setupMenu.isClickPlay()) {
            initSinglePlayer();
            setupMenu.setClickPlay(false);
        }
    }

    /**
     * Tick the main game in single player
     */
    private void playTick() {

        //If paused, tick pause and return
        if (paused) {
            pausedTick();
            return;
        }

        //Tick all involved objects
        clock.tick();
        campfires.tick();

        organisms.tick();
        resources.tick();
        predators.tick();
        buttonBar.tick();
        gameStats.tick();
        selection.tick();
        weather.tick();
        sfx.tick();
        keyManager.tick();
        musicManager.tick();

        //Change weather when the duration has passed
        if (clock.getSeconds() >= prevWeatherChange + WEATHER_CYCLE_DURATION_SECONDS) {
            weather.changeWeather();
            prevWeatherChange = clock.getSeconds();
            changeEnvironmentToWeather();
        }

        //Only tick camera when the organism panel is not active
        if (!organisms.getOrgPanel().isInputActive()) {
            camera.tick();
        }

        manageMouse();
        manageKeyboard();

        //Change the day cycle 
        if (clock.getSeconds() >= prevSecDayCycleChange + DAY_CYCLE_DURATION_SECONDS) {
            night = !night;
            background.setNight(night);
            prevSecDayCycleChange = clock.getSeconds();
        }

        maxIntButton.setOrg(organisms.getMostIntelligent());

        //Update resources
        resources.deleteResources();
        resources.respawnResources();

        organisms.checkKill();
        checkGameOver();
    }

    /**
     * Tick in multiplayer mode
     */
    private void multiTick() {

        //If paused, tick pause but not return
        if (paused) {
            pausedMultiTick();
        }

        //Tick involved objects
        clock.tick();
        network.tick();
        organisms.tick();
        otherOrganisms.tick();
        resources.tick();
        buttonBar.tick();
        inputKeyboard.tick();
        selection.tick();
        weather.tick();
        sfx.tick();

        //Check if the other player has disconnected
        if (network.isTimeOut()) {
            System.out.println("TIMEOUT");
            state = States.GameOver;
            win = true;
            overMenu = new OverMenu(0, 0, width, height, this, win, "Other player has disconnected");
            network.endConnection();
        }

        organisms.checkOtherVisible();

        //If server, change weather
        if (server) {
            if (clock.getSeconds() >= prevWeatherChange + WEATHER_CYCLE_DURATION_SECONDS) {
                weather.changeWeather();
                prevWeatherChange = clock.getSeconds();
            }
        }

        //Send the updated data of objects through the network
        network.sendDataPlants(resources);
        network.sendDataWaters(resources);
        network.sendData(organisms);

        //Check if the other player went extinct. If so, then game won
        if (network.isOtherExtinct()) {
            state = States.GameOver;
            win = true;
            overMenu = new OverMenu(0, 0, width, height, this, win, "You have extinguished the opponent");
            network.endConnection();
        }

        //Check if the other player has won. If so, then game lost
        if (network.isOtherWon()) {
            state = States.GameOver;
            win = false;
            overMenu = new OverMenu(0, 0, width, height, this, win);
            overMenu = new OverMenu(0, 0, width, height, this, win, "The opponent has reached max intelligence");
            network.endConnection();
        }

        keyManager.tick();
        musicManager.tick();

        //Only tick camera when org panel is not active
        if (!organisms.getOrgPanel().isInputActive()) {
            camera.tick();
        }

        manageMouse();
        manageKeyboard();

        //If server, change the day cycle
        if (server) {
            if (clock.getSeconds() >= prevSecDayCycleChange + DAY_CYCLE_DURATION_SECONDS) {
                night = !night;
                background.setNight(night);
                prevSecDayCycleChange = clock.getSeconds();
            }
        }

        organisms.checkKill();
        otherOrganisms.checkKill();

        //If server, then add and remove resources
        if (server) {
            resources.deleteResources();
            resources.respawnResources();
        }

        maxIntButton.setOrg(organisms.getMostIntelligent());
        maxIntButtonOpp.setOrg(otherOrganisms.getMostIntelligent());

        checkGameOver();
    }

    /**
     * Tick the pause menu
     */
    private void pausedTick() {
        pauseMenu.tick();
        keyManager.tick();
        musicManager.tick();
        
        //If p is pressed, go back to play
        if (keyManager.p) {
            paused = !paused;
            inputKeyboard.p = false;
            inputKeyboard.prevp = false;
        }

        //If esc is pressed, go back to play
        if (keyManager.esc) {
            paused = !paused;
        }

        if (!pauseMenu.isMainMenuDisplayed()) {
            paused = !paused;
        }

        //Is save button is pressed, save current game state
        if (pauseMenu.isClickSave()) {
            saveGame();
            pauseMenu.setClickSave(false);
        }

        //If load button is pressed, load current game state
        if (pauseMenu.isClickLoad()) {
            musicManager.stop();
            loadGame();
            pauseMenu.setClickLoad(false);
            musicManager.play();
            paused = !paused;
        }

        //If exit button is pressed, go back to main menu
        if (pauseMenu.isClickExit()) {
            pauseMenu.setClickExit(false);
            state = States.MainMenu;
            resetGame();
            musicManager.stop();

            if (network != null) {
                network.endConnection();
            }
        }
    }
    
        /**
     * Tick the pause menu
     */
    private void pausedMultiTick() {
        multiPauseMenu.tick();
        keyManager.tick();
        musicManager.tick();
        

        //If p is pressed, go back to play
        if (keyManager.p) {
            paused = !paused;
            inputKeyboard.p = false;
            inputKeyboard.prevp = false;
        }

        //If esc is pressed, go back to play
        if (keyManager.esc) {
            paused = !paused;
        }

        if (!multiPauseMenu.isMainMenuDisplayed()) {
            paused = !paused;
        }

        //If exit button is pressed, go back to main menu
        if (multiPauseMenu.isClickExit()) {
            pauseMenu.setClickExit(false);
            state = States.MainMenu;
            resetGame();
            musicManager.stop();

            if (network != null) {
                network.endConnection();
            }
        }
    }

    /**
     * Tick the game over menu
     */
    private void overTick() {
        overMenu.tick();

        //If main menu button is pressed, go back to main menu
        if (overMenu.isMainMenu()) {
            overMenu.setMainMenu(false);
            state = States.MainMenu;
            resetGame();
            musicManager.stop();
        }

        //If statistics button is pressed, go to stats screen
        if (overMenu.isStats()) {
            overMenu.setStats(false);
            state = States.Statistics;
        }
    }
    
    /**
     * Tick statistics menu 
    */
    private void statisticsTick(){
        
        statsMenu.tick();
        
        if(statsMenu.isMainMenu())
        {
            statsMenu.setMainMenu(false);
            state = States.MainMenu;
            musicManager.stop();
            resetGame();
            
            if (network != null) {
                network.endConnection();
            }
        }
        
    }

    /**
     * Initialize single player game
     */
    private void initSinglePlayer() {
        organisms.setSpeciesName(setupMenu.getName());
        setupMenu.setName("");
        organisms.setSkin(setupMenu.getOption());
        state = States.Play;
        musicManager.play();
        resources.init();
        night = false;
    }

    /**
     * Initialize the multiplayer game for the server
     */
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

    /**
     * initialize the multiplayer game for the client
     *
     * @param address the server address
     */
    public void multiInitClient(String address) {
        server = false;
        otherOrganisms = new OrganismManager(this, true);
        network = new NetworkManager(false, otherOrganisms, resources, predators);
        network.initClient(address, 5000);
        organisms.setSkin(2);   //Client is always skin 2

        otherOrganisms.setSkin(0);  //Server is always skin 0

        Thread myThread = new Thread(network);

        //Initialize receiving information from connection in a new thread
        myThread.start();
    }

    /**
     * Changes resources and predators according to weather
     */
    private void changeEnvironmentToWeather() {
        if (weather.getState() == weather.getPrevState()) {
            return;
        }

        switch (weather.getState()) {
            case Clear:
                resources.resetResources();
                break;
            case Dry:
                resources.reduceWaters(WATERS_AMOUNT/2);
                break;
            case Rain:
                resources.increaseResources(WATERS_AMOUNT*2);
                break;
            case Storm:
                //decrease number of small enemies
                break;
            case Hail:
                //decrease organism and predator movement speed
                break;
            case Snow:
                resources.reducePlants(PLANTS_AMOUNT/2);
                resources.reduceWaters(WATERS_AMOUNT/2);
                break;
        }
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
            //Check hover on in game buttons
            if (maxIntButton.hasMouse(mouseManager.getX(), mouseManager.getY())) {
                maxIntButton.setActive(true);
            } else {
                maxIntButton.setActive(false);
            }
        }
    }

    /**
     * Handle the keyboard input while in game
     */
    private void manageKeyboard() {
        //If paused check for pause-deactivating keys
        if (paused) {
            if (keyManager.esc) {
                pauseMenu.setMainMenuDisplayed(!paused);
                multiPauseMenu.setMainMenuDisplayed(!paused);
                paused = !paused;
            }

            if (keyManager.p && !organisms.getOrgPanel().isInputActive()) {
                pauseMenu.setMainMenuDisplayed(!paused);
                multiPauseMenu.setMainMenuDisplayed(!paused);
                paused = !paused;
                keyManager.p = false;
                keyManager.prevp = false;
            }

            return;
        }

        //Else, check all other keys
        if (!organisms.getOrgPanel().isActive()) {
            if (keyManager.esc) {
                pauseMenu.setMainMenuDisplayed(true);
                multiPauseMenu.setMainMenuDisplayed(true);
                paused = !paused;
            }
        } else {
            if (keyManager.esc) {
                organisms.getOrgPanel().setActive(false);
            }
        }

        if (keyManager.space) {
            organisms.selectInRect(new Rectangle(camera.getX(), camera.getY(), width, height));
        }

        if (keyManager.p && !organisms.getOrgPanel().isInputActive()) {
            pauseMenu.setMainMenuDisplayed(true);
            multiPauseMenu.setMainMenuDisplayed(true);
            paused = !paused;
        }

        //Turn on Water searching
        if (keyManager.num1) {
            buttonBar.setWaterActive(!buttonBar.isWaterActive());
            organisms.setSelectedSearchFood(buttonBar.isFoodActive());
            organisms.setSelectedSearchWater(buttonBar.isWaterActive());
            organisms.setSelectedAggressiveness(buttonBar.isFightActive());
            organisms.emptySelectedTargets();
        }

        //Turn on Food searching
        if (keyManager.num2) {
            buttonBar.setFoodActive(!buttonBar.isFoodActive());
            organisms.setSelectedSearchFood(buttonBar.isFoodActive());
            organisms.setSelectedSearchWater(buttonBar.isWaterActive());
            organisms.setSelectedAggressiveness(buttonBar.isFightActive());
            organisms.emptySelectedTargets();
        }

        //Turn on fight
        if (keyManager.num3) {
            buttonBar.setFightActive(!buttonBar.isFightActive());
            organisms.setSelectedSearchFood(buttonBar.isFoodActive());
            organisms.setSelectedSearchWater(buttonBar.isWaterActive());
            organisms.setSelectedAggressiveness(buttonBar.isFightActive());
            organisms.emptySelectedTargets();
        }
    }

    /**
     * Handle what a mouse left click should do
     */
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

        //First in hierarchy are the panels
        if (organisms.isOrgPanelActive() || organisms.isMutPanelActive() || organisms.isStatsPanelActive()) {
            //Let the panels handle mouse activity
            //Next check the selection
        } else if (selection.isActive()) {
            checkOrganismsInSelection();
            //Next if an organism is clicked
        } else if (organisms.checkPanel()) {
            mouseManager.setLeft(false);
            //Next check if the buttonbar is clicked
        } else if (buttonBar.hasMouse(mouseX, mouseY)) {
            //Process the mouse in the button bar
            buttonBar.applyMouse(mouseX, mouseY);
            
            organisms.setSelectedSearchFood(buttonBar.isFoodActive());
            organisms.setSelectedSearchWater(buttonBar.isWaterActive());
            organisms.setSelectedAggressiveness(buttonBar.isFightActive());
            organisms.emptySelectedTargets();
            mouseManager.setLeft(false);
            //Next, check the stats
        } else if(gameStats.hasMouse(mouseX, mouseY)){
            gameStats.applyMouse(mouseX, mouseY);
            mouseManager.setLeft(false);
          //Next, check the minimap
        } else if (minimap.hasMouse(mouseX, mouseY)) {
            minimap.applyMouse(mouseX, mouseY, camera);
            mouseManager.setLeft(false);
            //Next, check the max intelligence button 
        } else if (maxIntButton.hasMouse(mouseX, mouseY)) {
            camera.setX(maxIntButton.getOrg().getX() - width / 2);
            camera.setY(maxIntButton.getOrg().getY() - height / 2);
            organisms.clearSelection();
            maxIntButton.getOrg().setSelected(true);
            //Lastly, activate selection
        } else {
            selection.activate(camera.getAbsX(mouseX), camera.getAbsY(mouseY));
        }
    }

    /**
     * Handle what a mouse right click should do
     */
    public void manageRightClick() {
        int mouseX = mouseManager.getX();
        int mouseY = mouseManager.getY();

        //first check the buttonbar
        if (buttonBar.hasMouse(mouseX, mouseY)) {
            mouseManager.setRight(false);
            //Second in hierarchy is the minimap
        } else if(gameStats.hasMouse(mouseX, mouseY)){
            mouseManager.setRight(false);
        } else if (minimap.hasMouse(mouseX, mouseY)) {
            mouseManager.setRight(false);
            //Lastly, move the   
        } else {
            selection.deactivate();
            Resource clickedResource = resources.containsResource(camera.getAbsX(mouseX), camera.getAbsY(mouseY));

            //if clicked is not null, a resource has been clicked
            if (clickedResource != null) {
                organisms.emptySelectedTargets();
                //Set the resource to the selected organisms
                organisms.setSelectedResource(clickedResource);
                if (clickedResource.getType() == Resource.ResourceType.Plant) {
                    sfx.playPlant();
                    organisms.setSelectedSearchFood(true);
                    organisms.setSelectedSearchWater(false);
                } else {
                    sfx.playWater();
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

    /**
     * Check if the game is over
     */
    public void checkGameOver() {
        //If the player has no organisms left, end game in lost state
        if (organisms.getAmount() <= 0) {
            if (network != null) {
                network.sendDataExtinct();
            }
            state = States.GameOver;
            win = false;
            System.out.println("OVER");
            overMenu = new OverMenu(0, 0, width, height, this, win);
            statsMenu.setWin(win);
        }

        /**
         * If the organisms reached max intelligence, end game in won state
         */
        if (organisms.isMaxIntelligence()) {
            if (network != null) {
                network.sendDataWin();
            }
            state = States.GameOver;
            win = true;
            overMenu = new OverMenu(0, 0, width, height, this, win);
            statsMenu.setWin(win);
        }
    }

    /**
     * Check if organisms are in the selection rectangle
     */
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

            //Each state has its separete function for rendering when necessary
            switch (state) {
                case MainMenu:
                    mainMenu.render(g);
                    break;
                case Instructions:
                    instructionMenu.render(g);
                    break;
                case ModeMenu:
                    modeMenu.render(g);
                    break;
                case SetupMenu:
                    setupMenu.render(g);
                    break;
                case Play:
                    playRender(g);
                    break;
                case Multi:
                    multiRender(g);
                    break;
                case GameOver:
                    overRender(g);
                    break;
                case Statistics:
                    statsMenu.render(g);
                    break;
            }
          
            bs.show();
            g.dispose();
        }
    }

    /**
     * Render the play state
     *
     * @param g Graphics
     */
    public void playRender(Graphics g) {
        g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);

        resources.render(g);
        campfires.render(g);

        organisms.render(g);
        predators.render(g);


        if (night) {
            g.drawImage(Assets.backgroundFilter, 0, 0, width, height, null);
        }

        weather.render(g);
        minimap.render(g);
        buttonBar.render(g);
        gameStats.render(g);

        if (selection.isActive()) {
            selection.render(g);
        }

        if (organisms.isOrgPanelActive()) {
            organisms.getOrgPanel().render(g);
        } else if (organisms.isMutPanelActive()) {
            organisms.getMutPanel().render(g);
        } else if (organisms.getStatsPanel().isActive()) {
            organisms.getStatsPanel().render(g);
        } else if(organisms.getH() != null && organisms.isHover()) {
            organisms.getH().render(g);
        }

        if (paused) {
            pauseMenu.render(g);
        }

        maxIntButton.render(g);
    }

    /**
     * Render the multiplayer state
     *
     * @param g Graphics
     */
    public void multiRender(Graphics g) {
        if (!server) {
            background.setNight(night);
        }
        g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);

        resources.render(g);
        organisms.render(g);
        otherOrganisms.render(g);
        weather.render(g);

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

        if (paused) {
            multiPauseMenu.render(g);
        }
        
        maxIntButton.render(g);
        maxIntButtonOpp.render(g);
    }

    /**
     * Render the game over state
     *
     * @param g Graphics
     */
    public void overRender(Graphics g) {
        g.drawImage(background.getBackground(camera.getX(), camera.getY()), 0, 0, width, height, null);

        resources.render(g);
        organisms.render(g);
        predators.render(g);

        if (night) {
            g.drawImage(Assets.backgroundFilter, 0, 0, width, height, null);
        }

        overMenu.render(g);
    }

    /**
     * Saves current game status into a file. Each important variable to define
     * the current status of the game is stored in the file in a specific order
     */
    private void saveGame() {
        try {
            //Open text file
            PrintWriter pw = new PrintWriter(new FileWriter("game.txt"));

            //Save camera position
            pw.println(Integer.toString(camera.getX()));
            pw.println(Integer.toString(camera.getY()));

            //Save time
            pw.println(Long.toString(clock.getTicker()));
            pw.println(Integer.toString(night ? 1 : 0));
            pw.println(Integer.toString(prevSecDayCycleChange));
            pw.println(Integer.toString(prevWeatherChange));

            //Save weather
            weather.save(pw);

            //Save organisms
            organisms.save(pw);

            //Save resources
            resources.save(pw);

            //Save predators
            predators.save(pw);

            pw.close();

            System.out.println("SAVED!");
        } catch (IOException e) {
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
            clock.setTicker(Long.parseLong(br.readLine()));
            night = Integer.parseInt(br.readLine()) == 1;
            background.setNight(night);

            prevSecDayCycleChange = Integer.parseInt(br.readLine());
            prevWeatherChange = Integer.parseInt(br.readLine());

            //Load weather
            weather.load(br);

            //Load organisms
            organisms.load(br);

            //Load resources
            resources.load(br);

            //Load predators
            predators.load(br);

        } catch (IOException e) {
            System.out.println("BEEP BEEP");
            System.out.println(e.toString());
        }
    }

    /**
     * Reset the game in multiplayer
     */
    private void resetGameMutli() {
        if (server) {
            camera.setX(INITIAL_POINT_HOST - width / 2);
            camera.setY(INITIAL_POINT_HOST - height / 2);
            resources.reset(true);
        } else {
            camera.setX(INITIAL_POINT_CLIENT - width / 2);
            camera.setY(INITIAL_POINT_CLIENT - height / 2);
            resources.reset(false);
        }

        clock.setTicker(0);

        weather.setWeather(Weather.State.Clear);
        prevSecDayCycleChange = 0;
        prevWeatherChange = 0;

        night = false;

        organisms.reset();
        otherOrganisms.reset();
    }

    /**
     * Reset the game in single player. Set every important variable to its
     * initial values
     */
    public void resetGame() {
        server = true;
        camera.setX(INITIAL_POINT - width / 2);
        camera.setY(INITIAL_POINT - height / 2);

        clock.setTicker(0);

        weather.setWeather(Weather.State.Clear);
        prevSecDayCycleChange = 0;
        prevWeatherChange = 0;

        night = false;

        organisms.reset();
        predators.reset();  
        resources.reset(true);
        
        try {
            gameID = mysql.getLastGameID() + 1;
            mysql.insertGame(gameID, clock.getSeconds());
            mysql.insertSpecies(gameID);
            organisms.setSpeciesID(mysql.getSpeciesID(gameID));
            mysql.insertOrganism(organisms.getSpeciesID() , 1 ,organisms.getOrganism(0).getGeneration(),organisms.getOrganism(0).getSpeed(),organisms.getOrganism(0).getStealth() , organisms.getOrganism(0).getStrength(),organisms.getOrganism(0).getMaxHealth());
            
        } catch (SQLException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

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

    /**
     * to get graphics
     * @return g
     */
    public Graphics getG() {
        return g;
    }

    /**
     * to get the organism manager
     * @return organisms
     */
    public OrganismManager getOrganisms() {
        return organisms;
    }

    /**
     * to get the resource manager
     * @return resources;
     */
    public ResourceManager getResources() {
        return resources;
    }

    /**
     * to get the selection
     * @return selection
     */
    public Selection getSelection() {
        return selection;
    }

    /**
     * to set the selection
     * @param selection selection object
     */
    public void setSelection(Selection selection) {
        this.selection = selection;
    }

    /**
     * to get the button bar menu
     * @return buttonBar
     */
    public ButtonBarMenu getButtonBar() {
        return buttonBar;
    }

    /**
     * to check if it is night
     * @return night
     */
    public boolean isNight() {
        return night;
    }

    /**
     *  to set the night boolean
     * @param night night boolean
     */
    public void setNight(boolean night) {
        this.night = night;
    }

    /**
     * to get the other organism manager
     * @return otherOrganisms
     */
    public OrganismManager getOtherOrganisms() {
        return otherOrganisms;
    }

    /**
     * to get if it is server
     * @return server
     */
    public boolean isServer() {
        return server;
    }

    /**
     * to get the network manager
     * @return network
     */
    public NetworkManager getNetwork() {
        return network;
    }

    /**
     * to get the game state
     * @return state
     */
    public States getState() {
        return state;
    }

    /**
     * to get the sound effect manager
     * @return sfx
     */
    public SoundEffectManager getSfx() {
        return sfx;
    }

    /**
     * to get the weather object
     * @return weather
     */
    public Weather getWeather() {
        return weather;
    }

    public Clock getClock() {
        return clock;
    }

    public CampfireManager getCampfires() {
        return campfires;
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

    /**
     * to get game id
     * @return gameID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * to set game ID
     * @param gameID new id
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * to get mysql connection
     * @return mysql
     */
    public JDBC getMysql() {
        return mysql;
    }

    /**
     * to set mysql connection
     * @param mysql new mysql
     */
    public void setMysql(JDBC mysql) {
        this.mysql = mysql;
    }
    
    /**
     * to get game statistics menu
     * @return gameStats
     */
    public GameStatisticsMenu getGameStats() {
        return gameStats;
    }
}
