package evolith.entities;

import evolith.game.*;
import evolith.engine.*;
import evolith.entities.*;
import evolith.helpers.*;
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



public class ResourceManager implements Commons {

    private ArrayList<Resource> plants;    // arrays of the plants
    private ArrayList<Resource> waters;
    private Game game;                  //game instance

    private int watersAmount;                 //max amount of waters
    private int plantsAmount;

    /**
     * Constructor of the plants in the game
     *
     * @param game
     */
    public ResourceManager(Game game) {
        this.game = game;
        plants = new ArrayList<>();
        waters = new ArrayList<>();
        watersAmount = WATERS_AMOUNT;
        plantsAmount = PLANTS_AMOUNT;
        
        generateResources();
    }
    
    public void generateResources() {
        Random randomGen = new Random();

        for (int i = 0; i < plantsAmount; ++i) {
            System.out.println("amount " + plantsAmount);
            int xCoord, yCoord; 
            xCoord = randomGen.nextInt(5000) + 1;
            yCoord = randomGen.nextInt(5000) + 1;
            plants.add(new Resource(xCoord, yCoord, PLANT_SIZE, PLANT_SIZE, game, Resource.ResourceType.Plant));
            
            //Circle actualCircle = plants.get(i).getRadius();
            System.out.println(i);
            
            for (int j = 0; j < plants.size() - 1; j++) {
                if (plants.get(i).intersects(plants.get(j))) {
                    plants.remove(i);
                    i--;
                }
            }
        }
        
        
        for (int i = 0; i < watersAmount; ++i) {
            System.out.println("amount " + watersAmount);
            int xCoord, yCoord; 
            xCoord = randomGen.nextInt(5000) + 1;
            yCoord = randomGen.nextInt(5000) + 1;
            waters.add(new Resource(xCoord, yCoord, WATER_SIZE, WATER_SIZE, game, Resource.ResourceType.Water));
            
            //Circle actualCircle = waters.get(i).getRadius();
            System.out.println(i);
            
            for (int j = 0; j < waters.size() - 1; j++) {
                if (waters.get(i).intersects(waters.get(j))) {
                    waters.remove(i);
                    i--;
                }
            }
        }
    }
    
    public Resource containsResource(int x, int y) {
        for (int i = 0; i < plants.size()-1; i++) {
            if (plants.get(i).getPerimeter().contains(x, y)) {
                return plants.get(i);
            }
        }
        
        for (int i = 0; i < waters.size()-1; i++) {
            if (waters.get(i).getPerimeter().contains(x, y)) {
                return waters.get(i);
            }
        }
        
        return null;
    }
    
    public void tick() {
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).tick();
        }
        
        for (int i = 0; i < waters.size(); i++) {
            waters.get(i).tick();
        }
    }
        
    public void render(Graphics g) {
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).render(g);
        }
        
        for (int i = 0; i < waters.size(); i++) {
            waters.get(i).render(g);
        }
    }
    
    /*
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
*/
    /**
     * To tick the resources
     */
    /*public void tick() {
        for (int i = 0; i < plantsAmount; i++) {
            plants.get(i).tick();
        }
        
        for (int i = 0; i <  watersAmount; i++) {
            waters.get(i).tick();
        }
    }*/
/*
    /**
     * To render the plants
     *
     * @param g
     */
    /*public void render(Graphics g) {
        for (int i = 0; i < plantsAmount; i++) {
            plants.get(i).render(g);
        }
        
        for (int i = 0; i <  watersAmount; i++) {
            waters.get(i).render(g);
        }
    }
    
    /*
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
    }*/
}
