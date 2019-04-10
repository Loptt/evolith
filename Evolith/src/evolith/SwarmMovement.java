/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith;

import java.awt.Point;
import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 *
 * @author charles
 */
public class SwarmMovement implements Commons {
        
    public static void init() {
    }
    /*
    public static void generateNewPoints(int n) {
        positions = new ArrayList<>();
        positions.add(new Point(0,0));
        
        ArrayList<Point> generatedHex = generateHexTangents(0,0);
        
        for (int i = 0; i < n; i++) {
            positions.add(generatedHex.get(i));
        }
    }*/
    /*
    public static void generatePoints() {
        positions = new ArrayList<>();
        int colLimit = 0;
        int currRow = 0;
        int currCol = 0;

        for (int i = 0; i < MAX_ORGANISM_AMOUNT; i++) {
            positions.add(new ArrayList<>());
            colLimit = (int) Math.sqrt(i) + 1;
            currCol = 0;
            currRow = 0;
            
            for (int j = 0; j < i + 1; j++) {
                if (currCol >= colLimit) {
                    currRow++;
                    currCol = 0;
                }
                
                positions.get(i).add(new Point(currCol * (ORGANISM_SIZE - 20), currRow * (ORGANISM_SIZE - 20)));
                
                currCol++;
            }
        }
    }*/
    
    public static ArrayList<Point> generateHexPattern(int n) {
        if (n < 1) {
            return null;
        }
        
        ArrayList<Point> positions = new ArrayList<>();
        positions.add(new Point(0, 0));
        
        int layerAmount = (n-2) / 6 + 1; // WRONG
        
        ArrayList<Point> corners = new ArrayList<>();
        
        for (int i = 0; i < 6; i++) {
            corners.add(new Point(0, 0));
        }
        
        for (int i = 0; i < layerAmount; i++) {
            
            //Top left
            System.out.print("Prev top left:  ");
            System.out.println(corners.get(0));
            corners.get(0).setLocation(corners.get(0).x - SWARM_SEPARATION / 2, corners.get(0).y + (int) (sqrt(3)/2 * SWARM_SEPARATION));
            System.out.print("New top left:  ");
            System.out.println(corners.get(0));
            //Top right
            corners.get(1).setLocation(corners.get(1).x + SWARM_SEPARATION / 2, corners.get(1).y + (int) (sqrt(3)/2 * SWARM_SEPARATION));
            //Right
            corners.get(2).setLocation(corners.get(2).x + SWARM_SEPARATION, corners.get(2).y);
            //Bottom Right
            corners.get(3).setLocation(corners.get(3).x + SWARM_SEPARATION / 2, corners.get(3).y - (int) (sqrt(3)/2 * SWARM_SEPARATION));
            //Bottom Left
            corners.get(4).setLocation(corners.get(4).x - SWARM_SEPARATION / 2, corners.get(4).y - (int) (sqrt(3)/2 * SWARM_SEPARATION));
            //Left
            corners.get(5).setLocation(corners.get(5).x - SWARM_SEPARATION, corners.get(5).y);
            
            for (int j = 0; j < corners.size(); j++) {
                positions.add((Point) corners.get(j).clone());
            }
            
            for (int j = layerAmount - i - 1; j < layerAmount - 1 && i != 0; j++) {
                //Top left
                positions.add(new Point(corners.get(0).x + SWARM_SEPARATION * (j+1), corners.get(0).y));
                //Top right
                positions.add(new Point(corners.get(1).x + SWARM_SEPARATION/2 * (j+1), corners.get(1).y - (int) (sqrt(3)/2 * SWARM_SEPARATION) * (j+1)));
                //Right
                positions.add(new Point(corners.get(2).x - SWARM_SEPARATION/2 * (j+1), corners.get(2).y - (int) (sqrt(3)/2 * SWARM_SEPARATION) * (j+1)));
                //Bottom right
                positions.add(new Point(corners.get(3).x - SWARM_SEPARATION * (j+1), corners.get(3).y));
                //BottomLeft
                positions.add(new Point(corners.get(4).x - SWARM_SEPARATION/2 * (j+1), corners.get(4).y + (int) (sqrt(3)/2 * SWARM_SEPARATION) * (j+1)));
                //Left
                positions.add(new Point(corners.get(5).x + SWARM_SEPARATION/2 * (j+1), corners.get(5).y + (int) (sqrt(3)/2 * SWARM_SEPARATION) * (j+1)));
                System.out.println("Adding medium..");
            }
        }
        
            System.out.println("Pre-Transformed:");
        for (int i = 0; i < positions.size(); i++) {
            System.out.println(positions.get(i));
        }
        
        return positions;
    }
    
    public static ArrayList<Point> getPositions(int x, int y, int num) {
        ArrayList<Point> positions = generateHexPattern(num);
        
            System.out.println("Transformed:");
        for (int i = 0; i < positions.size(); i++) {
            positions.get(i).x += x + generateRandomness(0);
            positions.get(i).y += y + generateRandomness(0);
            
            System.out.println(positions.get(i));
        }
        
        return positions;
    }
    /*
    public static ArrayList<Point> getPositions(int x, int y, int num, int obj) {
        SwarmMovement.generateNewPoints(num);
        
        for (int i = 0; i < customPosition.size(); i++) {
            customPosition.get(i).x += x + generateRandomness(100);
            customPosition.get(i).y += y + generateRandomness(100);
        }
        
        customPosition.remove(num/2);
        return customPosition;
    }*/
    /*
    private static ArrayList<Point> generateHexTangents(int x, int y) {
        ArrayList<Point> result = new ArrayList<>();
        
        result.add(new Point(x - SWARM_SEPARATION / 2, y + (int) (sqrt(3)/2 * SWARM_SEPARATION)));
        result.add(new Point(x + SWARM_SEPARATION / 2, y + (int) (sqrt(3)/2 * SWARM_SEPARATION)));
        result.add(new Point(x + SWARM_SEPARATION, y));
        result.add(new Point(x + SWARM_SEPARATION / 2, y - (int) (sqrt(3)/2 * SWARM_SEPARATION)));
        result.add(new Point(x - SWARM_SEPARATION / 2, y - (int) (sqrt(3)/2 * SWARM_SEPARATION)));
        result.add(new Point(x - SWARM_SEPARATION, y));
        
        return result;
    }*/
    
    private static int generateRandomness(int random) {
        return (int) (Math.random() * random - random * 2);
    }
}
