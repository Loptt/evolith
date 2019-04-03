/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camerapanningtest;

import java.awt.Graphics;

/**
 *
 * @author charles
 */
public class Player extends Item {
    
    private Game game;

    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
    }

    @Override
    public void tick() {
        x++;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, game.getCamera().getTransX(x), game.getCamera().getTransY(y), width, height, null);
    }
    
}
