/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.game;

import evolith.engine.Assets;
import evolith.helpers.Commons;
import static evolith.helpers.Commons.MINIMAP_HEIGHT;
import static evolith.helpers.Commons.MINIMAP_WIDTH;
import static evolith.helpers.Commons.MINIMAP_X;
import static evolith.helpers.Commons.MINIMAP_Y;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Minimap implements Commons {
    private int width, height, x, y;
    private Game game;
    private Color organismsColor;
    
    /**
     * Initalizes the minimap with the parameters
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     */
    public Minimap(Game game) {
        this.game = game;
        x = MINIMAP_X;
        y = MINIMAP_Y;
        width = MINIMAP_WIDTH;
        height = MINIMAP_HEIGHT;
        organismsColor = new Color(0, 0, 0);
    }

    /**
     * To set the x of the minimap
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * To set the y of the minimap
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * To set the x of the minimap
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * To set the y of the minimap
     *
     * @return y
     */
    public int getY() {
        return y;
    }
    
    public void render(Graphics g) {
        g.drawImage(Assets.background, MINIMAP_X, MINIMAP_Y, MINIMAP_WIDTH, MINIMAP_HEIGHT, null);
        
        switch(game.getOrganismsSkin()) {
            case '0':
                organismsColor = PINK_COLOR;
                
                break;
            case '1':
                organismsColor = PURPLE_COLOR;
                
                break;
            case '2':
                organismsColor = BLUE_GREEN_COLOR;
                
                break;
            case '3':
                
                organismsColor = ORANGE_COLOR;
                
                break;
        }
        
        g.setColor(organismsColor);
        
        for (int i = 0; i < game.getOrganisms().getOrganismsPositions().size(); i++) {
            int organismPosX = (int) game.getOrganisms().getOrganismsPositions().get(i).getX() / 30;
            int organismPosY = (int) game.getOrganisms().getOrganismsPositions().get(i).getY() / 30;
            
            g.fillOval(MINIMAP_X + organismPosX, MINIMAP_Y + organismPosY, ORGANISM_SIZE / 10, ORGANISM_SIZE / 10);
        }
        
        g.drawRect(MINIMAP_X + game.getCamera().getX() / 30, MINIMAP_Y + game.getCamera().getY() / 30, game.getWidth() / 30 + 2, game.getHeight() / 30 + 2);
    }
}
