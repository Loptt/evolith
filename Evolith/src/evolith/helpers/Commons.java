    package evolith.helpers;

import java.awt.Color;

/**
 *a
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public interface Commons {
    public static final int MAX_INTELLIGENCE = 1000;        //This marks the end of a game
    
    public static final int ORGANISM_SIZE_STAT = 20;            // organism size
    public static final int MAX_ORGANISM_AMOUNT = 256;      // maximum organism amount
    public static final int PLANT_SIZE = 100;               // plant quanitity per unit
    public static final int FRAME_RATE = 60;                // frame rate
    public static final int PREDATOR_SIZE = 80;

    public static final int MAX_THIRST = 100;               // maximum water level of organism
    public static final int MAX_HUNGER = 100;               // maximum hunger level of organism
    public static final int MAX_MATURITY = 180;              // maximum maturity level of organism
    public static final int MAX_SPEED = 100;              // maximum maturity level of organism
    public static final int MAX_SIZE = 100;              // maximum maturity level of organism
    public static final int MAX_STRENGTH = 100;              // maximum maturity level of organism
    public static final int MAX_SURVIVABILITY = 100;              // maximum maturity level of organism
    public static final int MAX_STEALTH = 100;              // maximum maturity level of organism
    public static final double MAX_HEALTH = 100;
    public static final int MAX_SIGHT_DISTANCE = 500;
    
    public static final int MAX_STAMINA = 200;
    
    public static final int PLANTS_AMOUNT = 50;              // number of plants
    public static final int WATERS_AMOUNT = 50;
    public static final int PREDATORS_AMOUNT = 4;
    public static final int WATER_SIZE = 100;
    public static final int SWARM_SEPARATION = ORGANISM_SIZE_STAT+30;
    
    public static final int SECONDS_PER_HUNGER = 5;
    public static final int SECONDS_PER_THIRST = 5;
    public static final int SECONDS_PER_MATURITY = 1;
    public static final int SECONDS_PER_FULL_RES_INTEL = 2;
    
    public static final int BUTTON_PLAY_X = 330;
    public static final int BUTTON_PLAY_Y = 370;
      
    public static final int PANEL_X = 190;
    public static final int PANEL_Y = 150;
    public static final int PANEL_WIDTH = 620;
    public static final int PANEL_HEIGHT = 369;
    public static final int BUTTON_CLOSE_DIMENSION = 40;
    
    public static final int MUTATION_PANEL_X = 50;
    public static final int MUTATION_PANEL_Y = 75;
    public static final int MUTATION_PANEL_WIDTH = 732;
    public static final int MUTATION_PANEL_HEIGHT = 517;
    public static final int MUTATION_BUTTON_CLOSE_DIMENSION = 40;
    
    
    public static final int BUTTON_INSTRUCTIONS_Y = 500;
    public static final int BUTTON_INSTRUCTIONS_X = 330;
    
    public static final int BUTTON_PLAY_WIDTH = 350;
    public static final int BUTTON_PLAY_HEIGHT = 95;
    
    public static final int BUTTON_INSTRUCTIONS_WIDTH = 350;
    public static final int BUTTON_INSTRUCTIONS_HEIGHT = 95;

    public static final int MINIMAP_X = 810;
    public static final int MINIMAP_Y = 20;
            
    public static final int BACKGROUND_WIDTH = 5000;
    public static final int BACKGROUND_HEIGHT = 5000;
    
    public static final int INITIAL_POINT = BACKGROUND_WIDTH / 2;           // inital point

    
    public static final int MINIMAP_WIDTH = BACKGROUND_WIDTH / 30; // 166~
    public static final int MINIMAP_HEIGHT = BACKGROUND_HEIGHT / 30; // 166~
    
    public static final Color PURPLE_COLOR = new Color(235, 64, 255);
    public static final Color BLUE_GREEN_COLOR = new Color(1, 196, 181);
    public static final Color ORANGE_COLOR = new Color(239, 186, 1);
    public static final Color PINK_COLOR = new Color(255, 111, 199);
    
    public static final Color FONT_COLOR = new Color(9, 255, 200);
    
    public static final int CONSUMING_RATE = 1;
    
    public static final int DAY_CYCLE_DURATION_SECONDS = 15;
    
    public static final int PREDATOR_SECONDS_IN_RESOURCE = 7;
    public static final int PREDATOR_SECONDS_TO_ROAM = 10;
    
    public static final int BORN_TIME = 20;
    
    public static final int ORG_DATA_SIZE = 9;
    public static final int RES_DATA_SIZE = 7;
    public static final int PRE_DATA_SIZE = 6;
}
