/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

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

    public MutationManager(Game game) {
        ArrayList<Mutation> m1 = new ArrayList<Mutation>();
        m1.add(new Mutation("Spine",20,0,0,0,true,1,0,0,0,0,game));
        m1.add(new Mutation("Sting",40,-20,0,20,true,2,0,0,0,0,game));
        m1.add(new Mutation("Claws",60,-20,0,0,true,3,0,0,0,0,game));
        m1.add(new Mutation("Horns",80,-20,0,-40,true,4,0,0,0,0,game));
        mutations.add(m1);
    
        ArrayList<Mutation> m2 = new ArrayList<Mutation>();
        m2.add(new Mutation("2 legs",0,20,0,0,true,1,0,0,0,0,game));
        m2.add(new Mutation("4 legs",0,40,0,0,true,2,0,0,0,0,game));
        m2.add(new Mutation("6 legs",0,60,-20,0,true,3,0,0,0,0,game));
        m2.add(new Mutation("Wings",-20,80,-40,20,true,4,0,0,0,0,game));
        mutations.add(m2);
        
        ArrayList<Mutation> m3 = new ArrayList<Mutation>();
        m3.add(new Mutation("Medium Size",0,0,20,0,true,1,0,0,0,0,game));
        m3.add(new Mutation("Big Size",20,-20,40,-20,true,2,0,0,0,0,game));
        m3.add(new Mutation("Shell",-20 ,-40,80,0,true,3,0,0,0,0,game));
        mutations.add(m3);
        
        ArrayList<Mutation> m4 = new ArrayList<Mutation>();
        m4.add(new Mutation("Ears",0,0,0,20,true,1,0,0,0,0,game));
        m4.add(new Mutation("Stripes",0,0,0,40,true,2,0,0,0,0,game));
        mutations.add(m4);
        
    };

    };
    
   
