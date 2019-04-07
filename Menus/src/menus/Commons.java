/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

/**
 *
 * @author charles
 */
public interface Commons {

    
    public static final int BOARD_WIDTH = 1200; //wifth of the whole board
    public static final int BOARD_HEIGHT = 700; //height of the whole beard
    public static final int GROUND = 633; //position of the ground
    public static final int BOMB_HEIGHT = 24; //height of the alien's bomb
    public static final int BOMB_WIDTH = 12; //width of the alien's width
    public static final int ALIEN_HEIGHT = 28; //height of each alien
    public static final int ALIEN_WIDTH = 38;//width of each alien

  /**
     * This class contains all the values used in various objects so that they can
     * be easily accessed in a single place
     */

    public static final int BORDER_RIGHT = 30; //extra border at the right side of the board
    public static final int BORDER_LEFT = 5; //extra border at the left side of the board
    public static final int GO_DOWN = 24; //movement of the aliens when going down
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24; //total number of aliens
    public static final int PLAYER_WIDTH = 40; //width of the player's object
    public static final int PLAYER_HEIGHT = 30; //height of the player's object
    public static final int PLAYER_START_X = 600; //initial x position of the player
    public static final int PLAYER_START_Y = 599; //initial y position of the player
    public static final int ALIEN_INIT_X = 150;//initial x position of the aliens 
    public static final int ALIEN_INIT_Y = 5; //initial y position of the aliens
    public static final int SHOT_WIDTH = 3; //width of the player's shot
    public static final int SHOT_HEIGHT = 28; //height of the player's shot
    public static final int ANIMATION_FRAMES = 20; //numbers of frames for animation
}
