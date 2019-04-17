/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.engine.Assets;
import evolith.entities.Organism;
import evolith.game.Game;
import evolith.helpers.Commons;
import evolith.helpers.InputReader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErickFrank
 */
public class OrganismPanel extends Menu implements Commons {

    private int speed;
    private int size;
    private int strength;
    private int maturity;
    private int survivability;
    private int stealth;
    private int generation;
    private double duration;
    private Organism organism;
    
    private String fontPath;
    private String name;
    private Font fontEvolve;
    private InputStream is;
    
    private InputReader inputReader;

    private boolean active;
    private boolean clickEdit;
    
    public OrganismPanel(int x, int y, int width,int height,Game game) {
        super(x, y, width, height, game);
        active = false;
        fontPath = "/Fonts/MADE-Evolve-Sans-Regular.ttf";
        this.is = OrganismPanel.class.getResourceAsStream(fontPath);
        try {
            fontEvolve = Font.createFont(Font.TRUETYPE_FONT, is);
            fontEvolve = fontEvolve.deriveFont(20f);
        } catch (FontFormatException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        inputReader = new InputReader(game);
    }

    public OrganismPanel(int x, int y, int width, int height, Game game, Organism org)
    {
        super(x, y, width, height, game);
        this.organism = org;
        
        this.speed = org.getSpeed();
        this.size = org.getSize();
        this.strength = org.getStrength();
        this.survivability = org.getSurvivability();
        this.stealth = org.getStealth();
        this.maturity = org.getMaturity();
        this.generation = org.getGeneration();
        this.duration = org.getTime().getSeconds();
        this.name = org.getName();
        
        this.active = true;
        
        buttons.add(new Button(this.x + this.width - 20, this.y - 20, 40, 40)); // Exit
        buttons.add(new Button(this.x + 32, this.y + 28, 190, 35)); // Edit
        
        inputReader = new InputReader(game);
    }

    public String getName() {
        return name;
    }
    
    @Override
    public void tick() {
        
        inputReader.readInput();
        if (active) {
            
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                    System.out.println("button is here");
                    //if the mouse is over the button
                    buttons.get(i).setActive(true);
                    //if left click change mouse status
                    if (game.getMouseManager().isLeft()) {
                        buttons.get(i).setPressed(true);
                        game.getMouseManager().setLeft(false);
                    }
                } else {
                    buttons.get(i).setActive(false);
                }
                if (buttons.get(0).isPressed()) {
                    active = false;     
                }
                  
            }
        }
        
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isClickEdit() {
        return clickEdit;
    }

    public void setClickEdit(boolean clickEdit) {
        this.clickEdit = clickEdit;
    }

    @Override
    public void render(Graphics g) {
        
        if(active){
            
        g.drawImage(Assets.organismPanel_menu,x , y ,width, height, null);
        g.drawImage(Assets.organismPanel_close, x + width - 20, y - 20, BUTTON_CLOSE_DIMENSION, BUTTON_CLOSE_DIMENSION, null);        
        //Stealth
        g.setColor(Color.ORANGE);
        g.fillRect(x + 470, y + 114, (int) 70 * stealth / MAX_STEALTH, 20);
        //Max Health
        g.setColor(Color.CYAN);
        g.fillRect(x + 470, y + 169, (int) 70 * survivability / MAX_SURVIVABILITY, 20);
        //maturity
        g.setColor(Color.YELLOW);
        g.fillRect(x + 470, y + 226, (int) 70 * maturity / MAX_MATURITY, 20);
        
        //speed
        g.setColor(Color.MAGENTA);
        g.fillRect(x + 368, y + 114, (int) 70 * speed / MAX_SPEED, 20);
        //size
        g.setColor(Color.WHITE);
        g.fillRect(x + 368, y + 169, (int) 70 * size / MAX_SIZE, 20);
        //strength
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x + 368, y + 226, (int) 70 * strength / MAX_STRENGTH, 20);
        
        // Edit
        g.setColor(Color.WHITE);
        g.setFont(fontEvolve);
        g.drawString(Integer.toString(generation), x + 160, y + 277);
        g.drawString(Double.toString(duration), x + 130, y + 305);
        
        g.setColor(Color.WHITE);
        g.setFont(fontEvolve);

        g.drawString(name, x + 40, y + 57);
    }
        
    }
};
