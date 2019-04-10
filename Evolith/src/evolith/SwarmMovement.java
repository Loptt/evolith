package evolith;

import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class SwarmMovement implements Commons {

    private static ArrayList<ArrayList<Point>> positions; //Array of arrays of points

    /**
     * Initalizes the arrays of arrays of points
     */
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
                //
                positions.get(i).add(new Point(currCol * (ORGANISM_SIZE - 20), currRow * (ORGANISM_SIZE - 20)));

                currCol++;
            }
        }
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
        //initalizes the swarm movement
        SwarmMovement.init();
        ArrayList<Point> customPosition = (ArrayList<Point>) positions.get(num - 1).clone();

        // adds a random number to the current position
        for (int i = 0; i < customPosition.size(); i++) {
            customPosition.get(i).x += x + generateRandomness(100);
            customPosition.get(i).y += y + generateRandomness(100);
        }

        return customPosition;
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
        SwarmMovement.init();

        ArrayList<Point> customPosition = (ArrayList<Point>) positions.get(num - 1 + obj).clone();

        for (int i = 0; i < customPosition.size(); i++) {
            customPosition.get(i).x += x + generateRandomness(100);
            customPosition.get(i).y += y + generateRandomness(100);
        }

        //remove middle point since new has been generated
        customPosition.remove(num / 2);

        return customPosition;
    }

    /**
     * To generate random number
     *
     * @param random
     * @return
     */
    private static int generateRandomness(int random) {
        return (int) (Math.random() * random - random * 2);
    }
}
