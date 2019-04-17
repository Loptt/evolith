/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodandwater;

/**
 *
 * @author charles
 */
public class Camera {
    private int width, height, x, y;
    private Game game;
    
    public Camera(int x, int y, int width, int height, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void tick() {
        if (game.getKeyManager().w) {
            if(getY() - 5 <= 10){
                setY(10);
            }
            else{
                setY(getY() - 5);
            }
        }
        if (game.getKeyManager().a) {
            if(getX() - 5 <= 10){
                setX(10);
            }
            else{
                setX(getX() - 5);
            }
        }
        if (game.getKeyManager().s) {
            if(getY() + 5 >= game.getBackground().getHeight() - game.getHeight() - 10){
                setY(game.getBackground().getHeight() - game.getHeight() - 10);
            }
            else{
                setY(getY() + 5);
            }                                    
        }
        if (game.getKeyManager().d) {
            if(getX() + 5 >= game.getBackground().getWidth() - game.getWidth() - 10){
                setX(game.getBackground().getWidth() - game.getWidth() - 10);
            }
            else{
                setX(getX() + 5);
            }
        }
    }
    
    public int getRelX(int absX) {
        return absX - x;
    }
    
    public int getRelY(int absY) {
        return absY - y;
    }
    
    public int getAbsX(int relX) {
        return relX + x;
    }
    
    public int getAbsY(int relY) {
        return relY + y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
