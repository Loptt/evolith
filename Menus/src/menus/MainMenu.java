/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public class MainMenu extends Menu{
    private boolean active;
    /* private Button play = new Button(300,350);
    private Button instructions = new Button(300,350);
    */
    private ArrayList<Button> buttons;
    private boolean clickPlay;


    public MainMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        active = true;
        clickPlay = false;

        buttons = new ArrayList<Button>();
        buttons.add(new Button(340,390,350,110)); // Play button
        buttons.add(new Button(340,530,350,110)); // Instructions button
  
    }
    
    public boolean isActive(){
        return active;
    }
    
    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isClickPlay() {
        return clickPlay;
    }

    public void setClickPlay(boolean clickPlay) {
        this.clickPlay = clickPlay;
    }
    
    
    
    @Override
    public void tick() {
        if(active){
        for(int i=0; i<buttons.size(); i++){
            if(((game.getMouseManager().getX()>buttons.get(i).getX() && game.getMouseManager().getX()<buttons.get(i).getX()+buttons.get(i).getWidth() && game.getMouseManager().getY()>buttons.get(i).getY() && game.getMouseManager().getY()<buttons.get(i).getY()+buttons.get(i).getHeight()))){
                //if the mouse is over the button
                buttons.get(i).setActive(true);
                if(game.getMouseManager().isIzquierdo()){
                    buttons.get(i).setPressed(true);
                    game.getMouseManager().setIzquierdo(false);
                }
            }
            else{
                buttons.get(i).setActive(false);
            }
            if(buttons.get(0).isPressed()){
                setClickPlay(true);
                setActive(false);
            }
        }
        }
    }

    @Override
    public void render(Graphics g) {
        if(active&&!buttons.get(0).isActive()&&!buttons.get(1).isActive()){
            g.drawImage(Assets.start, 0, 0, 1000, 700, null);
        }
        else if(active&&buttons.get(0).isActive()){
            g.drawImage(Assets.startPlay, 0, 0, 1000, 700, null);
            
        }
        else if(active&&buttons.get(1).isActive()){
            g.drawImage(Assets.startInstructions, 0, 0, 1000, 700, null);
        }
    }
    
    
}
