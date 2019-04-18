/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hover;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author charles
 */
public class Display {
    private JFrame jframe;
    private Canvas canvas;
    
    private String title;
    private int width;
    private int height;
    
    /**
     * initializes the values for the application game
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
    
    /*
    * create the app and the canvas and add the canvas to the window app
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
    
    /*
    * to get the canvas of the game
    * @return the canvas
    */
    public Canvas getCanvas() {
        return canvas;
    }
    
    /**
     * To get jframe
     * @return 
     */
    public JFrame getJframe() {
        return jframe;
    }
}
