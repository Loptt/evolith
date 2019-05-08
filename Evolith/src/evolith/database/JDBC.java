package evolith.database;

import evolith.entities.Organism;
import evolith.entities.OrganismManager;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author ErickFrank
 */
public class JDBC {

    public static String url;
    public static String user;
    public static String password;
    public static Connection myConnection;
    public static Statement myStatement;
    public static ResultSet myResult;

    /**
     * Constructor of the database connection through the driver manager. The
     * url is the connection through which the JDBC driver connects to the
     * servers. Mysql protocol handles the reconnection.
     */
    public JDBC() {

        url = "jdbc:mysql://SG-Evolith-496-master.servers.mongodirector.com:3306/Evolith"; // url to the database
        user = "carladmin"; // main user
        password = "Carldmin1$"; // user's password
        //tries to connect through the specified pathway of the driver else sends out a connection error
        try {
            myConnection = DriverManager.getConnection(url, user, password);

            // System.out.println("Connection made of user: " + user + " with password " + password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Function to get the top ranking games of Evolith
     *
     * @return myRanking
     * @throws SQLException
     */
    public ArrayList<ArrayList<Object>> getRanking() throws SQLException {
        //Matrix to store the ranking and useful information about the game
        ArrayList<ArrayList<Object>> myRanking = new ArrayList<ArrayList<Object>>();
        //String that queries the species name, game duration and the maximum intelligence of the species.
        String getRanking = "SELECT species_name, game_duration, MAX(species_intelligence) FROM game G JOIN species S ON S.game_id = G.game_id GROUP BY S.species_id ORDER BY S.species_intelligence DESC, G.game_duration ASC LIMIT 8;";
        //Executes the previous query
        try {
            //Creates a new statement from the connection of the database
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myResult = JDBC.myStatement.executeQuery(getRanking);
            //While there is still information in the dataset it stores it into the matrix
            while (myResult.next()) {
                //Creates an array of objects and adds the different columns to the ranking
                ArrayList<Object> Res = new ArrayList<Object>(3);
                Res.add(myResult.getString(1));
                Res.add(Integer.toString(myResult.getInt(2)));
                Res.add(Integer.toString(myResult.getInt(3)));
                myRanking.add(Res);
            }
        } catch (Exception e) {
            //Prints out the exception
            System.out.println(e.getMessage());
        }
        return myRanking;
    }

    /**
     * To get the last game ID of the game to identify the game and generate
     * future queries
     *
     * @return gameID
     * @throws SQLException
     */
    public int getLastGameID() throws SQLException {
        //Initializes the games
        int gameID = 0;
        String getLastGameID = "SELECT game_id FROM game ORDER BY game_id DESC LIMIT 1;";
        //Executes the previous query
        try {
            //Creates a new statement from the connection of the database
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myResult = JDBC.myStatement.executeQuery(getLastGameID);
            //Moves the pointer to the next datase
            myResult.next();
            //Stores the result of the game id into the variable
            gameID = myResult.getInt(1);

        } catch (Exception e) {
            //Prints out the exception
            System.out.println(e.getMessage());
        }

        return gameID;
    }

    /**
     * Inserts a new game with the gameid and sets a new game duration according
     * to the time spent
     *
     * @param gameID
     * @param ticker
     */
    public void insertGame(int gameID, int ticker) {
        //Query
        String insertGame = "INSERT INTO game(game_id,game_duration) VALUES (" + Integer.toString(gameID) + "," + Integer.toString(ticker) + ");";
        //Executes the previous query
        try {
            //Creates a new statement from the connection of the database
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate(insertGame);
        } catch (Exception e) {
            //Prints out the exception
            System.out.println(e.getMessage());
        }

    }

    /**
     * Updates the game's time from the ticker and the game id
     *
     * @param gameID
     * @param ticker
     */
    public void updateTimeGame(int gameID, int ticker) {
        //Query to update
        String updateTime = "UPDATE game SET game_duration = " + Integer.toString(ticker) + " WHERE game_id = " + Integer.toString(gameID) + ";";
        try {
            //Creates a new statement from the connection of the database
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate(updateTime);
        } catch (Exception e) {
            //Prints out the exception
            System.out.println(e.getMessage());
        }

    }

    /**
     * Inserts the species with the name and the game id
     *
     * @param gameID
     * @param name
     */
    public void insertSpecies(int gameID, String name) {
        //Query
        String insertSpecies = "INSERT INTO species(game_id,species_name) VALUES ( " + Integer.toString(gameID) + ", \" " + name + " \" );";

        try {
            //Creates a new statement from the connection of the database
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate(insertSpecies);
        } catch (Exception e) {
            //Prints out the exception
            System.out.println(e.getMessage());
        }
    }

    /**
     * To get the speciesID from the gameID
     *
     * @param gameID
     * @return speciesID
     */
    public int getSpeciesID(int gameID) {
        //Initialiazes the species ID to 0
        int speciesID = 0;
        //Query to get the speciesID
        String getSpeciesID = "SELECT species_id FROM species WHERE game_id =" + Integer.toString(gameID) + ";";
        try {
            //Creates a new statement from the connection of the database
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myResult = JDBC.myStatement.executeQuery(getSpeciesID);
            //Moves the pointer to the next datase
            myResult.next();
            //Stores the result of the species id into the variable
            speciesID = myResult.getInt(1);

        } catch (Exception e) {
            //Prints out the exception
            System.out.println(e.getMessage());
        }

        return speciesID;

    }

    /**
     * Inserts an organism with a specific generation, speed, stealth, strength
     * and maxHealth
     *
     * @param speciesID
     * @param i
     * @param generation
     * @param speed
     * @param stealth
     * @param strength
     * @param maxHealth
     */
    public void insertOrganism(int speciesID, int i, int generation, int speed, int stealth, int strength, int maxHealth) {
        //Query
        String insertOrganism = "INSERT INTO backup_organism(backup_organism_alive,backup_organism_generation,backup_organism_speed,backup_organism_stealth,backup_organism_strength,backup_organism_max_health,species_id) VALUES(" + Integer.toString(i) + "," + Integer.toString(generation) + "," + Integer.toString(speed) + "," + Integer.toString(stealth) + "," + Integer.toString(strength) + "," + Integer.toString(maxHealth) + "," + Integer.toString(speciesID) + ");";

        try {
            //Creates a new statement from the connection of the database
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate(insertOrganism);
        } catch (Exception e) {
            //Prints out the exception
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the organism from the database and updates the intelligence of
     * the species
     *
     * @param om
     */
    public void updateOrganisms(OrganismManager om) {
        //Query to update organisms' intelligence
        String updateIntelligence = "UPDATE species SET species_intelligence = " + Integer.toString(om.getMaxIntelligence()) + "WHERE species_id = " + Integer.toString(om.getSpeciesID());

        try {
            //Creates a new statement from the connection of the databases
            myStatement = myConnection.createStatement();
            //Updates the manager from the id
            for (int i = 0; i < om.getAmount(); i++) {
                myStatement.executeUpdate("UPDATE backup_organism SET backup_organism_alive = " + Integer.toString(om.getOrganism(i).isDead() ? 0 : 1) + ", backup_organism_generation = " + Integer.toString(om.getOrganism(i).getGeneration()) + ", backup_organism_speed =" + Integer.toString(om.getOrganism(i).getSpeed()) + ", backup_organism_stealth =" + Integer.toString(om.getOrganism(i).getStealth()) + ", backup_organism_strength = " + Integer.toString(om.getOrganism(i).getStrength()) + ", backup_organism_max_health = " + Integer.toString(om.getOrganism(i).getMaxHealth()) + " WHERE backup_organism_id = " + Integer.toString(om.getOrganism(i).getId()) + ";");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            //Creates a new statement from the connection of the database
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate(updateIntelligence);

        } catch (Exception e) {
            //Prints out the exceptions
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the species name
     *
     * @param gameID
     * @param name
     */
    public void updateSpeciesName(int gameID, String name) {
        //Query to update the species name
        String updateSpeciesName = "UPDATE species SET species_name = \" " + name + " \" WHERE game_id = " + Integer.toString(gameID) + ";";

        try {
            //Creates a new statement from the connection of the databases
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate(updateSpeciesName);
        } catch (Exception e) {
            //Prints out the exceptions
            System.out.println(e.getMessage());
        }

    }

    /**
     * Initialize a record with a new species
     *
     * @param gameID
     */
    public void insertSpecies(int gameID) {
        //Query 
        String insertSpecies = "INSERT INTO species(game_id) VALUES (" + Integer.toString(gameID) + ");";

        try {
            //Creates a new statement from the connection of the databases
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate(insertSpecies);
        } catch (Exception e) {
            //Prints out the exceptions
            System.out.println(e.getMessage());
        }

    }

    /**
     * Updates the backup organism from the database and inserts it to the
     */
    public void updateBackup() {
        //Queries that delete  inserts all the content from one to another
        String mysqlDelete = "DELETE FROM backup_organism;";
        String modifyAutoIncrement = " ALTER TABLE backup_organism AUTO_INCREMENT = 1;";

        try {
            //Creates a new statement from the connection of the databases
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate("INSERT INTO organism(organism_alive,organism_generation,organism_speed,organism_stealth,organism_strength,organism_max_health,species_id) SELECT backup_organism_alive,backup_organism_generation,backup_organism_speed,backup_organism_stealth,backup_organism_strength,backup_organism_max_health,species_id FROM backup_organism ;");
        } catch (Exception e) {
            //Prints out the exceptionss
            System.out.println(e.getMessage());
        }

        try {
            //Creates a new statement from the connection of the databases
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate(mysqlDelete);
        } catch (Exception e) {
            //Prints out the exceptionss
            System.out.println(e.getMessage());
        }

        try {
            //Creates a new statement from the connection of the databasess
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myStatement.executeUpdate(modifyAutoIncrement);
        } catch (Exception e) {
            //Prints out the exceptionss
            System.out.println(e.getMessage());
        }

    }

    /**
     * Gets the average attributes of an organism
     *
     * @return avg
     */
    public int[] getAverage() {
        //Initializes the array
        int avg[] = new int[4];

        //Query
        String getAverage = "SELECT AVG(organism_speed),AVG(organism_strength),AVG(organism_speed),AVG(organism_max_health) FROM organism;";

        try {
            //Creates a new statement from the connection of the databases
            myStatement = myConnection.createStatement();
            //Execute and stores the result of the query
            myResult = JDBC.myStatement.executeQuery(getAverage);

            while (myResult.next()) {
                avg[0] = myResult.getInt(1);
                avg[1] = myResult.getInt(2);
                avg[2] = myResult.getInt(3);
                avg[3] = myResult.getInt(4);
                //Prints out the exceptions
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return avg;
    }
}
