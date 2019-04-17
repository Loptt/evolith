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
import java.awt.image.BufferedImage;

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
    
    private BufferedImage sprite;
    
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

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }
    
    
    public int getSpeed(){
        return speed;
    }
    
    public int getStrength(){
        return strength;
    }
    
    public int getStealth(){
        return stealth;
    }
    
    public int getTier(){
        return tier;
    }
    
    public BufferedImage getSprite(){
        return sprite;
    }

    public boolean isbMutated() {
        return bMutated;
    }
    
    public void setbMutated(boolean bMutated) {
        this.bMutated = bMutated;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    public void setSprite(BufferedImage sprite){
        this.sprite = sprite;
    }
    
    @Override
    public void render(Graphics g) {
    
    }
    
}
