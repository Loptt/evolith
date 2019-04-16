package evolith.entities;

import evolith.game.Game;
import evolith.game.Item;
import evolith.engine.Assets;
import evolith.entities.Organisms.Organism;
import evolith.helpers.*;
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
    
    
    public int getPlantsAmount(){
            return plantsAmount;
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
    
    public Item assignOnResource(Rectangle r) {
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).intersects(r)) {
                if (!plants.get(i).isFull()) {
                    plants.get(i).setParasitesAm(plants.get(i).getParasitesAm()+1);
                    if (plants.get(i).getParasitesAm() >= 6) {
                        System.out.println("FULL");
                        plants.get(i).setFull(true);
                    }
                    return plants.get(i);
                }
            }
        }
        
        return null;
    }
    
    public class Water extends Item {

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
            g.drawString(Integer.toString(qty) + "/100", game.getCamera().getRelX(x) + 45, game.getCamera().getRelY(y) + 150);
        }
    }
    
    public class Plant extends Item {

        private boolean full;
        private int parasitesAm;
        private ArrayList<Point> positions;
        private ArrayList<Organism> parasites;

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
            full = false;
            positions = SwarmMovement.getPositions(x, y, 6, 1);
            System.out.println("PLAN CREATED");
            parasitesAm = 0;
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
            g.drawString(Integer.toString(qty) + "/100", game.getCamera().getRelX(x) + 45, game.getCamera().getRelY(y) + 150);
        }


        public boolean isFull() {
            return full;
        }

        public void setFull(boolean full) {
            this.full = full;
        }

        public int getParasitesAm() {
            return parasitesAm;
        }

        public void setParasitesAm(int parasitesAm) {
            this.parasitesAm = parasitesAm;
        }
        
        public void addParasite(Organism org) {
            parasites.add(org);
            //org.setPoint();
        }
        
        public void removeParasite(Organism org) {
            parasites.remove(org);
        }
        
        public Point getNextAvailablePosition() {
            System.out.println(positions.size());
            Point p = positions.get(0);
            positions.remove(0);
            return p;
        }
    }
}
