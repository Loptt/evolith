package evolith;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Camera {

    private int width, height, x, y;
    private Game game;

    /**
     * Initalizes the camera with the parameters
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     */
    public Camera(int x, int y, int width, int height, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * To tick the camera depending on the keys
     */
    public void tick() {
        //To move the camera up
        if (game.getKeyManager().w) {
            //Limits the camera to the top end
            if (getY() - 5 <= 10) {
                setY(10);
            } else {
                setY(getY() - 5);
            }
        }
        //To move the camera to the left
        if (game.getKeyManager().a) {
            //Limits the camera to the left end
            if (getX() - 5 <= 10) {
                setX(10);
            } else {
                setX(getX() - 5);
            }
        }
        //To move the camera down
        if (game.getKeyManager().s) {
            //Limits the camera to the bottom end
            if (getY() + 5 >= game.getBackground().getHeight() - game.getHeight() - 10) {
                setY(game.getBackground().getHeight() - game.getHeight() - 10);
            } else {
                setY(getY() + 5);
            }
        }
        //To move the camera right
        if (game.getKeyManager().d) {
            //Limits the camera to the right end
            if (getX() + 5 >= game.getBackground().getWidth() - game.getWidth() - 10) {
                setX(game.getBackground().getWidth() - game.getWidth() - 10);
            } else {
                setX(getX() + 5);
            }
        }
    }

    /**
     * To get the relative x to the camera
     *
     * @param absX
     * @return absX - x
     */
    public int getRelX(int absX) {
        return absX - x;
    }

    /**
     * To get the relative y to the camera
     *
     * @param absY
     * @return absY - y
     */
    public int getRelY(int absY) {
        return absY - y;
    }

    /**
     * To get the absolute x to the camera
     *
     * @param relX
     * @return relX + x
     */
    public int getAbsX(int relX) {
        return relX + x;
    }

    /**
     * To get the absolute y to the camera
     *
     * @param relY
     * @return relY + y
     */
    public int getAbsY(int relY) {
        return relY + y;
    }

    /**
     * To set the x of the camera
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * To set the y of the camera
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * To set the x of the camera
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * To set the y of the camera
     *
     * @return y
     */
    public int getY() {
        return y;
    }
}
