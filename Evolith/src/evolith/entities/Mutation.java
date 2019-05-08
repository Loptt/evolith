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
public class Mutation implements Commons {
    
    /**
     * Four evolutionary traits
     */
    private int maxHealth;
    private int speed;
    private int strength;
    private int stealth;
    
    private String name;    //Mutation name
    private Game game;      //Game object
    
    private double xOff;    //x coordinate render offset
    private double yOff;    //y coordinate render offset
    private double wOff;    //width modifier to render
    private double hOff;    //height modifier to render
    
    private boolean active; //Active state
    
    private int tier;       //mutation tier
    
    private BufferedImage sprite;       //Sprite to render in organism
    private BufferedImage mutSprite;    //Sprite to render in mutation panel
    
    private Organism org;               //mutation of organism
        
    
    /**
     * Mutation constructor
     * @param name mutation name
     * @param strength current strength
     * @param speed current speed
     * @param health current health
     * @param stealth current stealth
     * @param active active state
     * @param tier mutation tier
     * @param x x offset
     * @param y y offset
     * @param width width modifier
     * @param height height modifier
     * @param game game object
     * @param org organism
     */
    public Mutation(String name, int strength,int speed,int health, int stealth, boolean active, int tier, double x, double y, double width, double height, Game game, Organism org) {
        
        this.maxHealth = health;
        this.speed = speed;
        this.strength = strength; 
        this.stealth = stealth;
        this.active = active;
        this.tier = tier;
        this.name = name;
        this.game = game;
        this.org  = org;
        
        xOff = x;
        yOff = y;
        wOff = width;
        hOff = height;
    }

    /**
     * to get max health
     * @return maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * to get the mutation name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * to get speed
     * @return speed
     */
    public int getSpeed(){
        return speed;
    }
    
    /**
     * to get strength
     * @return strength
     */
    public int getStrength(){
        return strength;
    }
    
    /**
     * to get stealth
     * @return stealth
     */
    public int getStealth(){
        return stealth;
    }
    
    /**
     * to get the tier
     * @return tier
     */
    public int getTier(){
        return tier;
    }
    
    /**
     * to get the mutation image
     * @return sprite
     */
    public BufferedImage getSprite(){
        return sprite;
    }

    /**
     * to get the mutation panel sprite
     * @return sprite if null, mutSprite otherwise
     */
    public BufferedImage getMutSprite() {
        if (mutSprite == null) {
            return sprite;
        }
        return mutSprite;
    }

    /**
     * set mutSprite
     * @param mutSprite new sprite
     */
    public void setMutSprite(BufferedImage mutSprite) {
        this.mutSprite = mutSprite;
    }

    /**
     * to check if active
     * @return active
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     * to set active state
     * @param active active state
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * to set strength
     * @param strength new strength
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * to set tier
     * @param tier new tier
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    /**
     * to set stealth
     * @param stealth new stealth
     */
    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    /**
     * to set speed
     * @param speed new speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * to set name
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * to set max health
     * @param maxHealth new health
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    /**
     * to set the sprite
     * @param sprite new spirte
     */
    public void setSprite(BufferedImage sprite){
        this.sprite = sprite;
    }
    
    /**
     * to render the mutation 
     * @param g graphics
     */
    public void render(Graphics g) {
        if(active){
        g.drawImage(sprite, game.getCamera().getRelX((int) (org.getX()+org.getCurrentSize()*xOff)), game.getCamera().getRelY((int) (org.getY()+org.getCurrentSize()*yOff)), (int) (org.getCurrentSize()*wOff), (int) (org.getCurrentSize()*hOff), null);
        }
    }
}
