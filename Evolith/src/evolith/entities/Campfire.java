/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.engine.Assets;
import evolith.game.Game;
import evolith.game.Item;
import evolith.helpers.Commons;
import evolith.helpers.SwarmMovement;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author USUARIO
 */
public class Campfire extends Item implements Commons{
    int life;
    boolean over;
    int count;
    Game game;
    
    int rad;
    
    public Campfire(int x, int y, int width, int height, Game game){
        super(x, y, width, height);
        life = 100;
        count = 0;
        this.game = game;
    }

    public int getLife() {
        return life;
    }

    public boolean isOver() {
        return over;
    }

    public void setLife(int life) {
        this.life = life;
    }
    

    public void setOver(boolean over) {
        this.over = over;
    }
    
    public void decreaseLife(){
        count++;
        if(count>=60){
            life = life - 5;
            count = 0;
            if(life<=0){
                over = true;
            }
        }
    }
    
    @Override
    public void tick() {
        decreaseLife();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.campfire, game.getCamera().getRelX(x)-(width/2), game.getCamera().getRelY(y)-(height/2), width, height, null);
    }

    public int getRad() {
        return width / 2;
    }
    
    public Point getCenter() {
        return new Point(x + width/2, y + height / 2);
    }
    
    public boolean containsRectInRad(Rectangle r) {
        if (SwarmMovement.distanceBetweenTwoPoints(r.x, r.y, x + width / 2, y + height / 2) < getRad()) {
            return true;
        }
        
        if (SwarmMovement.distanceBetweenTwoPoints(r.x + r.width, r.y, x + width / 2, y + height / 2) < getRad()) {
            return true;
        }
        
        if (SwarmMovement.distanceBetweenTwoPoints(r.x, r.y + r.height, x + width / 2, y + height / 2) < getRad()) {
            return true;
        }
        
        if (SwarmMovement.distanceBetweenTwoPoints(r.x + r.width, r.y + r.height, x + width / 2, y + height / 2) < getRad()) {
            return true;
        }
        
        return false;
    }
}
