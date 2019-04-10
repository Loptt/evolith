
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

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
    public boolean w;
    public boolean a;
    public boolean s;
    public boolean d;
    
    public boolean b;
    public boolean c;
    public boolean e;
    public boolean f;
    public boolean g;
    public boolean h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;
    public boolean r;
    public boolean t;
    public boolean u;
    public boolean v;
    public boolean x;
    public boolean y;
    public boolean z;
    
    private boolean keys[];

    public KeyManager() {
        keys = new boolean[256];
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
        w = keys[KeyEvent.VK_W];
        a = keys[KeyEvent.VK_A];
        s = keys[KeyEvent.VK_S];
        d = keys[KeyEvent.VK_D];
        
        b = keys[KeyEvent.VK_B];
        c = keys[KeyEvent.VK_C];
        e = keys[KeyEvent.VK_E];
        f = keys[KeyEvent.VK_F];
        g = keys[KeyEvent.VK_G];
        h = keys[KeyEvent.VK_H];
        i = keys[KeyEvent.VK_I];
        j = keys[KeyEvent.VK_J];
        k = keys[KeyEvent.VK_K];
        l = keys[KeyEvent.VK_L];
        m = keys[KeyEvent.VK_M];
        n = keys[KeyEvent.VK_N];
        o = keys[KeyEvent.VK_O];
        p = keys[KeyEvent.VK_P];
        q = keys[KeyEvent.VK_Q];
        r = keys[KeyEvent.VK_R];
        t = keys[KeyEvent.VK_T];
        u = keys[KeyEvent.VK_U];
        v = keys[KeyEvent.VK_V];
        x = keys[KeyEvent.VK_X];
        y = keys[KeyEvent.VK_Y];
        z = keys[KeyEvent.VK_Z];
    }
}
