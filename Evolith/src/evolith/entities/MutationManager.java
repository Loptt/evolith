/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.engine.Assets;
import evolith.game.Game;
import evolith.helpers.Commons;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author ErickFrank
 */
public class MutationManager implements Commons{

    private ArrayList<ArrayList<Mutation>> mutations;
    private Organism org;
    private Game game;
    

    public MutationManager(Organism org, Game game) {
        mutations = new ArrayList<ArrayList<Mutation>>();
        this.org = org;
        
        ArrayList<Mutation> m1 = new ArrayList<Mutation>();
        m1.add(new Mutation("Spine",20,0,0,0,false,1,0,0,org.getWidth(),org.getHeight(),game, org));
        m1.add(new Mutation("Sting",40,-20,0,20,false,2,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        m1.add(new Mutation("Claws",60,-20,0,0,false,2,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        m1.add(new Mutation("Horns",80,-20,0,-40,false,4,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        mutations.add(m1);
    
        ArrayList<Mutation> m2 = new ArrayList<Mutation>();
        m2.add(new Mutation("2 legs",0,20,0,0,false,1,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        m2.add(new Mutation("4 legs",0,40,0,0,false,2,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        m2.add(new Mutation("6 legs",0,60,-20,0,false,3,org.getX(),org.getY(),org.getWidth(),org.getHeight(),game, org));
        m2.add(new Mutation("Wings",-20,80,-40,20,false,4,org.getX(),org.getY(),org.getWidth(),org.getHeight(),game, org));
        mutations.add(m2);
        
        ArrayList<Mutation> m3 = new ArrayList<Mutation>();
        m3.add(new Mutation("Medium Size",0,0,20,0,false,1,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        m3.add(new Mutation("Big Size",20,-20,40,-20,false,2,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        m3.add(new Mutation("Shell",-20 ,-40,80,0,false,3,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        mutations.add(m3);
        
        ArrayList<Mutation> m4 = new ArrayList<Mutation>();
        m4.add(new Mutation("Ears",0,0,0,20,false,1,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        m4.add(new Mutation("Stripes",0,0,0,40,false,2,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));
        mutations.add(m4);
        
        for(int i=0; i<4; i++){
            for(int j=0; j<mutations.get(i).size(); j++){
                mutations.get(i).get(j).setSprite(Assets.mutations.get(i).get(j));
            }
        }
        
    }

    public ArrayList<ArrayList<Mutation>> getMutations() {
        return mutations;
    }

    /*
    public void setMutationData(Mutation orgMutation, int stat, int tier){
        orgMutation.setSpeed(mutations.get(stat).get(tier).getSpeed());
        orgMutation.setStrength(mutations.get(stat).get(tier).getStrength());
        orgMutation.setMaxHealth(mutations.get(stat).get(tier).getMaxHealth());
        orgMutation.setStealth(mutations.get(stat).get(tier).getStealth());
        orgMutation.setName(mutations.get(stat).get(tier).getName());
        orgMutation.setActive(mutations.get(stat).get(tier).isActive());
        orgMutation.setSprite(mutations.get(stat).get(tier).getSprite());
        orgMutation.setX(mutations.get(stat).get(tier).getX());
        orgMutation.setY(mutations.get(stat).get(tier).getY());
        orgMutation.setWidth(mutations.get(stat).get(tier).getWidth());
        orgMutation.setHeight(mutations.get(stat).get(tier).getHeight());
    }
    */
    
    public void render(Graphics g){
        for(int i=0; i<4; i++){
            for(int j=0; j<mutations.get(i).size(); j++){
                mutations.get(i).get(j).render(g);
            }
        }
    }

    }
    
   
