/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Carlos Estrada
 */
public class ModeMenu extends Menu {
    
    private ArrayList<BufferedImage> backgrounds;
    private int currentPage;
    
    private boolean single;
    private boolean load;
    private boolean host;
    private boolean join;
    private boolean toMainMenu;
    
    private InputReader inputReader;
    
    private String fontPath;
    private String address;
    private Font fontEvolve;
    private InputStream is;
    private int timeOpen;
    private boolean tickToWrite;
    
    public ModeMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        currentPage = 0;
        
        backgrounds = new ArrayList<>();
        
        backgrounds.add(Assets.modes.get(0));
        backgrounds.add(Assets.modes.get(1));
        backgrounds.add(Assets.modes.get(2));
        backgrounds.add(Assets.modes.get(3));
        
        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 300, 400, 80, Assets.singlePlayerOn, Assets.singlePlayerOff)); //Single player
        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 400, 400, 80, Assets.loadModeOn, Assets.loadModeOff)); //Load player
        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 500, 400, 80, Assets.multiplayerOn, Assets.multiplayerOff)); //Multiplayer
        buttons.add(new Button(game.getWidth() / 2 - 250 / 2, 600, 250, 60, Assets.backOn, Assets.backOff)); //Back
        
        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 350, 400, 60, Assets.hostOn, Assets.hostOff)); //Host
        buttons.add(new Button(game.getWidth() / 2 - 400 / 2, 450, 400, 60, Assets.joinOn, Assets.joinOff)); //Join
        
        single = false;
        load = false;
        host = false;
        join = false;
        toMainMenu = false;
        
        this.address = "localhost";
        inputReader = new InputReader(game);
        
        
        fontPath = "/Fonts/MADE-Evolve-Sans-Regular.ttf";
        this.is = OrganismPanel.class.getResourceAsStream(fontPath);
        try {
            fontEvolve = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.timeOpen = 0;
        this.tickToWrite = false;
    }

    @Override
    public void tick() {
        for(int i=0; i<buttons.size(); i++){
            if(buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())){
                //if the mouse is over the button
                buttons.get(i).setActive(true);

                if(game.getMouseManager().isLeft()){
                    buttons.get(i).setPressed(true);
                    game.getMouseManager().setLeft(false);
                }
            }
            else {
                buttons.get(i).setActive(false);
            }
        }
        
        

        address = inputReader.getSpeciesName();
        
        switch(currentPage) {
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
            case 1:
                if (buttons.get(4).isPressed()) {
                    buttons.get(4).setPressed(false);
                    game.mutliInitServer();
                    currentPage = 2;
                }
                
                if (buttons.get(5).isPressed()) {
                    buttons.get(5).setPressed(false);
                    currentPage = 3;
                }
                if (buttons.get(3).isPressed()) {
                    buttons.get(3).setPressed(false);
                    currentPage = 0;
                }
                break;
            case 2:
                if (buttons.get(3).isPressed()) {
                    buttons.get(3).setPressed(false);
                    currentPage = 1;
                }
                
                if (game.getNetwork().isClientReady()) {
                    single = false;
                    load = false;
                    host = true;
                    join = false;
                    game.getNetwork().sendReady(false);
                }
                break;
            case 3:
                timeOpen++;
            
                if (game.getG().getFontMetrics().stringWidth(inputReader.getSpeciesName()) > 383) {
                    inputReader.setOnlyDelete(true);
                }
                else {
                    inputReader.setOnlyDelete(false);
                }

                inputReader.readInput();
                address = inputReader.getSpeciesName();
                
                if (buttons.get(3).isPressed()) {
                    buttons.get(3).setPressed(false);
                    currentPage = 1;
                }
                
                if (buttons.get(5).isPressed()) {
                    buttons.get(5).setPressed(false);
                    game.multiInitClient(address);
                }
                
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
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(backgrounds.get(currentPage), x, y, width, height, null);
        
        switch (currentPage) {
            case 0:
                buttons.get(0).render(g);
                buttons.get(1).render(g);
                buttons.get(2).render(g);
                buttons.get(3).render(g);
                break;
            case 1:
                buttons.get(3).render(g);
                buttons.get(4).render(g);
                buttons.get(5).render(g);
                break;
            case 2:
                buttons.get(3).render(g);
                break;
            case 3:
                buttons.get(3).render(g);
                buttons.get(5).render(g);
                g.setColor(Commons.FONT_COLOR);
                g.setFont(fontEvolve.deriveFont(40f));

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

    public boolean isToMainMenu() {
        return toMainMenu;
    }

    public boolean isSingle() {
        return single;
    }

    public boolean isLoad() {
        return load;
    }

    public boolean isHost() {
        return host;
    }

    public boolean isJoin() {
        return join;
    }

    public void setToMainMenu(boolean toMainMenu) {
        this.toMainMenu = toMainMenu;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }
}
