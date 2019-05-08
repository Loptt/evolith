package evolith.menus;

import evolith.game.Game;
import evolith.game.Item;
import evolith.engine.Assets;
import evolith.entities.Organism;
import evolith.helpers.Commons;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Hover extends Item implements Commons {

    private Game game;          //  runnable game
    private int food;           //  food of the organism
    private int water;          //  water of the organism
    private double life;        //  maturity level of the organism
    private int maxHealth;      // max health of the organism
    private int stealthX;       // stealth position x circumference
    private int stealthY;       // stealth position y circumference
    private int stealthRange;   // stealth range of the organism
    private String name;        //  name of the organism

    /**
     * Constructor of the hover
     * @param x
     * @param y
     * @param width
     * @param height
     * @param food
     * @param water
     * @param life
     * @param maxHealth
     * @param game
     * @param org 
     */
    public Hover(int x, int y, int width, int height, int food, int water, double life, int maxHealth, Game game, Organism org) {
        super(x, y, width, height);
        this.game = game;
        this.food = food;
        this.water = water;
        this.life = life;
        this.maxHealth = maxHealth;
        //sets the stealth range
        stealthRange = org.getStealthRange();
        //sets the position of the stealth range
        stealthX = org.getX() - stealthRange + org.getWidth() / 2;
        stealthY = org.getY() - stealthRange + org.getHeight() / 2;
        //updates the name
        name = org.getName();
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
        g.drawImage(Assets.hoverImage, x, y, width, height, null);
        g.drawImage(Assets.hoverImage, x, y, width, height, null);

        //  Hunger information 
        g.setColor(Color.green);
        g.fillRect(x + 42, y + 57, (int) 87 * this.food / MAX_HUNGER, 20);

        //  Thirst information
        g.setColor(Color.blue);
        g.fillRect(x + 42, y + 110, (int) 87 * this.water / MAX_THIRST, 20);

        //  Maturity Information
        g.setColor(Color.red);
        g.fillRect(x + 42, y + 167, (int) (87 * (this.life / maxHealth)), 20);
        
        g.setColor(new Color(88,241,252, 20));
        g.fillOval(game.getCamera().getRelX(stealthX) , game.getCamera().getRelY(stealthY), stealthRange*2, stealthRange*2);
        g.setColor(new Color(88,241,252));
        g.drawOval(game.getCamera().getRelX(stealthX) , game.getCamera().getRelY(stealthY), stealthRange*2, stealthRange*2);
       
        //  Name Information      
        g.setColor(Color.white);
        //calculate the length of the string's name
        int widthName = g.getFontMetrics().stringWidth(name);
        g.drawString(name, x + width/ 2 - widthName / 2, y + 23);
    }
    
}
