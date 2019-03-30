
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author charles
 */
public class KeyManager implements KeyListener {
    
    public boolean left;
    public boolean right;
    
    /**
     * Keys to be used in the game
     */
    public boolean p;
    public boolean r;
    public boolean g;
    public boolean c;
    public boolean space;
    public boolean enter;
    
    private boolean keys[];
    
    private boolean prevp;
    private boolean prevg;
    private boolean prevc;
    private boolean prevr;
    
    public KeyManager() {
        keys = new boolean[256];
        prevp = false;
    }
    
    /**
     * Not used
     * @param e 
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    /**
     * Detect when a key has been pressed
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    
    /**
     * Detect when a key has been released
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
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        
        space = keys[KeyEvent.VK_SPACE];
        enter = keys[KeyEvent.VK_ENTER];
        
        r = keys[KeyEvent.VK_R];
        
        
        /**
         * The following set of if statements prevent the key being triggered multiple
         * consecutive times without releasing the key
         */
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
        
        /**
         * Same for this
         */
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
        
        /**
         * Same for this
         */
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
        
        /**
         * Same for this
         */        
        if (keys[KeyEvent.VK_R]) {
            if (!prevr) {
                r = true;
                prevr = true;
            } else {
                r = false;
            }
        } else {
            prevr = false;
        }
  
    }
}
