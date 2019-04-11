package evolith.menus;

import evolith.game.Game;
import evolith.game.Item;
import evolith.engine.Assets;
import evolith.helpers.Commons;
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

    private Game game;      // runnable game
    private int food;       // food of the organism
    private int water;      //  water of the organism
    private int mat;        //maturity level of the organism

    /**
     * Contructor of the hover panel of the organism
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param food
     * @param water
     * @param mat
     * @param game
     */
    public Hover(int x, int y, int width, int height, int food, int water, int mat, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.food = food;
        this.water = water;
        this.mat = mat;
    }

    /**
     * To tick the hover
     */
    @Override
    public void tick() {
    }

    /**
     * To render the hover panel over an organism
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
/*
        //Outer frame of the panel
        g.setColor(new Color(137, 44, 152));
        g.fillRect(x, y, 200, 150);

        //Inner frame of the panel
        g.setColor(new Color(37, 198, 133));
        g.fillRect(x + 10, y + 10, 180, 130);

        // Font determined
        Font small = new Font("Helvetica", Font.BOLD, 14);
        g.setColor(new Color(197, 56, 218));
        g.setFont(small);*/

        g.drawImage(Assets.hoverImage, x, y, width, height, null);

//  Hunger information 
        /*g.drawString("Hunger:", x + 25, y + 30);
        //draws outer rectangle
        g.setColor(Color.white);
        g.drawRect(x + 25, y + 40, 120, 10);*/
        //displays the hunger depending on the food given
        g.setColor(Color.green);
        g.fillRect(x + 24, y + 58, (int) 118 * this.food / MAX_HUNGER, 12);

//  Thirst information
        /*g.setColor(new Color(197, 56, 218));
        g.drawString("Thirst:", x + 25, y + 70);
        //draws outer rectangle
        g.setColor(Color.white);
        g.drawRect(x + 25, y + 80, 120, 10);*/
        //displays the thirst depending on the water given
        g.setColor(Color.blue);
        g.fillRect(x + 24, y + 115, (int) 118 * this.water / MAX_THIRST, 12);

//  Maturity Information
        /*g.setColor(new Color(197, 56, 218));
        g.drawString("Maturity:", x + 25, y + 110);
        //draws outer rectangle
        g.setColor(Color.white);
        g.drawRect(x + 25, y + 120, 120, 10);*/
        //displays the maturity depending on the level
        g.setColor(Color.red);
        g.fillRect(x + 24, y + 174, (int) 118 * this.mat / MAX_MATURITY, 12);
        
        g.drawImage(Assets.hoverImage, x, y, width, height, null);
    }
}
