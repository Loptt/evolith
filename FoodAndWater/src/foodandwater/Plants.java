/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodandwater;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author charles
 */
public class Plants implements Commons {
    
    private ArrayList<Plant> plants;
    private Game game;
    
    private int amount;
    
    public Plants(Game game) {
        this.game = game;
        plants = new ArrayList<>();
        amount = 1;
        
        for (int i = 0; i < amount; i++) {
            plants.add(new Plant(400, 400, PLANT_SIZE, PLANT_SIZE));
        }
    }
    
    public void tick() {
        for (int i = 0; i < amount; i++) {
            plants.get(i).tick();
        }
    }
    
    public void render(Graphics g) {
        for (int i = 0; i < amount; i++) {
            plants.get(i).render(g);
        }
    }
    
    private class Plant extends Item {
        
        int quantity;

        public Plant(int x, int y, int width, int height) {
            super(x, y, width, height);
            quantity = 100;
        }

        @Override
        public void tick() {
            
        }

        @Override
        public void render(Graphics g) {
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawImage(Assets.plant, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
            g.drawString(Integer.toString(quantity) + "/100", game.getCamera().getRelX(x) + 45, game.getCamera().getRelY(y) + 150);
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
