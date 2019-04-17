/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.game.Game;
import evolith.helpers.Commons;
import java.util.ArrayList;

/**
 *
 * @author ErickFrank
 */
public class MutationManager implements Commons{
    
    private ArrayList<ArrayList<Mutation>> mutations;
    private Game game;
    private int amount;

    public MutationManager() {
        mutations.get(1).add(new Mutation("Spine",20,0,0,0,true,1,0,0,0,0,game));
        mutations.get(1).add(new Mutation("Sting",40,-20,0,20,true,2,0,0,0,0,game));
        mutations.get(1).add(new Mutation("Claws",60,-20,0,0,true,3,0,0,0,0,game));
        mutations.get(1).add(new Mutation("Horns",80,-20,0,-40,true,4,0,0,0,0,game));
        
        
        mutations.get(2).add(new Mutation("2 legs",0,20,0,0,true,1,0,0,0,0,game));
        mutations.get(2).add(new Mutation("4 legs",0,40,0,0,true,2,0,0,0,0,game));
        mutations.get(2).add(new Mutation("6 legs",0,60,-20,0,true,3,0,0,0,0,game));
        mutations.get(2).add(new Mutation("Wings",-20,80,-40,20,true,4,0,0,0,0,game));
        
        
        mutations.get(3).add(new Mutation("Medium Size",0,0,20,0,true,1,0,0,0,0,game));
        mutations.get(3).add(new Mutation("Big Size",20,-20,40,-20,true,2,0,0,0,0,game));
        mutations.get(3).add(new Mutation("Shell",-20 ,-40,80,0,true,3,0,0,0,0,game));
        
        
        mutations.get(4).add(new Mutation("Ears",0,0,0,20,true,1,0,0,0,0,game));
        mutations.get(4).add(new Mutation("Stripes",0,0,0,40,true,2,0,0,0,0,game));
        
        
    };

    };
    
   
