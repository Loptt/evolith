/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orgmove;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author charles
 */
public class SwarmMovement implements Commons{
    
    private static ArrayList<ArrayList<Point>> positions;
    
    public static void init() {
        positions = new ArrayList<>();
        int colLimit = 0;
        int currRow = 0;
        int currCol = 0;
        
        for (int i = 0; i < 10; i++) {
            
            positions.add(new ArrayList<>());
            colLimit = (int) Math.sqrt(i) + 1;
            //System.out.println(colLimit);
            
            currCol = 0;
            currRow = 0;
            
            for (int j = 0; j < i + 1; j++) {
                if (currCol >= colLimit) {
                    currRow++;
                    currCol = 0;
                }
                /*
                System.out.print(Integer.toString(currCol) + "--");
                System.out.print(currRow);
                System.out.print("     ");*/
                
                positions.get(i).add(new Point(currCol * ORGANISM_SIZE, currRow * ORGANISM_SIZE));
                
                currCol++;
            }
            
            //System.out.println();
        }
    }
    
    public static ArrayList<Point> getPositions(int x, int y, int num) {
        ArrayList<Point> customPosition = positions.get(num - 1);
        
        for (int i = 0; i < customPosition.size(); i++) {
            customPosition.get(i).x += x;
            customPosition.get(i).y += y;
        }
        
        return customPosition;
    }
}
