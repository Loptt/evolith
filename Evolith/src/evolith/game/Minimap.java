/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.game;

import evolith.engine.Assets;
import evolith.helpers.Commons;
import evolith.menus.Menu;
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
public class Minimap extends Menu implements Commons{
    private int width, height, x, y;
    private int relativeX, relativeY;
    //private Game game;
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
    public Minimap(int x, int y, int width, int height, Game game) {
        super(x,y,width,height,game);
        organismsColor = new Color(0, 0, 0);
    }
    
    public void applyMouse(int mouseX, int mouseY, Camera camera) {
        relativeX = mouseX - MINIMAP_X + MINIMAP_WIDTH;
        relativeY = mouseY - MINIMAP_Y + MINIMAP_HEIGHT;
        //System.out.println("relativeX: " + relativeX + " relativeY: " + relativeY);

        int XOutputStart = 0;
        int XOutputEnd = 4000; 
        int XInputStart = 170 + 500/30;
        int XInputEnd = 330 - 500/30;
        
        if(relativeX > (int)(330 - 500/30)){
            camera.setX(4000);
        }else if(relativeX < (int)(170 + 500/30)){
            camera.setX(0);
        }else{
            camera.setX(XOutputStart + ((XOutputEnd - XOutputStart) / (XInputEnd - XInputStart)) * (relativeX - XInputStart));
        }
        
        
        int YOutputStart = 0;
        int YOutputEnd = 4300; 
        int YInputStart = (170 + 350/30);
        int YInputEnd = (330 - 350/30);
        
        if(relativeY > (int)(330 - 350/30)){
            camera.setY(4300);
        }else if(relativeY < (int)(170 + 350/30)){
            camera.setY(0);
        }else{
            camera.setY(YOutputStart + ((YOutputEnd - YOutputStart) / (YInputEnd - YInputStart)) * (relativeY - YInputStart));
        }

        //limites en x de 0 a 4000
        //limites en y de 0 a 4300

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
        g.drawImage(game.getBackground().getCurrentFullBackground(), MINIMAP_X, MINIMAP_Y, MINIMAP_WIDTH, MINIMAP_HEIGHT, null);
        
        switch(game.getOrganismsSkin()) {
            case 0:
                organismsColor = PINK_COLOR;
                
                break;
            case 1:
                organismsColor = PURPLE_COLOR;
                
                break;
            case 2:
                organismsColor = BLUE_GREEN_COLOR;
                
                break;
            case 3:
                
                organismsColor = ORANGE_COLOR;
                
                break;
        }
        
        g.setColor(organismsColor);
        
        for (int i = 0; i < game.getOrganisms().getOrganismsPositions().size(); i++) {
            int organismPosX = (int) game.getOrganisms().getOrganismsPositions().get(i).getX() / 30;
            int organismPosY = (int) game.getOrganisms().getOrganismsPositions().get(i).getY() / 30;
            
            if (game.getOrganisms().getOrganism(i).isEgg()) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(organismsColor);
            }

            g.fillOval(MINIMAP_X + organismPosX, MINIMAP_Y + organismPosY, 4, 4);
        }
        
        for (int i = 0; i < game.getPredators().getPredatorAmount(); i++) {
            int predX = (int) game.getPredators().getPredator(i).getX() / 30;
            int predY = (int) game.getPredators().getPredator(i).getY() / 30;
            
            g.fillOval(MINIMAP_X + predX, MINIMAP_Y + predY, 30 / 10, 30 / 10);
        }
        g.setColor(Color.BLACK);
        g.drawRect(MINIMAP_X + game.getCamera().getX() / 30, MINIMAP_Y + game.getCamera().getY() / 30, game.getWidth() / 30 + 2, game.getHeight() / 30 + 2);
        g.drawImage(Assets.minimapFrame, MINIMAP_X, MINIMAP_Y, null);
    }

    @Override
    public void tick() {
    }
}
