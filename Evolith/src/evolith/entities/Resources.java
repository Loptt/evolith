package evolith.entities;

import evolith.game.Game;
import evolith.game.Item;
import evolith.engine.Assets;
import evolith.helpers.Circle;
import evolith.helpers.Commons;
import static evolith.helpers.Commons.PLANTS_AMOUNT;
import static evolith.helpers.Commons.PLANT_SIZE;
import static evolith.helpers.Commons.WATERS_AMOUNT;
import static evolith.helpers.Commons.WATER_SIZE;
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



public class Resources implements Commons {
    private long numberOfIntersections = 0;

    private ArrayList<Plant> plants;    // arrays of the plants
    private ArrayList<Water> waters;
    private Game game;                  //game instance

    private int watersAmount;                 //max amount of waters
    private int plantsAmount;

    /**
     * Constructor of the plants in the game
     *
     * @param game
     */
    public Resources(Game game) {
        this.game = game;
        plants = new ArrayList<>();
        waters = new ArrayList<>();
        watersAmount = WATERS_AMOUNT;
        plantsAmount = PLANTS_AMOUNT;
        Random randomGen = new Random();

       
        for (int i = 0; i <plantsAmount; ++i){
            System.out.println("amount " + plantsAmount);
            int xCoord, yCoord; 
            xCoord = randomGen.nextInt(5000) + 1;
            yCoord = randomGen.nextInt(5000) + 1;
            plants.add(new Plant(xCoord, yCoord, PLANT_SIZE, PLANT_SIZE));
            
            Circle actualCircle = plants.get(i).getRadius();
            System.out.println(i);
            if(checkPlantsRadius(actualCircle, i) && i > 0){
                System.out.println("deleting");
                plants.remove(i);
                i--;
            }
            System.out.println("voy en la planta # " + i );
            System.out.println("x: " + xCoord + " y: " + yCoord);
        }
        
        
        for (int i = 0; i <watersAmount; ++i) {
            System.out.println("amount " + watersAmount);
            int xCoord, yCoord; 
            xCoord = randomGen.nextInt(5000) + 1;
            yCoord = randomGen.nextInt(5000) + 1;
            waters.add(new Water(xCoord, yCoord, WATER_SIZE, WATER_SIZE));
            
            Circle actualCircle = waters.get(i).getRadius();
            System.out.println(i);
            if(checkPlantsRadius(actualCircle, i) && i > 0){
                System.out.println("deleting");
                waters.remove(i);
                i--;
            }
            System.out.println("voy en el agua # " + i );
            System.out.println("x: " + xCoord + " y: " + yCoord);
        }
    }
    
    public boolean checkPlantsRadius(Circle c, int actualIndex) {
        for (int i = 0; i <= plants.size()-1; i++) {
        System.out.println("checking" + i);
            if (plants.get(i).getRadius().intersects(c) && i != actualIndex) {
                System.out.println("INTERSECTION.");
                return true;
            }
        }
        
        return false;
    }
    public Item containsResource(int x, int y) {
        for (int i = 0; i < plants.size()-1; i++) {
            if (plants.get(i).getPerimeter().contains(x, y)) {
                return plants.get(i);
            }
        }
        
        return null;
    }
    
    public Plant getPlant(int i) {
       return plants.get(i);
    }
    
    public Water getWater(int i) {
       return waters.get(i);
    }

    /**
     * To tick the resources
     */
    public void tick() {
        for (int i = 0; i < plantsAmount; i++) {
            plants.get(i).tick();
        }
        
        for (int i = 0; i <  watersAmount; i++) {
            waters.get(i).tick();
        }
    }

    /**
     * To render the plants
     *
     * @param g
     */
    public void render(Graphics g) {
        for (int i = 0; i < plantsAmount; i++) {
            plants.get(i).render(g);
        }
        
        for (int i = 0; i <  watersAmount; i++) {
            waters.get(i).render(g);
        }
    }
    
    public boolean checkRadius(Circle c, int actualIndex) {
        
        for (int i = 0; i <= plants.size()-1; i++) {
        //System.out.println("checking" + i);
            if (plants.get(i).getRadius().intersects(c) && i != actualIndex) {
                System.out.println("INTERSECTION." + numberOfIntersections++);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean assignOnResource(Rectangle r) {
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).intersects(r)) {
                if (!plants.get(i).isFull()) {
                    plants.get(i).setParasites(plants.get(i).getParasites()+1);
                    if (plants.get(i).getParasites() >= 6) {
                        plants.get(i).setFull(true);
                    }
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public class Water extends Item {

        int quantity;   // maximum quanitity of food per plant;

        /**
         * Constructor of a new plant
         *
         * @param x
         * @param y
         * @param width
         * @param height
         */
        public Water(int x, int y, int width, int height) {
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

            g.drawImage(Assets.water, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);

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
    
    public class Plant extends Item {

        private int quantity;   // maximum quanitity of food per plant;
        private boolean full;
        private int parasites;

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
            full = false;
            parasites = 0;
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
            g.setColor(Color.RED);
            g.drawOval(game.getCamera().getRelX(radius.getX() - width / 2), game.getCamera().getRelY(radius.getY() - width / 2), radius.getRadius(), radius.getRadius());
            

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

        public boolean isFull() {
            return full;
        }

        public void setFull(boolean full) {
            this.full = full;
        }

        public int getParasites() {
            return parasites;
        }

        public void setParasites(int parasites) {
            this.parasites = parasites;
        }
        
        
    }
}
