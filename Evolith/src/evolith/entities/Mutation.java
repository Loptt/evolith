/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.game.Game;
import evolith.game.Item;
import evolith.helpers.Commons;
import java.awt.Graphics;

/**
 *
 * @author ErickFrank
 */
public class Mutation extends Item implements Commons {
    
    private int health;
    private int speed;
    private int strength;
    private int stealth;
    private String name;
    
    private boolean bMutated;
    
    private int tier;
    
    private Game game;
    
    public Mutation(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Mutation(String name, int strength,int speed,int health, int stealth, boolean bMutated, int tier, int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.health = health;
        this.speed = speed;
        this.strength = strength;
        this.stealth = stealth;
        this.bMutated = bMutated;
        this.tier = tier;
        this.name = name;
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
    
    }
    
}
