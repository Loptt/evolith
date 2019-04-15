package evolith.helpers;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public interface Commons {
    public static final int ORGANISM_SIZE = 30;            // organism size
    public static final int INITIAL_POINT = 1000;           // inital point
    public static final int MAX_ORGANISM_AMOUNT = 256;      // maximum organism amount
    public static final int PLANT_SIZE = 100;               // plant quanitity per unit
    public static final int FRAME_RATE = 60;                // frame rate
    public static final int PLANTS_AMOUNT = 1;              // number of plants
    public static final int MAX_THIRST = 100;               // maximum water level of organism
    public static final int MAX_HUNGER = 100;               // maximum hunger level of organism
    public static final int MAX_MATURITY = 30;              // maximum maturity level of organism
    
    public static final int SWARM_SEPARATION = ORGANISM_SIZE+10;
    
    public static final int SECONDS_PER_HUNGER = 3;
    public static final int SECONDS_PER_THIRST = 3;
    public static final int SECONDS_PER_MATURITY = 1;
}
