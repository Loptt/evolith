/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.engine;

import java.awt.event.KeyEvent;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class InputKeyboard extends KeyManager {
    private boolean keys[];
    
    /**
     * Keys to be used to type in the game
     */
    public boolean a;
    public boolean b;
    public boolean c;
    public boolean d;
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
    public boolean s;
    public boolean t;
    public boolean u;
    public boolean v;
    public boolean w;
    public boolean x;
    public boolean y;
    public boolean z;
    public boolean num1;
    public boolean num2;
    public boolean num3;
    public boolean num4;
    public boolean num5;
    public boolean num6;
    public boolean num7;
    public boolean num8;
    public boolean num9;
    public boolean num0;
    public boolean period;
    
    public boolean delete;
    
    public boolean preva;
    public boolean prevb;
    public boolean prevc;
    public boolean prevd;
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
    public boolean prevs;
    public boolean prevt;
    public boolean prevu;
    public boolean prevv;
    public boolean prevw;
    public boolean prevx;
    public boolean prevy;
    public boolean prevz;
    public boolean prevnum1;
    public boolean prevnum2;
    public boolean prevnum3;
    public boolean prevnum4;
    public boolean prevnum5;
    public boolean prevnum6;
    public boolean prevnum7;
    public boolean prevnum8;
    public boolean prevnum9;
    public boolean prevnum0;
    public boolean prevperiod;
    
    public boolean prevdelete;
    
    public InputKeyboard() {
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
        
        if (keys[KeyEvent.VK_H]) {
            if (!prevh) {
                h = true;
                prevh = true;
            } else {
                h = false;
            }
        } else {
            prevh = false;
        }
        
        if (keys[KeyEvent.VK_I]) {
            if (!previ) {
                i = true;
                previ = true;
            } else {
                i = false;
            }
        } else {
            previ = false;
        }
        
        if (keys[KeyEvent.VK_J]) {
            if (!prevj) {
                j = true;
                prevj = true;
            } else {
                j = false;
            }
        } else {
            prevj = false;
        }
        
        if (keys[KeyEvent.VK_K]) {
            if (!prevk) {
                k = true;
                prevk = true;
            } else {
                k = false;
            }
        } else {
            prevk = false;
        }
        
        if (keys[KeyEvent.VK_L]) {
            if (!prevl) {
                l = true;
                prevl = true;
            } else {
                l = false;
            }
        } else {
            prevl = false;
        }
        
        if (keys[KeyEvent.VK_M]) {
            if (!prevm) {
                m = true;
                prevm = true;
            } else {
                m = false;
            }
        } else {
            prevm = false;
        }
        
        if (keys[KeyEvent.VK_N]) {
            if (!prevn) {
                n = true;
                prevn = true;
            } else {
                n = false;
            }
        } else {
            prevn = false;
        }
        
        if (keys[KeyEvent.VK_O]) {
            if (!prevo) {
                o = true;
                prevo = true;
            } else {
                o = false;
            }
        } else {
            prevo = false;
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
        
        if (keys[KeyEvent.VK_Q]) {
            if (!prevq) {
                q = true;
                prevq = true;
            } else {
                q = false;
            }
        } else {
            prevq = false;
        }
        
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
        
        if (keys[KeyEvent.VK_S]) {
            if (!prevs) {
                s = true;
                prevs = true;
            } else {
                s = false;
            }
        } else {
            prevs = false;
        }
        
        if (keys[KeyEvent.VK_T]) {
            if (!prevt) {
                t = true;
                prevt = true;
            } else {
                t = false;
            }
        } else {
            prevt = false;
        }
        
        if (keys[KeyEvent.VK_U]) {
            if (!prevu) {
                u = true;
                prevu = true;
            } else {
                u = false;
            }
        } else {
            prevu = false;
        }
        
        if (keys[KeyEvent.VK_V]) {
            if (!prevv) {
                v = true;
                prevv = true;
            } else {
                v = false;
            }
        } else {
            prevv = false;
        }
        
        if (keys[KeyEvent.VK_W]) {
            if (!prevw) {
                w = true;
                prevw = true;
            } else {
                w = false;
            }
        } else {
            prevw = false;
        }
        
        if (keys[KeyEvent.VK_X]) {
            if (!prevx) {
                x = true;
                prevx = true;
            } else {
                x = false;
            }
        } else {
            prevx = false;
        }
        
        if (keys[KeyEvent.VK_Y]) {
            if (!prevy) {
                y = true;
                prevy = true;
            } else {
                y = false;
            }
        } else {
            prevy = false;
        }
        
        if (keys[KeyEvent.VK_Z]) {
            if (!prevz) {
                z = true;
                prevz = true;
            } else {
                z = false;
            }
        } else {
            prevz = false;
        }
        
        if (keys[KeyEvent.VK_BACK_SPACE]) {
            if (!prevdelete) {
                delete = true;
                prevdelete = true;
            } else {
                delete = false;
            }
        } else {
            prevdelete = false;
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
        
        if (keys[KeyEvent.VK_4]) {
            if (!prevnum4) {
                num4 = true;
                prevnum4 = true;
            } else {
                num4 = false;
            }
        } else {
            prevnum4 = false;
        }
        
        if (keys[KeyEvent.VK_5]) {
            if (!prevnum5) {
                num5 = true;
                prevnum5 = true;
            } else {
                num5 = false;
            }
        } else {
            prevnum5 = false;
        }
        if (keys[KeyEvent.VK_6]) {
            if (!prevnum6) {
                num6 = true;
                prevnum6 = true;
            } else {
                num6 = false;
            }
        } else {
            prevnum6 = false;
        }
        if (keys[KeyEvent.VK_7]) {
            if (!prevnum7) {
                num7 = true;
                prevnum7 = true;
            } else {
                num7 = false;
            }
        } else {
            prevnum7 = false;
        }
        if (keys[KeyEvent.VK_8]) {
            if (!prevnum8) {
                num8 = true;
                prevnum8 = true;
            } else {
                num8 = false;
            }
        } else {
            prevnum8 = false;
        }
        if (keys[KeyEvent.VK_9]) {
            if (!prevnum9) {
                num9 = true;
                prevnum9 = true;
            } else {
                num9 = false;
            }
        } else {
            prevnum9 = false;
        }
        if (keys[KeyEvent.VK_0]) {
            if (!prevnum0) {
                num0 = true;
                prevnum0 = true;
            } else {
                num0 = false;
            }
        } else {
            prevnum0 = false;
        }
        if (keys[KeyEvent.VK_PERIOD]) {
            if (!prevperiod) {
                period = true;
                prevperiod = true;
            } else {
                period = false;
            }
        } else {
            prevperiod = false;
        }
    }
}
