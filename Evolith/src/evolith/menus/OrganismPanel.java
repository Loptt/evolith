/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.engine.Assets;
import evolith.game.Game;
import evolith.helpers.Commons;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author ErickFrank
 */
public class OrganismPanel extends Menu implements Commons {

    private int speed;
    private int size;
    private int strength;
    private int hunger;
    private int thirst;
    private int maturity;
    private int generation;
    private double duration;

    private boolean active;
    private boolean clickClose;
    private boolean clickEdit;
    
    public OrganismPanel(int x, int y, int width,int height,Game game) {
        super(x, y, width, height, game);
        active = false;
    }

    public OrganismPanel(int x, int y, int width, int height,int speed, int size, int strength, int hunger, int thirst, int maturity, int generation, double duration, Game game) {
        super(x, y, width, height, game);
        active = true;
        clickClose = false;
        clickEdit = false;
        this.speed = speed;
        this.size = size;
        this.strength = strength;
        this.hunger = hunger;
        this.thirst = thirst;
        this.maturity = maturity;
        this.generation = generation;
        this.duration = duration;

        buttons.add(new Button(this.x + this.width - 20, this.y - 20, 40, 40)); // Exit
        buttons.add(new Button(this.x + (this.width / 2) - 25, this.y + 250, 50, 30)); // Edit
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public int getMaturity() {
        return maturity;
    }

    public void setMaturity(int maturity) {
        this.maturity = maturity;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
    
    @Override
    public void tick() {
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
                    setClickEdit(true);
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
        //hunger
        g.setColor(Color.green);
        g.fillRect(x + 31, y + 114, (int) 70 * hunger / MAX_HUNGER, 20);
        //thirst
        g.setColor(Color.blue);
        g.fillRect(x + 32, y + 169, (int) 70 * thirst / MAX_THIRST, 20);
        //maturity
        g.setColor(Color.orange);
        g.fillRect(x + 32, y + 226, (int) 70 * maturity / MAX_MATURITY, 20);
        
        //speed
        g.setColor(Color.MAGENTA);
        g.fillRect(x + 145, y + 114, (int) 70 * speed / MAX_SPEED, 20);
        //size
        g.setColor(Color.pink);
        g.fillRect(x + 145, y + 169, (int) 70 * size / MAX_SIZE, 20);
        //strength
        g.setColor(Color.cyan);
        g.fillRect(x + 145, y + 226, (int) 70 * strength / MAX_STRENGTH, 20);
        
        g.setColor(Color.RED);
        g.drawRect(x + 32, y + 28, 190, 35);
        // Edit
    }
    }
};
