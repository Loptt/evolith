package evolith;

import java.awt.Point;
import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class SwarmMovement implements Commons {
    
    public static ArrayList<Point> generateHexPattern(int n) {
        if (n < 1) {
            return null;
        }
        
        ArrayList<Point> positions = new ArrayList<>();
        positions.add(new Point(0, 0));
        
        Point left = new Point(0, 0);
        Point right = new Point(0, 0);
        
        Point top = new Point(0, 0);
        Point bottom = new Point(0, 0);
        
        int layerAmount = (int) Math.ceil((3 + sqrt(9-12*(1-n))) / 6);
        
        for (int i = 0; i < layerAmount-1; i++) {
            //Generate left and right
            left = new Point(left.x - SWARM_SEPARATION, left.y);
            right = new Point(right.x + SWARM_SEPARATION, right.y);
            
            positions.add(left);
            positions.add(right);
            
            for (int j = 0; j < i+1; j++) {
                //Add top left
                positions.add(new Point(left.x + SWARM_SEPARATION/2 * (j+1), left.y + (int) (sqrt(3)/2 * SWARM_SEPARATION) * (j+1)));
                //Add bottom left
                positions.add(new Point(left.x + SWARM_SEPARATION/2 * (j+1), left.y - (int) (sqrt(3)/2 * SWARM_SEPARATION) * (j+1)));
                //Add top right
                positions.add(new Point(right.x - SWARM_SEPARATION/2 * (j+1), left.y + (int) (sqrt(3)/2 * SWARM_SEPARATION) * (j+1)));
                //Add bottom right
                positions.add(new Point(right.x - SWARM_SEPARATION/2 * (j+1), left.y - (int) (sqrt(3)/2 * SWARM_SEPARATION) * (j+1)));
                
                if (j == i) {
                    top = positions.get(positions.size()-4);
                    bottom = positions.get(positions.size()-3);
                }
            }
            
            for (int j = 0; j < i; j++) {
                positions.add(new Point(top.x + SWARM_SEPARATION * (j+1), top.y));
                positions.add(new Point(bottom.x + SWARM_SEPARATION * (j+1), bottom.y));
            }
        }
        
        return positions;
    }

    /**
     * To get the positions of the array and update it randomly
     *
     * @param x
     * @param y
     * @param num
     * @return customPosition
     */
    public static ArrayList<Point> getPositions(int x, int y, int num) {
        ArrayList<Point> positions = generateHexPattern(num);
        
        for (int i = 0; i < positions.size(); i++) {
            positions.get(i).x += x + generateRandomness(80);
            positions.get(i).y += y + generateRandomness(80);
        }
        
        return positions;
    }

    /**
     * To simulate the swarm movement of the points given
     *
     * @param x
     * @param y
     * @param num
     * @param obj
     * @return
     */
    public static ArrayList<Point> getPositions(int x, int y, int num, int obj) {
        ArrayList<Point> positions = generateHexPattern(num+obj);
        
        for (int i = 0; i < positions.size(); i++) {
            
            if (i > 6) {
                positions.get(i).x += x + generateRandomness(80);
                positions.get(i).y += y + generateRandomness(80);
            } else {
                positions.get(i).x += x + generateRandomness(0);
                positions.get(i).y += y + generateRandomness(0);
            }
            
        }
        
        for (int i = 0; i < obj; i++) {
            positions.remove(i);
        }
        
        return positions;
    }

    /**
     * To generate random number
     *
     * @param random
     * @return
     */
    private static int generateRandomness(int random) {
        return (int) (Math.random() * random - random / 2);
    }
}
