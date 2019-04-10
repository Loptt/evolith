package evolith;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class MouseManager implements MouseListener, MouseMotionListener {

    private boolean izquierdo;
    private boolean derecho;
    private int x;
    private int y;

    /**
     * Constructor of the mouse manager
     */
    public MouseManager() {
    }

    /**
     * Determines if the mouse is clicked
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Determines if the mouse button is pressed
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // if the right button of the mouse is pressed
        if (e.getButton() == MouseEvent.BUTTON1) {
            izquierdo = true;
            x = e.getX();
            y = e.getY();
        }
    }

    /**
     * Determines if the mouse button is released
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // if the right button of the mouse is released
        if (e.getButton() == MouseEvent.BUTTON1) {
            izquierdo = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Determines if the mouse is dragged and updates the x and y of the mouse
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (true) {
            izquierdo = true;
            x = e.getX();
            y = e.getY();
        }
    }

    /**
     * Updates the x and y of the mouse
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (true) {
            x = e.getX();
            y = e.getY();
        }
    }

    /**
     * To get the x of the mouse
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * To get the y of the mouse
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * To get the status of the left button
     *
     * @return izquierdo
     */
    public boolean isIzquierdo() {
        return izquierdo;
    }

    /**
     * To get the status of the right button
     *
     * @return derecho
     */
    public boolean isDerecho() {
        return derecho;
    }

    /**
     * To set the status of the left button
     *
     * @param izquierdo
     */
    public void setIzquierdo(boolean izquierdo) {
        this.izquierdo = izquierdo;
    }
}
