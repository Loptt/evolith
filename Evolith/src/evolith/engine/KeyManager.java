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
    public boolean g;
    public boolean c;
    public boolean esc;
    
    public boolean num1;
    public boolean num2;
    public boolean num3;
    
    public boolean prevp;
    public boolean prevesc;
    public boolean prevnum1;
    public boolean prevnum2;
    public boolean prevnum3;
    public boolean prevg;
    public boolean prevc;

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
        
        if (keys[KeyEvent.VK_ESCAPE]) {
            if (!prevesc) {
                esc = true;
                prevesc = true;
            } else {
                esc = false;
            }
        } else {
            prevesc = false;
        }
        
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
        
        if (keys[KeyEvent.VK_1]) {
            if (!prevnum1) {
                num1 = true;
                prevnum1 = true;
            } else {
                num1 = false;
            }
        } else {
            prevnum1 = false;
        }
        
        if (keys[KeyEvent.VK_2]) {
            if (!prevnum2) {
                num2 = true;
                prevnum2 = true;
            } else {
                num2 = false;
            }
        } else {
            prevnum2 = false;
        }
        
        if (keys[KeyEvent.VK_3]) {
            if (!prevnum3) {
                num3 = true;
                prevnum3 = true;
            } else {
                num3 = false;
            }
        } else {
            prevnum3 = false;
        }
        
        if (keys[KeyEvent.VK_G]) {
            if (!prevg) {
                g = true;
                prevg = true;
            } else {
                g = false;
            }
        } else {
            prevg = false;
        }
        
        if (keys[KeyEvent.VK_C]) {
            if (!prevc) {
                c = true;
                prevc = true;
            } else {
                c = false;
            }
        } else {
            prevc = false;
        }
    }
}
