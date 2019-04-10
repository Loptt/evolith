
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
    
    public boolean delete;
    
    public boolean prevw;
    public boolean preva;
    public boolean prevs;
    public boolean prevd;
    
    public boolean prevb;
    public boolean prevc;
    public boolean preve;
    public boolean prevf;
    public boolean prevg;
    public boolean prevh;
    public boolean previ;
    public boolean prevj;
    public boolean prevk;
    public boolean prevl;
    public boolean prevm;
    public boolean prevn;
    public boolean prevo;
    public boolean prevp;
    public boolean prevq;
    public boolean prevr;
    public boolean prevt;
    public boolean prevu;
    public boolean prevv;
    public boolean prevx;
    public boolean prevy;
    public boolean prevz;
    
    public boolean prevdelete;
    
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
        if (keys[KeyEvent.VK_A]) {
            if (!preva) {
                a = true;
                preva = true;
            } else {
                a = false;
            }
        } else {
            preva = false;
        }
        
        if (keys[KeyEvent.VK_B]) {
            if (!prevb) {
                b = true;
                prevb = true;
            } else {
                b = false;
            }
        } else {
            prevb = false;
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
        
        if (keys[KeyEvent.VK_D]) {
            if (!prevd) {
                d = true;
                prevd = true;
            } else {
                d = false;
            }
        } else {
            prevd = false;
        }
        
        if (keys[KeyEvent.VK_E]) {
            if (!preve) {
                e = true;
                preve = true;
            } else {
                e = false;
            }
        } else {
            preve = false;
        }
        
        if (keys[KeyEvent.VK_F]) {
            if (!prevf) {
                f = true;
                prevf = true;
            } else {
                f = false;
            }
        } else {
            prevf = false;
        }
    }
}
