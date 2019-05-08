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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
    private int plantsAmount;                 //max amount of plants
    private int deadWaters;                   //dead waters
    private int deadPlants;                   //dead plants

    /**
     * Constructor of the plants in the game
     *
     * @param game Game object
     */
    public ResourceManager(Game game) {
        this.game = game;
        plants = new ArrayList<>();
        waters = new ArrayList<>();
        watersAmount = WATERS_AMOUNT;
        plantsAmount = PLANTS_AMOUNT;
        deleteResources();
        respawnResources();
    }
    
    /**
     * initialize resources
     */
    public void init() {
        generateResources();
    }
    
    /**
     * create new resources
     */
    public void respawnResources(){
        Random randomGen = new Random();
        for (int i = 0; i < deadPlants; ++i) {
            int xCoord, yCoord; 
            xCoord = randomGen.nextInt(5000) + 1;
            yCoord = randomGen.nextInt(5000) + 1;
            plants.add(new Resource(xCoord, yCoord, PLANT_SIZE, PLANT_SIZE, game, Resource.ResourceType.Plant));
            
            //System.out.println("plants size: " + plants.size());
            
            for (int j = 0; j < plants.size()-1; j++) {
                if (plants.get(plants.size()-1).intersects(plants.get(j))) {
                    plants.remove(i);
                    i--;
                }
            }
        }
        
        
        for (int i = 0; i < deadWaters; ++i) {
            int xCoord, yCoord; 
            xCoord = randomGen.nextInt(5000) + 1;
            yCoord = randomGen.nextInt(5000) + 1;
            waters.add(new Resource(xCoord, yCoord, WATER_SIZE, WATER_SIZE, game, Resource.ResourceType.Water));

            //System.out.println("waters size: " + waters.size());
            
            for (int j = 0; j < waters.size() - 1; j++) {
                if (waters.get(waters.size() - 1).intersects(waters.get(j)) && i != j) {
                    waters.remove(i);
                    i--;
                }
            }
        }
  
    
    }
    
    /**
     * Delete dead resrouces
     */
    public void deleteResources(){
        deadWaters = 0;
        deadPlants = 0;
        for(int i=0; i<waters.size(); i++){
            if(waters.get(i).isOver()){
                deadWaters++;
                waters.remove(i);
            }
        }
        for(int i=0; i<plants.size(); i++){
            if(plants.get(i).isOver()){
                deadPlants++;
                plants.remove(i);
            }
        }
    }
    
    /**
     * Generate new resources
     */
    public void generateResources(){
        Random randomGen = new Random();
        
        int newWidthWaters = (int) Math.ceil( 5000/Math.sqrt(WATERS_AMOUNT) );
        int newHeightWaters = (int) Math.ceil( 5000/Math.sqrt(WATERS_AMOUNT) );
         
        for(int i=newWidthWaters; i<5000 - 2 * newWidthWaters; i+=newWidthWaters){
            for(int j=newHeightWaters; j<5000 - 2 * newHeightWaters; j+=newHeightWaters){
                int xCoord, yCoord; 
                xCoord = randomGen.nextInt(newWidthWaters) + j;
                yCoord = randomGen.nextInt(newHeightWaters) + i;
                waters.add(new Resource(xCoord, yCoord, WATER_SIZE, WATER_SIZE, game, Resource.ResourceType.Water));
            }
        }
        
        int newWidthPlants = (int) Math.ceil( 5000/Math.sqrt(PLANTS_AMOUNT) );
        int newHeightPlants = (int) Math.ceil( 5000/Math.sqrt(PLANTS_AMOUNT) );
        
        for(int i = newWidthPlants; i < 5000 - 2 * newWidthPlants; i += newWidthPlants){
            for(int j = newHeightPlants; j < 5000 - 2 * newHeightPlants; j += newHeightPlants){
                int xCoord, yCoord; 
                xCoord = randomGen.nextInt(newWidthPlants) + j;
                yCoord = randomGen.nextInt(newHeightPlants) + i;
                plants.add(new Resource(xCoord, yCoord, PLANT_SIZE, PLANT_SIZE, game, Resource.ResourceType.Plant));
            }
        }
    }
    
    /**
     * Check if the coordinates contain a resource
     * @param x x coordinate
     * @param y y coordinate
     * @return the contained resource
     */
    public Resource containsResource(int x, int y) {
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).getPerimeter().contains(x, y)) {
                return plants.get(i);
            }
        }
        
        for (int i = 0; i < waters.size(); i++) {
            if (waters.get(i).getPerimeter().contains(x, y)) {
                return waters.get(i);
            }
        }
        
        return null;
    }
    
    /**
     * empty parasites in all resources
     */
    public void emptyParasites() {
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).removeParasites();
        }
        
        for (int i = 0; i < waters.size(); i++) {
            waters.get(i).removeParasites();
        } 
    }
    
    /**
     * Save current game state to the print writer
     * @param pw print writer
     */
    public void save(PrintWriter pw) {
        pw.println(Integer.toString(plants.size()));
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).save(pw);
        }
        
        pw.println(Integer.toString(waters.size()));
        for (int i = 0; i < waters.size(); i++) {
            waters.get(i).save(pw);
        }
    }
    
    /**
     * Load last saved state from the buffered reader
     * @param br buffered reader
     * @throws IOException 
     */
    public void load(BufferedReader br) throws IOException {
        int plantam = Integer.parseInt(br.readLine());
        plants.clear();
        
        for (int i = 0; i < plantam; i++) {
            plants.add(new Resource(0,0,PLANT_SIZE, PLANT_SIZE, game, Resource.ResourceType.Plant));
            plants.get(i).load(br);
        }
        
        int wateram = Integer.parseInt(br.readLine());
        waters.clear();
        
        for (int i = 0; i < wateram; i++) {
            waters.add(new Resource(0,0,WATER_SIZE, WATER_SIZE, game, Resource.ResourceType.Water));
            waters.get(i).load(br);
        }
    }
    
    /**
     * Restart values to their initial states
     * @param server if it is a server
     */
    public void reset(boolean server) {
        plants.clear();
        waters.clear();
        
        if (server) {
            generateResources();
        }
    }
    
    /**
     * to get the current plants amount
     * @return plants amount
     */
    public int getPlantAmount() {
        return plants.size();
    }
    
    /**
     * to get the current water amount
     * @return water amount
     */
    public int getWaterAmount() {
        return waters.size();
    }
    
    /**
     * to get the plant at an index
     * @param i index
     * @return plant at index
     */
    public Resource getPlant(int i) {
        return plants.get(i);
    }
    
    /**
     * to get the water at an index
     * @param i index
     * @return water at index
     */
    public Resource getWater(int i) {
        return waters.get(i);
    }
    
    /**
     * to add a plant to the array
     * @param res plant to add
     */
    public void addPlant(Resource res) {
        plants.add(res);
    }
    
    /**
     * to remove a plant from the array
     * @param res plant to remove
     */
    public void removePlant(Resource res) {
        System.out.println("REMOVING IN remove");
        plants.remove(res);
    }
    
    /**
     * to add a water to the array
     * @param res water to add
     */
    public void addWater(Resource res) {
        waters.add(res);
    }
    
    /**
     * to remove a water from the array
     * @param res water to remove
     */
    public void removeWater(Resource res) {
        waters.remove(res);
    }
    
    /**
     * to tick the plants and waters
     */
    public void tick() {
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).tick();
        }
        
        for (int i = 0; i < waters.size(); i++) {
            waters.get(i).tick();
        }
    }
        
    /**
     * to render the plants and waters
     * @param g graphics
     */
    public void render(Graphics g) {
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).render(g);
        }
        
        for (int i = 0; i < waters.size(); i++) {
            waters.get(i).render(g);
        }
    }

    /**
     * to get the game object
     * @return
     */
    public Game getGame() {
        return game;
    }
}
