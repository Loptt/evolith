
package evolith;

import java.awt.Graphics;

/**
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Clock extends Item implements Commons {
    
    private final Time time;

    public Clock(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.time = new Time();
    }

    /**
     * To tick the ticker per number of frames
     */
    public void tick()
    {
        time.tick();
        if( time.getTicker() % 60  == 0)
            System.out.println("Seconds Passed: " + time.getSeconds());
        
    }

    @Override
    public void render(Graphics g) {
       }
    
}
