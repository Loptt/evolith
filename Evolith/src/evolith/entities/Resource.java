/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.engine.Assets;
import evolith.entities.Organisms.Organism;
import evolith.game.Game;
import evolith.game.Item;
import evolith.helpers.SwarmMovement;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author charles
 */
public abstract class Resource extends Item {

    private int quantity;
    private Game game;
    private boolean full;
    private int parasiteAmount;
    private ArrayList<Point> positions;
    private HashMap<Organism, Integer> map;
    public enum ResourceType {Plant, Water};
    private ResourceType type;
    
    public Resource(int x, int y, int width, int height, Game game, ResourceType type) {
        super(x, y, width, height);
        this.game = game;
        quantity = 100;
        full = false;
        parasiteAmount = 0;
        map = new HashMap<>();
        positions = SwarmMovement.getPositions(x, y, 6, 1);
        
        this.type = type;
    }
    
    public void addParasite(Organism org) {
        if (!full) {
            for (int i = 0; i < 6; i++) {
                if (!map.containsValue(i)) {
                    map.put(org, i);
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
            map.remove(org);
            parasiteAmount--;
        } else {
            System.out.println("ERROR, ORGANISM NOT IN RESOURCE");
        }
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
    
    @Override
    public void tick() {
        quantity -= parasiteAmount;
    }

    @Override
    public void render(Graphics g) {
        switch(type) {
            case Plant:
                g.setColor(new Color(173, 255, 250));
                g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

                g.drawImage(Assets.plant, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
                g.setColor(Color.RED);
                g.drawOval(game.getCamera().getRelX(radius.getX() - width / 2), game.getCamera().getRelY(radius.getY() - width / 2), radius.getRadius(), radius.getRadius());


                //To display the actual quantity over the maximum
                g.drawString(Integer.toString(quantity) + "/100", game.getCamera().getRelX(x) + 45, game.getCamera().getRelY(y) + 150);
                break;
            case Water:
                g.setColor(new Color(173, 255, 250));
                g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

                g.drawImage(Assets.water, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);

                //To display the actual quantity over the maximum
                g.drawString(Integer.toString(quantity) + "/100", game.getCamera().getRelX(x) + 45, game.getCamera().getRelY(y) + 150);
        }
    }
    
}
