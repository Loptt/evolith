/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.game.Game;
import evolith.game.Item;
import java.awt.Graphics;

/**
 *
 * @author charles
 */
public abstract class Resource extends Item {

    protected int quantity;
    protected Game game;
    
    public Resource(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        quantity = 100;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public abstract void tick();

    @Override
    public abstract void render(Graphics g);
    
}
