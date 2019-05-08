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
    
    private int maxHealth;
    private int speed;
    private int strength;
    private int stealth;
    private String name;
    private Game game;
    
    private double xOff;
    private double yOff;
    private double wOff;
    private double hOff;
    
    private boolean active;
    
    private int tier;
    
    private BufferedImage sprite;
    private BufferedImage mutSprite;
    
    private Organism org;
        
    

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

    public int getMaxHealth() {
        return maxHealth;
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

    public BufferedImage getMutSprite() {
        if (mutSprite == null) {
            return sprite;
        }
        return mutSprite;
    }

    public void setMutSprite(BufferedImage mutSprite) {
        this.mutSprite = mutSprite;
    }

    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
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

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    public void setSprite(BufferedImage sprite){
        this.sprite = sprite;
    }
    
    public void render(Graphics g) {
        if(active){
        g.drawImage(sprite, game.getCamera().getRelX((int) (org.getX()+org.getCurrentSize()*xOff)), game.getCamera().getRelY((int) (org.getY()+org.getCurrentSize()*yOff)), (int) (org.getCurrentSize()*wOff), (int) (org.getCurrentSize()*hOff), null);
        }
    }
}
