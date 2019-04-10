/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author charles
 */
public class Organisms implements Commons {
    
    private ArrayList<Organism> organisms;
    private int amount;
    
    private Game game;
    private int counter;
    
    private Hover h;
    private boolean hover;
    
    private int newX;
    private int newY;
    
    public Organisms(Game game) {
        this.game = game;
        organisms = new ArrayList<>();
        amount = 20;
        
        for (int i = 0; i < amount; i++) {
            organisms.add(new Organism(INITIAL_POINT, INITIAL_POINT, ORGANISM_SIZE, ORGANISM_SIZE));
        }
        
        newX = INITIAL_POINT;
        newY = INITIAL_POINT;
    }
    
    public void tick() {
        ArrayList<Point> points;
        
        if (game.getMouseManager().isIzquierdo()) {
            newX = game.getCamera().getAbsX(game.getMouseManager().getX());
            newY = game.getCamera().getAbsX(game.getMouseManager().getY());
            points = SwarmMovement.getPositions(newX, newY, amount);
            System.out.println("In organisms");
            for (int i = 0; i < amount; i++) {
                organisms.get(i).setPoint(points.get(i));
                System.out.println(points.get(i));
            }
            
            game.getMouseManager().setIzquierdo(false);
        }
        
        /*points = SwarmMovement.getPositions(newX, newY, amount);
        for (int i = 0; i < amount; i++) {
            organisms.get(i).setPoint(points.get(i));
        }*/
        
        for (int i = 0; i < amount; i++) {
            organisms.get(i).tick();
        }
        
        counter++;
        
        if (counter >= 180) {
            organisms.add(new Organism(organisms.get(0).getX()+50, organisms.get(0).getY(), ORGANISM_SIZE, ORGANISM_SIZE));
            if (amount < 5) {
                amount++;
            }
            counter = 0;
        }
        
        checkHover();
    }
    
    public void checkHover() {
                
         for (int i = 0; i < amount; i++) {
              if(organisms.get(i).getPerimeter().contains(game.getCamera().getAbsX(game.getMouseManager().getX()),
                      game.getCamera().getAbsY(game.getMouseManager().getY())))
              {
                h = new Hover(game.getMouseManager().getX(),game.getMouseManager().getY(),100,100,
                        organisms.get(i).hunger, organisms.get(i).thirst, organisms.get(i).maturity, game);
                setHover(true);
                break;
              }
              else setHover(false);
         }

    }
    
    public void render(Graphics g) {
        for (int i = 0; i < amount; i++) {
            organisms.get(i).render(g);
        }
        
        if (h != null && isHover()) {
            h.render(g);
        }
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public boolean isHover() {
        return hover;
    }
    
    private class Organism extends Item {
        private Point point;
        private int maxVel;
        private int acc;
        private int xVel;
        private int yVel;
        
        private int size;
        private int speed;
        private int strength;
        private int stealth;
        private int survivability;
        
        private int hunger;
        private int thirst;
        private int maturity;

        public Organism (int x, int y, int width, int height) {
            super(x, y, width, height);
            point = new Point(x, y);
            maxVel = 3;
            xVel = 0;
            yVel = 0;
            acc = 1;
            
            size = 1;
            speed = 1;
            strength = 1;
            stealth = 1;
            survivability = 1;
            
            hunger = 100;
            thirst = 100;
            maturity = 0;
        }

        @Override
        public void tick() {
            if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 25) {
                if (Math.abs((int) point.getX() - x) < 15 && Math.abs((int) point.getY() - y) < 15) {
                    if (Math.abs((int) point.getX() - x) < 5 && Math.abs((int) point.getY() - y) < 5) {
                        maxVel = 0;
                    } else {
                        maxVel = 1;
                    }
                } else {
                    maxVel = 2;
                }
            } else {
                maxVel = 3;
            }


            if ((int) point.getX() > x) {
                xVel += acc;
            } else {
                xVel -= acc; 
            }

            if ((int) point.getY() > y) {
                yVel += acc;
            } else {
                yVel -= acc; 
            }

            if (xVel > maxVel) {
                xVel = maxVel;
            }

            if (xVel < maxVel * -1) {
                xVel = maxVel * -1;
            }

            if (yVel > maxVel) {
                yVel = maxVel;
            }

            if (yVel < maxVel * -1) {
                yVel = maxVel * -1;
            }

            x += xVel;
            y += yVel;
        }

        @Override
        public void render(Graphics g) {
            g.drawImage(Assets.player, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }
    }
}
