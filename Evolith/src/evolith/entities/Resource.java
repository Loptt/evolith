/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.engine.Assets;
import evolith.game.Game;
import evolith.game.Item;
import evolith.helpers.Commons;
import evolith.helpers.SwarmMovement;
import evolith.helpers.Time;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

/**
 *
 * @author charles
 */
public class Resource extends Item implements Commons{

    private int quantity;
    private int maxQuantity;
    private Game game;
    private boolean full;
    private boolean fullOfPredators;
    private boolean over;
    private int parasiteAmount;
    private int predatorAmount;
    private ArrayList<Point> positions;
    private HashMap<Organism, Integer> map;
    private Time time;

    public enum ResourceType {Plant, Water};
    private ResourceType type;
    
    private double prevSecUpdate;
    private boolean update;
    private boolean add;
    
    private Predator predator;
    
    public Resource(int x, int y, int width, int height, Game game, ResourceType type) {
        super(x, y, width, height);
        this.game = game;
        maxQuantity = 50;
        quantity = maxQuantity;
        full = false;
        fullOfPredators = false;
        over = false;
        parasiteAmount = 0;
        predatorAmount = 0;
        map = new HashMap<>();
        positions = SwarmMovement.getPositions(x + PLANT_SIZE / 2, y + PLANT_SIZE / 2, 6, 1);
        
        time = new Time();
        prevSecUpdate = 0;
        
        this.type = type;
        update = true;
        add = true;
        
        predator = null;
    }
    
    public void updatePositions() {
        positions = SwarmMovement.getPositions(x + PLANT_SIZE / 2, y + PLANT_SIZE / 2, 6, 1);
    }
    
    public void addParasite(Organism org) {
        if (!full) {
            for (int i = 0; i < 6; i++) {
                if (!map.containsValue(i)) {
                    map.put(org, i);
                    org.setPoint((Point) positions.get(i).clone());
                    //System.out.println(positions.get(i));
                    //System.out.println("TO ID:   " + org.getId());
                    parasiteAmount++;
                    if (parasiteAmount >= 6) {
                        full = true;
                    }
                    return;
                }
            }
            
            //If code reaches here, it is already full so error
            System.out.println("ERROR, POSITIONS FULL");
        }
    }

    public void removeParasite(Organism org) {
        if (map.containsKey(org)) {
            //System.out.println("AMOUNT  :" + map.size());
            map.remove(org);
            parasiteAmount--;
            if (parasiteAmount < 6) {
                full = false;
            }
            //System.out.println("PARASITE REMOVED  ID:  " + i);
        } else {
            System.out.println("ERROR, ORGANISM NOT IN RESOURCE");
        }
        
        //System.out.println("END OF REMOVEPAR FUNCTION:  ID:   " + i);
    }
    
    public void save(PrintWriter pw) {
        //Save positions
        pw.println(Integer.toString(x));
        pw.println(Integer.toString(y));
        
        //Quantity
        pw.println(Integer.toString(quantity));
        
        //Type
        pw.println(Integer.toString(type == ResourceType.Plant ? 1 : 0));
    }
    
    public void load(BufferedReader br) throws IOException {
        x = Integer.parseInt(br.readLine());
        y = Integer.parseInt(br.readLine());
        
        updatePositions();
        
        quantity = Integer.parseInt(br.readLine());
        
        int b = Integer.parseInt(br.readLine());
    }
    
    public void removeParasites() {
        map.clear();
    }
    
    boolean hasParasite(Organism org) {
        return map.containsKey(org);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Predator getPredator() {
        return predator;
    }

    public void setPredator(Predator predator) {
        this.predator = predator;
    }
    
    @Override
    public void tick() {
        time.tick();
        
        if (time.getSeconds() > prevSecUpdate + CONSUMING_RATE) {
            if (parasiteAmount > 0) {
                update = true;
            } else {
                update = false;
            }
            quantity -= parasiteAmount;
            prevSecUpdate = time.getSeconds();
            Iterator it = map.entrySet().iterator();
            
            while(it.hasNext()){
                Map.Entry element = (Map.Entry) it.next();
                Organism org = (Organism) element.getKey();
                if(org.isEating()){
                    int actualHunger = org.getHunger();
                    org.setHunger(actualHunger+=2);
                }
                if(org.isDrinking()){
                    int actualThirst = org.getThirst();
                    org.setThirst(actualThirst+=2);
                }
            }
        }
        
        if (quantity <= 0) {
            quantity = 0;
            over = true;
        }

    }

    @Override
    public void render(Graphics g) {
        switch(type) {
            case Plant:
                g.setColor(new Color(173, 255, 250));
                g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

                g.drawImage(Assets.plant, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
               
                //To display the actual quantity over the maximum
                //g.drawString(Integer.toString(quantity) + "/100", game.getCamera().getRelX(x) + 45, game.getCamera().getRelY(y) + 150);
                g.setColor(Color.GREEN);
                break;
            case Water:
                g.setColor(new Color(173, 255, 250));
                g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

                g.drawImage(Assets.water, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);

                //To display the actual quantity over the maximum
                //g.drawString(Integer.toString(quantity) + "/100", game.getCamera().getRelX(x) + 45, game.getCamera().getRelY(y) + 150);
                g.setColor(Color.BLUE);
        }
        g.fillRect(game.getCamera().getRelX(x) + 10, game.getCamera().getRelY(y) + 85, (int) 87 * this.quantity / maxQuantity, 7);
        g.setColor(Color.white);
        g.drawRect(game.getCamera().getRelX(x) + 9, game.getCamera().getRelY(y) + 85, 87, 8);
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
    
    public boolean isPlant() {
        return type == ResourceType.Plant;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }
}