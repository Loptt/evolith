package evolith.helpers;

import evolith.game.Item;
import evolith.helpers.Commons;
import java.awt.Graphics;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Clock extends Item implements Commons {

    private final Time time; // time variable to determine the game time

    /**
     * Constructor of the clock with x,y, width and height
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Clock(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.time = new Time();
    }
    
    /**
     * To get the ticker
     * 
     * @return ticker
     */
    public long getTicker() {
        return time.getTicker();
    }
    
    /**
     * To set the ticker
     * 
     * @param tick
     */
    public void setTicker(long tick) {
        time.setTicker(tick);
    }

    /**
     * To tick the ticker per number of frames
     */
    public void tick() {
        // Ticks the time
        time.tick();

        // Displays the actual time in the terminal
        if (time.getTicker() % 60 == 0) {
            //System.out.println("Seconds Passed: " + time.getSeconds());
        }
    }

    // To render the clock in the screen
    @Override
    public void render(Graphics g) {

    }
    
    /**
     * To return the number of seconds
     * 
     * @return seconds
     */
    public int getSeconds() {
        return (int) time.getSeconds();
    }

}
