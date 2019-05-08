/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.helpers;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class Circle {
    
    private int x;
    private int y;
    private int radius;
    
    /**
     * Circle constructor
     * 
     * @param x
     * @param y
     * @param radius
     */
    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    
    /**
     * To check if the coordinates x and y are inside the circle
     * 
     * @param x
     * @param y
     * @return true if the circle contains the coordinates
     */
    public boolean contains(int x, int y) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) < radius * radius; 
    }
    
    /**
     * To check if a point is inside the circle
     * 
     * @param p
     * @return true if the circle contains the point
     */
    public boolean contains(Point p) {
        return (p.x - this.x) * (p.x - this.x) + (p.y - this.y) * (p.y - this.y) < radius * radius;         
    }
    
    /**
     * To check if the circle collides with a rectangle
     * 
     * @param r
     * @return true if the circle collides with a rectangle
     */
    public boolean intersects(Rectangle r) {
        //If circle center is inside rectangle return true
        if (r.contains(getLocation())) {
            return true;
        }
        
        //Down intersection
        if (x > r.x && x < r.x + r.width && r.y + r.height < y - radius) {
            System.out.println("Down");
            return true;
        }
        
        //Up intersection
        if (x > r.x && x < r.x + r.width && r.y < y + radius) {
            System.out.println("Up");
            return true;
        }
        
        //Left intersection
        if (y > r.y && y < r.y + r.height && r.x < x + radius) {
            System.out.println("Left");
            return true;
        }
        
        //Right intersection
        if (y > r.y && y < r.y + r.height && r.x + r.width > x - radius) {
            System.out.println("Right");
            return true;
        }
        
        //If none of the above are true, return false
        return false;
    }
    
    /**
     * To check if the circle collides with another circle
     * 
     * @param c
     * @return true if the circle collides with another circle
     */
    public boolean intersects(Circle c) {
        return (Math.sqrt((c.x - x) * (c.x - x) + (c.y - y) * (c.y - y)) < 2 * 100);
    }
    
    /**
     * To get the location in a point class
     * 
     * @return point
     */
    public Point getLocation() {
        return new Point(x, y);
    }

    /**
     * To get the position x of the circle
     * 
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * To get the position y of the circle
     * 
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * To get the radius of the circle
     * 
     * @return radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * To set the position x of the circle
     * 
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * To set the position y of the circle
     * 
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * To set the radius of the circle
     * 
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
}
