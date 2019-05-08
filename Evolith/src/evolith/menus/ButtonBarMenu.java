package evolith.menus;

import evolith.game.Game;
import evolith.engine.Assets;
import evolith.entities.OrganismManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */

public class ButtonBarMenu extends Menu {
    private BufferedImage background;           //asset of the background
    private boolean foodActive;                 //to determine if the button of food is active
    private boolean waterActive;                //to determine if the button of water is active
    private boolean fightActive;                //to determine if the button of fight is active
    
    /**
     * Constructor that initializes the values of its position, width and height
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game 
     */
    public ButtonBarMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        //sets the background to the button bar 
        background = Assets.buttonBar.get(0).get(0);
       
        buttons.add(new Button(34,31,141,55, Assets.buttonBar.get(1).get(1), Assets.buttonBar.get(1).get(0))); // Water button in pos 1
        buttons.add(new Button(191,31,141,55, Assets.buttonBar.get(2).get(1), Assets.buttonBar.get(2).get(0))); // Food  button in pos 2
        buttons.add(new Button(348,31,141,55, Assets.buttonBar.get(3).get(1), Assets.buttonBar.get(3).get(0))); // Fight  button in pos 3
        
        foodActive = false;     //set to false the status
        waterActive = false;    //set to false the status
        fightActive = false;    //set to false the status

    }
    // organism's behaviour when Water button is active
    public void activateWater(){
        waterActive = true;
    }
    // organism's behaviour when Food button is active 
    public void activateFood(){
        foodActive = true;
    }
    // organism's behaviour when Fight button is active
    public void activateFight(){
        fightActive = true;
    }
    /**
     * To tick the button bar menu in case of change in status
     */
    @Override
    public void tick() {
        //sets the orgnism manager
        OrganismManager orgs = game.getOrganisms();
        //updates the status of the actions
        foodActive = orgs.selectionHasActiveFood();
        waterActive = orgs.selectionHasActiveWater();
        fightActive = orgs.selectionHasAggressiveness();
        //changes the status of the actions of the buttons
        buttons.get(1).setActive(foodActive);
        buttons.get(0).setActive(waterActive);
        buttons.get(2).setActive(fightActive);
    }
    /**
     * To render the button bar menu
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        //to render the image of the background
        g.drawImage(background, x, y, width, height, null);
        //to render the buttons of food,water and fight
        for(int i=0; i<buttons.size(); i++){
            buttons.get(i).render(g);
        }
    }
    /**
     * To control the mouse with the left and right click
     * @param mouseX
     * @param mouseY 
     */
    public void applyMouse(int mouseX, int mouseY) {
        //changes the status if clicked
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).hasMouse(mouseX, mouseY)) {
                buttons.get(i).setActive(!buttons.get(i).isActive());
            }
        }
        // updates the status of the organisms if activated
        foodActive = buttons.get(1).isActive();
        waterActive = buttons.get(0).isActive();
        fightActive = buttons.get(2).isActive();
    }
    /**
     * To check if the food button is active
     * @return foodActive
     */
    public boolean isFoodActive() {
        return foodActive;
    }
    /**
     * To check if the water button is active
     * @return waterActive
     */
    public boolean isWaterActive() {
        return waterActive;
    }
    /**
     * To check if the fight button is active
     * @return fightActive
     */
    public boolean isFightActive() {
        return fightActive;
    }
    /**
     * To set the status of the food
     * @param foodActive 
     */
    public void setFoodActive(boolean foodActive) {
        this.foodActive = foodActive;
    }
    /**
     * To set the status of the water
     * @param waterActive 
     */
    public void setWaterActive(boolean waterActive) {
        this.waterActive = waterActive;
    }
    /**
     * To set the status of the fight
     * @param fightActive 
     */
    public void setFightActive(boolean fightActive) {
        this.fightActive = fightActive;
    }
}
