/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.engine.Assets;
import evolith.game.Game;
import evolith.helpers.Commons;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author ErickFrank
 */
public class MutationManager implements Commons{

    private ArrayList<ArrayList<Mutation>> mutations = new ArrayList<ArrayList<Mutation>>();
    private Game game;

<<<<<<< HEAD
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

    }

    public ArrayList<ArrayList<Mutation>> getMutations() {
        return mutations;
    }

    public void setMutations(ArrayList<ArrayList<Mutation>> mutations) {
        this.mutations = mutations;
    }
    
=======
    public MutationManager(Game game) {
        this.game = game;
        
        ArrayList<Mutation> m1 = new ArrayList<Mutation>();
        m1.add(new Mutation("Spine",20,0,0,0,false,1,0,0,0,0,game));
        m1.add(new Mutation("Sting",40,-20,0,20,false,2,0,0,0,0,game));
        m1.add(new Mutation("Claws",60,-20,0,0,false,3,0,0,0,0,game));
        m1.add(new Mutation("Horns",80,-20,0,-40,false,4,0,0,0,0,game));
        mutations.add(m1);
    
        ArrayList<Mutation> m2 = new ArrayList<Mutation>();
        m2.add(new Mutation("2 legs",0,20,0,0,false,1,0,0,0,0,game));
        m2.add(new Mutation("4 legs",0,40,0,0,false,2,0,0,0,0,game));
        m2.add(new Mutation("6 legs",0,60,-20,0,false,3,0,0,0,0,game));
        m2.add(new Mutation("Wings",-20,80,-40,20,false,4,0,0,0,0,game));
        mutations.add(m2);
        
        ArrayList<Mutation> m3 = new ArrayList<Mutation>();
        m3.add(new Mutation("Medium Size",0,0,20,0,false,1,0,0,0,0,game));
        m3.add(new Mutation("Big Size",20,-20,40,-20,false,2,0,0,0,0,game));
        m3.add(new Mutation("Shell",-20 ,-40,80,0,false,3,0,0,0,0,game));
        mutations.add(m3);
        
        ArrayList<Mutation> m4 = new ArrayList<Mutation>();
        m4.add(new Mutation("Ears",0,0,0,20,false,1,0,0,0,0,game));
        m4.add(new Mutation("Stripes",0,0,0,40,false,2,0,0,0,0,game));
        mutations.add(m4);
        
        for(int i=0; i<4; i++){
            for(int j=0; j<mutations.get(i).size(); j++){
                mutations.get(i).get(j).setSprite(Assets.mutations.get(i).get(j));
            }
        }
        
    };
>>>>>>> 745b631089715734eaa43f50264e865dfad4ec7d

    };
    
   
