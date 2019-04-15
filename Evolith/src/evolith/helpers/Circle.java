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
 * @author charles
 */
public class Circle {
    private int x;
    private int y;
    private int radius;
    
    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    
    public boolean contains(int x, int y) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) < radius * radius; 
    }
    
    public boolean contains(Point p) {
        return (p.x - this.x) * (p.x - this.x) + (p.y - this.y) * (p.y - this.y) < radius * radius;         
    }
    
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
    
    public boolean intersects(Circle c) {
        return (int) Math.sqrt((this.x - c.x) * (this.x - c.x) + (this.y - c.y) * (this.y - c.y)) < 2 * 100;
    }
    
    public Point getLocation() {
        return new Point(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
