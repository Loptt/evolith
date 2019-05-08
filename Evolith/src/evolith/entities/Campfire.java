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
import java.awt.Graphics;

/**
 *
 * @author USUARIO
 */
public class Campfire extends Item implements Commons{
    int life;
    boolean over;
    int count;
    Game game;
    
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
            life = life - 20;
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
    
    
}
