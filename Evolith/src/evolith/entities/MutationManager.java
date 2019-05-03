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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
        
        // name, strength, speed, health, stealth, active, tier, int x, int y, int width, int height, Game game, Organism org
        
        ArrayList<Mutation> m1 = new ArrayList<Mutation>();
        m1.add(new Mutation("Spine",20,0,0,0,false,1,-0.34,-0.35,1.72,1.72,game, org)); // Finished
        m1.add(new Mutation("Sting",20,-20,0,20,false,2,-0.1,0.95,0.8,0.8, game, org)); // Finished
        m1.add(new Mutation("Claws",20,-20,0,0,false,3,-0.3,1,0.9,0.9, game, org));     // Asset must be changed
        m1.add(new Mutation("Horns",20,-20,0,-40,false,4,-0.5,-0.5,2,1, game, org));    // Finished
        mutations.add(m1);

        ArrayList<Mutation> m2 = new ArrayList<Mutation>();
        m2.add(new Mutation("2 legs",0,20,0,0,false,1,-0.34,0.6,1.7,0.2, game, org));    // Finished 
        m2.add(new Mutation("4 legs",0,20,0,0,false,2,-0.3,0.2,1.6,0.65, game, org));   // Finished
        m2.add(new Mutation("6 legs",0,20,-20,0,false,3,-0.3,0.2,1.6,0.7,game, org));   // Finished
        m2.add(new Mutation("Wings",-20,20,-40,20,false,4,-0.5,0,2,1,game, org));       // Finished
        mutations.add(m2);
        
        ArrayList<Mutation> m3 = new ArrayList<Mutation>();
        m3.add(new Mutation("Medium Size",0,0,20,0,false,1,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));   // Finished
        m3.add(new Mutation("Big Size",20,-20,20,-20,false,2,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org)); // Finished
        m3.add(new Mutation("Shell",-20 ,-40,40,0,false,3,org.getX(),org.getY(),org.getWidth(),org.getHeight(), game, org));    // Finished
        mutations.add(m3);
        
        ArrayList<Mutation> m4 = new ArrayList<Mutation>();
        m4.add(new Mutation("Ears",0,0,0,20,false,1,0,-0.02,1.05,0.35, game, org));     // Finished
        m4.add(new Mutation("Stripes",0,0,0,20,false,2,0,0,1,1, game, org));            // Finished
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
    
    public int getStrengthTier() {
        int tier = 0;
        for (int i = 0; i < mutations.get(0).size(); i++) {
            if (mutations.get(0).get(i).isActive()) {
                tier = i + 1;
            }
        }
        
        return tier;
    }
    
    public int getSpeedTier() {
        int tier = 0;
        for (int i = 0; i < mutations.get(1).size(); i++) {
            if (mutations.get(1).get(i).isActive()) {
                tier = i + 1;
            }
        }
        
        return tier;
    }
    
    public int getHealthTier() {
        int tier = 0;
        for (int i = 0; i < mutations.get(2).size(); i++) {
            if (mutations.get(2).get(i).isActive()) {
                tier = i + 1;
            }
        }
        
        return tier;
    }
    
    public int getStealthTier() {
        int tier = 0;
        for (int i = 0; i < mutations.get(3).size(); i++) {
            if (mutations.get(3).get(i).isActive()) {
                tier = i + 1;
            }
        }
        
        return tier;
    }
    
    public void save(PrintWriter pw) {
        //Save the state of each mutation
        for (int i = 0; i < mutations.size(); i++) {
            for (int j = 0; j < mutations.get(i).size(); j++) {
                pw.println(Integer.toString(mutations.get(i).get(j).isActive() ? 1 : 0));
            }
        }
    }
    
    public void load(BufferedReader br) throws IOException {
        for (int i = 0; i < mutations.size(); i++) {
            for (int j = 0; j < mutations.get(i).size(); j++) {
                mutations.get(i).get(j).setActive(Integer.parseInt(br.readLine()) == 1);
            }
        }
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
    
   
