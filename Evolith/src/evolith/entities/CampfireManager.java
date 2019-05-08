/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.game.Game;
import evolith.helpers.Commons;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class CampfireManager implements Commons {
    private ArrayList<Campfire> campfires;  //array of all organisms
    private int amount;                     //max organism amount

    private Game game;                      // game instance
    private int idCounter;
    
    private int currentMaxAmount;
    
    private boolean purged;
    
    public CampfireManager(Game game){
        this.game = game;
        campfires = new ArrayList<Campfire>();
        purged = false;
        currentMaxAmount = 5;
        idCounter = 0;
    }
    
    public void addCampfire(int x, int y){
        campfires.add(new Campfire(x, y, 250, 250, game));
    }
    
    public void tick(){
        if(campfires.size()>0){
            for(int i=0; i<campfires.size();i++){
                campfires.get(i).tick();
            }
            checkCampfires();
        }
    }
    
    public void checkCampfires(){
        if(campfires.size()>0){
            for(int i=0; i<campfires.size();i++){
                if(campfires.get(i).isOver()){
                    campfires.remove(i);
                }
            }
        }
    }
    public void turnOffCampfires(){
        if(campfires.size()>0){
            for(int i=0; i<campfires.size();i++){
                campfires.get(i).setOver(true);
            }
        }
    }
    
    public void render(Graphics g){
        if(campfires.size()>0){
            for(int i=0; i<campfires.size();i++){
                campfires.get(i).render(g);
            }
        }
    }
    
    
}
