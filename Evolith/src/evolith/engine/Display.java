package evolith.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Display {

    private JFrame jframe;
    private Canvas canvas;

    private String title;   // The title of the display
    private int width;      // The width of the display
    private int height;     // The height of the display

    /**
     * Initializes the values for the application game
     *
     * @param title to display the title of the window
     * @param width to set the width
     * @param height to set the height
     */
    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    /**
     * Create the app and the canvas and add the canvas to the window app
     */
    public void createDisplay() {
        jframe = new JFrame(title);

        jframe.setSize(width, height);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        jframe.add(canvas);
        jframe.pack();

    }

    /**
     * To get the canvas of the game
     * 
     * @return canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * To get the jframe of the game
     *
     * @return jframe
     */
    public JFrame getJframe() {
        return jframe;
    }
}
