/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodandwater;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author charles
 */
public class SwarmMovement implements Commons{
    
    private static ArrayList<ArrayList<Point>> positions; //Array of arrays of points
    
    public static void init() {
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
    }
    
    public static ArrayList<Point> getPositions(int x, int y, int num) {
        SwarmMovement.init();
        ArrayList<Point> customPosition = (ArrayList<Point>) positions.get(num - 1).clone();
        
        for (int i = 0; i < customPosition.size(); i++) {
            customPosition.get(i).x += x + generateRandomness(100);
            customPosition.get(i).y += y + generateRandomness(100);
        }
        
        return customPosition;
    }
    
    public static ArrayList<Point> getPositions(int x, int y, int num, int obj) {
        SwarmMovement.init();
        ArrayList<Point> customPosition = (ArrayList<Point>) positions.get(num - 1 + obj).clone();
        
        for (int i = 0; i < customPosition.size(); i++) {
            customPosition.get(i).x += x + generateRandomness(100);
            customPosition.get(i).y += y + generateRandomness(100);
        }
        
        customPosition.remove(num/2);
        
        return customPosition;
    }
    
    private static int generateRandomness(int random) {
        return (int) (Math.random() * random - random * 2);
    }
}
