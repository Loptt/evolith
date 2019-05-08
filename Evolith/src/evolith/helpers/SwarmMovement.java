package evolith.helpers;

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
    
    private static final int randomness = 70; //The amount of randomness in the positions
    
    /**
     * To generate an hexagonal pattern to arrange all organisms in a swarm.
     * 
     * @param n the amount of organisms
     * @return an <code>ArrayList
     * </code> with all the points generated
     */
    public static ArrayList<Point> generateHexPattern(int n) {
        //If n is 0 or less, it is not possible to generate pattern
        if (n < 1) {
            System.out.println("GAME OVER");
            return null;
        }
        
        //Create the array of the positions
        ArrayList<Point> positions = new ArrayList<>();
        
        //First position will always be on the origin
        positions.add(new Point(0, 0));
        
        //Create two helper points, left and right which go respective to the origin point
        Point left = new Point(0, 0);
        Point right = new Point(0, 0);
        
        //Create other two helper points, top and bottom which go respective to the origin point
        Point top = new Point(0, 0);
        Point bottom = new Point(0, 0);
        
        /**
         * The amount of layers means the amount of points there exists from the origin to the outer most point
         * going perpendicular to the outer shell
         * It is given by 3 + the square root of 9 - 12 * (1 - n) all divided by 6, and we ceil the result, where
         * n is the amount of points to generate
         * This formula was derived from the quadratic sequence of 1, 7, 19, 37 ....
         */
        int layerAmount = (int) Math.ceil((3 + sqrt(9-12*(1-n))) / 6);
        
        /**
         * Iterate for one less than the layer amount since we already have the first layer which is (0, 0)
         */
        for (int i = 0; i < layerAmount-1; i++) {
            //Generate left and right
            left = new Point(left.x - SWARM_SEPARATION, left.y);
            right = new Point(right.x + SWARM_SEPARATION, right.y);
            
            //Add them to the array
            positions.add(left);
            positions.add(right);
            
            /**
             * Iterate for one more than the current i to build the layer up and bottom, taking  left and right as bases
             */
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
            
            /**
             * Iterate over the top and bottom to fill the spaces
             */
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
            //Add a randomness to give a more natura feel
            positions.get(i).x += x + generateRandomness(randomness);
            positions.get(i).y += y + generateRandomness(randomness);
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
                positions.get(i).x += x + generateRandomness(randomness);
                positions.get(i).y += y + generateRandomness(randomness);
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
     * To generate random number from -random/2 to +random/2
     *
     * @param random
     * @return
     */
    public static int generateRandomness(int random) {
        return (int) (Math.random() * random - random / 2);
    }
    
    /**
     * To get the distance between two coordinates
     * @param x1 x from first coordinate
     * @param y1 y from first coordinate
     * @param x2 x from second coordinate
     * @param y2 y from second coordinate
     * @return distance
     */
    public static double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
    }
}
