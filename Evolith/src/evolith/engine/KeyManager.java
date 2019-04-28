package evolith.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class KeyManager implements KeyListener {

    public boolean left;
    public boolean right;

    /**
     * Keys to be used in the game
     */
    public boolean w;
    public boolean a;
    public boolean s;
    public boolean d;
    public boolean p;
    public boolean esc;
    
    public boolean prevp;
    public boolean prevesc;

    protected boolean keys[];

    public KeyManager() {
        keys = new boolean[256];
    }

    /**
     * Not used
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Detect when a key has been pressed
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    /**
     * Detect when a key has been released
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    /**
     * To update the object each frame
     */
    public void tick() {
        w = keys[KeyEvent.VK_W];
        a = keys[KeyEvent.VK_A];
        s = keys[KeyEvent.VK_S];
        d = keys[KeyEvent.VK_D];
        
        if (keys[KeyEvent.VK_P]) {
            if (!prevp) {
                p = true;
                prevp = true;
            } else {
                p = false;
            }
        } else {
            prevp = false;
        }
    }
}
