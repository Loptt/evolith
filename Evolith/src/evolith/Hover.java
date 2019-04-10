
package evolith;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Hover extends Item implements Commons {
    
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

    
    g.setColor(new Color(137, 44, 152));
    g.fillRect(x, y, 200 , 150);
    
    
    g.setColor(new Color(37, 198, 133));
    g.fillRect(x+10, y+10, 180 , 130);
    
    
    Font small = new Font("Helvetica", Font.BOLD, 14);
       
    g.setColor(new Color(197, 56, 218));
    g.setFont(small);
    
//Hunger    
    g.drawString("Hunger:", x + 25, y + 30 );
    
    
    g.setColor(Color.white);
    g.drawRect(x + 25, y + 40 , 120 , 10);
    
    g.setColor(Color.green);
    g.fillRect(x + 26, y + 41, (int) 118 * this.food/MAX_HUNGER , 8);
    
//Thirst
    g.setColor(new Color(197, 56, 218));
    g.drawString("Thirst:", x + 25, y + 70 );
    
    g.setColor(Color.white);
    g.drawRect(x +25, y + 80 , 120 , 10);
    
    g.setColor(Color.blue);
    g.fillRect(x + 26, y + 81, (int) 118 * this.water/MAX_THIRST , 8);
//Maturity     
    g.setColor(new Color(197, 56, 218));
    g.drawString("Maturity:", x + 25, y + 110);
    
    
    g.setColor(Color.white);
    g.drawRect(x +25, y + 120, 120 , 10);
    
     g.setColor(Color.red);
    g.fillRect(x + 26, y + 121, (int) 118 * this.mat/MAX_MATURITY , 8);
}
}
   
