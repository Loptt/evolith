/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hover;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *
 * @author charles
 */
public class Hover extends Item {
    
    private Game game;
    int food, water, mat;

    public Hover(int x, int y, int width, int height, int food, int water, int mat,Game game) {
        super(x, y, width, height);
        this.game = game;
        this.food = food;
        this.water = water;
        this.mat = mat;
    }

    @Override
    public void tick() {
    }
    
    @Override
    public void render(Graphics g) {
    String food = Integer.toString(this.food);
    String thirst = Integer.toString(this.water);
    String maturity = Integer.toString(this.mat);
    g.setColor(Color.white);
    g.fillRect(x, y, 200 , 150);
    g.setColor(new Color(3, 214, 210));
    g.fillRect(x+10, y+10, 180 , 130);
    Font small = new Font("Helvetica", Font.BOLD, 14);
    
    FontMetrics metr = game.getDisplay().getJframe().getFontMetrics(small);
    g.setColor(new Color(197, 56, 218));
    g.setFont(small);
    g.drawString("Hunger:   " + food, x + 40, y + 40 );
    g.drawString("Thirst:   " + thirst, x + 40, y + 70 );
    g.drawString("Maturity:   " + maturity, x + 40, y + 100 );

   

}
}
   
