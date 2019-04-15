package evolith.entities;

import evolith.game.Game;
import evolith.game.Item;
import evolith.engine.Assets;
import evolith.helpers.Commons;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Plants implements Commons {

    private ArrayList<Plant> plants;    // arrays of the plants 
    private Game game;                  //game instance

    private int amount;                 //max amount of the plant

    /**
     * Constructor of the plants in the game
     *
     * @param game
     */
    public Plants(Game game) {
        this.game = game;
        plants = new ArrayList<>();
        amount = PLANTS_AMOUNT;
        Random randomGen = new Random();

        for (int i = 0; i < amount; i++) {
            int xCoord, yCoord; 
            xCoord = randomGen.nextInt(1000) + 1;
            yCoord = randomGen.nextInt(1000) + 1;
            plants.add(new Plant(xCoord, yCoord, PLANT_SIZE, PLANT_SIZE));
        }
    }
    
    public Point containsPlant(int x, int y) {
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).getPerimeter().contains(x, y)) {
                return new Point(plants.get(i).getX() + PLANT_SIZE / 2, plants.get(i).getY() + PLANT_SIZE / 2);
            }
        }
        
        return new Point(-1,-1);
    }

    /**
     * To tick the plants
     */
    public void tick() {
        for (int i = 0; i < amount; i++) {
            plants.get(i).tick();
        }
    }

    /**
     * To render the plants
     *
     * @param g
     */
    public void render(Graphics g) {
        for (int i = 0; i < amount; i++) {
            plants.get(i).render(g);
        }
    }
    
    public boolean checkRadius(Rectangle r) {
        for (int i = 0; i < amount; i++) {
            if (plants.get(i).getRadius().intersects(r)) {
                System.out.println("INTERSECTION.");
                return true;
            }
        }
        
        return false;
    }

    public class Plant extends Item {

        int quantity;   // maximum quanitity of food per plant;

        /**
         * Constructor of a new plant
         *
         * @param x
         * @param y
         * @param width
         * @param height
         */
        public Plant(int x, int y, int width, int height) {
            super(x, y, width, height);
            quantity = 100;
        }

        /**
         * To tick the plant
         */
        @Override
        public void tick() {
        }

        /**
         * Renders the plant
         *
         * @param g
         */
        @Override
        public void render(Graphics g) {
            g.setColor(new Color(173, 255, 250));
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

            g.drawImage(Assets.plant, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);

            //To display the actual quantity over the maximum
            g.drawString(Integer.toString(quantity) + "/100", game.getCamera().getRelX(x) + 45, game.getCamera().getRelY(y) + 150);
        }

        /**
         * To get the quantity of the plant
         *
         * @return quantity
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * To set the quantity of the plant
         *
         * @param quantity
         */
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
