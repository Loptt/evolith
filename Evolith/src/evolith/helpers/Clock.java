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
    
    public int getTicker() {
        return time.getTicker();
    }
    
    public void setTicker(int tick) {
        time.setTicker(tick);
    }

    /**
     * To tick the ticker per number of frames
     */
    public void tick() {
        //ticks the time
        time.tick();

        //displays the actual time in the terminal
        if (time.getTicker() % 60 == 0) {
            //System.out.println("Seconds Passed: " + time.getSeconds());
        }
    }

    //to render the clock in the screen
    @Override
    public void render(Graphics g) {

    }
    
    public int getSeconds() {
        return (int) time.getSeconds();
    }

}
