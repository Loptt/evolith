package evolith.menus;

import evolith.engine.Assets;
import evolith.game.Game;
import evolith.helpers.Commons;
import evolith.helpers.InputReader;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class ModeMenu extends Menu {

    private ArrayList<BufferedImage> backgrounds;
    private int currentPage;

    private boolean single;             // to check if the single mode is active
    private boolean load;               // to check if the load mode is active
    private boolean host;               // to check if the host mode is active
    private boolean join;               // to check if the join mode is active
    private boolean toMainMenu;         // to check if the main menu mode is active

    private InputReader inputReader;    //handle the input of the keyboard

    private String fontPath;            //relative path to the font
    private String address;             //address of the connection
    private Font fontEvolve;            //main font 
    private InputStream is;             //handles the input 
    private int timeOpen;               //handles the time of the connection 
    private boolean tickToWrite;        //handle the ticking writing

    public ModeMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        currentPage = 0;

        backgrounds = new ArrayList<>(); //array with the background modes
        //add the background images of the modes
        backgrounds.add(Assets.modes.get(0));
        backgrounds.add(Assets.modes.get(1));
        backgrounds.add(Assets.modes.get(2));
        backgrounds.add(Assets.modes.get(3));

        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 200, 400, 80, Assets.singlePlayerOn, Assets.singlePlayerOff)); //Single player
        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 330, 400, 80, Assets.loadModeOn, Assets.loadModeOff)); //Load player
        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 460, 400, 80, Assets.multiplayerOn, Assets.multiplayerOff)); //Multiplayer
        buttons.add(new Button(game.getWidth() / 2 - 250 / 2, 575, 250, 60, Assets.backOn, Assets.backOff)); //Back

        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 300, 400, 80, Assets.hostOn, Assets.hostOff)); //Host
        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 450, 400, 80, Assets.joinOn, Assets.joinOff)); //Join
        //initalize all states in false
        single = false;
        load = false;
        host = false;
        join = false;
        toMainMenu = false;
        //127.0.0.1 address to connect
        this.address = "localhost";
        //initializes the input reader
        inputReader = new InputReader(game);

        //sets the path of the font
        fontPath = "/Fonts/MADE-Evolve-Sans-Regular.ttf";
        this.is = OrganismPanel.class.getResourceAsStream(fontPath);
        try {
            fontEvolve = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //time open of the connection
        this.timeOpen = 0;
        this.tickToWrite = false;
    }

    /**
     * Ticks the button
     */
    @Override
    public void tick() {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                //if the mouse is over the button
                buttons.get(i).setActive(true);
                //if mouse is clicked then set pressed
                if (game.getMouseManager().isLeft()) {
                    System.out.println("PRESSED: " + i);
                    buttons.get(i).setPressed(true);
                }
            } else {
                buttons.get(i).setActive(false);
            }
        }
        //turns off the mouse
        game.getMouseManager().setLeft(false);
        //the address of the connection to multiplayer is read
        address = inputReader.getSpeciesName();
        //handles the mode selected
        switch (currentPage) {
            //Single player
            case 0:
                if (buttons.get(0).isPressed()) {
                    single = true;
                    load = false;
                    host = false;
                    join = false;
                    buttons.get(0).setPressed(false);
                }
                if (buttons.get(1).isPressed()) {
                    single = false;
                    load = true;
                    host = false;
                    join = false;
                    buttons.get(1).setPressed(false);

                }
                if (buttons.get(2).isPressed()) {
                    buttons.get(2).setPressed(false);
                    currentPage = 1;
                }
                if (buttons.get(3).isPressed()) {
                    buttons.get(3).setPressed(false);
                    toMainMenu = true;
                }
                break;
            //Multiplayer host or client
            case 1:
                //If the host is selected initilize the server locally
                if (buttons.get(4).isPressed()) {
                    System.out.println("HOST");
                    buttons.get(4).setPressed(false);
                    game.mutliInitServer();
                    currentPage = 2;
                }
                //if its the client change the screen
                if (buttons.get(5).isPressed()) {
                    System.out.println("CLIENT");
                    buttons.get(5).setPressed(false);
                    currentPage = 3;
                }
                if (buttons.get(3).isPressed()) {
                    buttons.get(3).setPressed(false);
                    currentPage = 0;
                }
                break;
            //Multiplayer connection
            case 2:
                //cancel the multiplayer connection
                if (buttons.get(3).isPressed()) {
                    buttons.get(3).setPressed(false);
                    currentPage = 1;
                    if (game.getNetwork() != null) {
                        game.getNetwork().endConnection();
                    }
                }
                // if the client is connected then turn on the host mode and await connection
                if (game.getNetwork().isClientReady()) {
                    single = false;
                    load = false;
                    host = true;
                    join = false;
                    game.getNetwork().sendReady(false);
                }
                break;
            //Multiplayer host waiting
            case 3:
                //increases the time that the connection is open
                timeOpen++;

                //limits the input of the keyboard
                if (game.getG().getFontMetrics().stringWidth(inputReader.getSpeciesName()) > 383) {
                    inputReader.setOnlyDelete(true);
                } else {
                    inputReader.setOnlyDelete(false);
                }
                //reads the input and updates the address of the connection
                inputReader.readInput();
                address = inputReader.getSpeciesName();
                //cancel the multiplayer connection
                if (buttons.get(3).isPressed()) {
                    buttons.get(3).setPressed(false);
                    currentPage = 1;
                    if (game.getNetwork() != null) {
                        game.getNetwork().endConnection();
                    }
                }
                //initialize the client mode 
                if (buttons.get(5).isPressed()) {
                    buttons.get(5).setPressed(false);
                    game.multiInitClient(address);
                }
                //join the game mode is on
                if (game.getNetwork() != null) {
                    if (game.getNetwork().isServerReady()) {
                        single = false;
                        load = false;
                        host = false;
                        join = true;
                    } else {
                        System.out.println("NOT READY");
                    }

                    game.getNetwork().sendReady(true);
                } else {
                }
        }
        //to tick the buttons
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setPressed(false);
        }
    }

    /**
     * To render the images
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(backgrounds.get(currentPage), x, y, width, height, null);

        switch (currentPage) {
            //single player
            case 0:
                buttons.get(0).render(g);
                buttons.get(1).render(g);
                buttons.get(2).render(g);
                buttons.get(3).render(g);
                break;
            //Multiplayer host or client
            case 1:
                buttons.get(3).render(g);
                buttons.get(4).render(g);
                buttons.get(5).render(g);
                break;
            //Multiplayer connection
            case 2:
                buttons.get(3).render(g);
                break;
            //Multiplayer host waiting
            case 3:
                buttons.get(3).render(g);
                buttons.get(5).render(g);
                g.setColor(Commons.FONT_COLOR);
                g.setFont(fontEvolve.deriveFont(40f));
                //timeopen of the connection is counted and tick to write
                if (timeOpen % 60 == 0) {
                    timeOpen = 0;

                    tickToWrite = !tickToWrite;
                }

                g.drawString(address, x + 407, y + 567);
                int width = g.getFontMetrics().stringWidth(address);

                if (tickToWrite && !inputReader.isOnlyDelete()) {
                    g.drawString("l", x + 407 + width, y + 567);
                }
                break;
        }
    }

    /**
     * To check if the mode is main menu
     *
     * @return toMainMenu
     */
    public boolean isToMainMenu() {
        return toMainMenu;
    }

    /**
     * To check if the mode is single player
     *
     * @return single
     */
    public boolean isSingle() {
        return single;
    }

    /**
     * To check if the mode is load
     *
     * @return load
     */
    public boolean isLoad() {
        return load;
    }

    /**
     * To check if the user is the host
     *
     * @return host
     */
    public boolean isHost() {
        return host;
    }

    /**
     * To check if the user is joining a game
     *
     * @return join
     */
    public boolean isJoin() {
        return join;
    }

    /**
     * To set the status of the main menu
     *
     * @param toMainMenu
     */
    public void setToMainMenu(boolean toMainMenu) {
        this.toMainMenu = toMainMenu;
    }

    /**
     * To set the status of the single mode
     *
     * @param single
     */
    public void setSingle(boolean single) {
        this.single = single;
    }

    /**
     * To set the load status
     *
     * @param load
     */
    public void setLoad(boolean load) {
        this.load = load;
    }

    /**
     * To set if the user is host or client
     *
     * @param host
     */
    public void setHost(boolean host) {
        this.host = host;
    }

    /**
     * To set if the player is joining a game
     *
     * @param join
     */
    public void setJoin(boolean join) {
        this.join = join;
    }
}
