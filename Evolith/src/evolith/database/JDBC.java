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

    public JDBC() {
        url = "jdbc:mysql://SG-Evolith-496-master.servers.mongodirector.com:3306/Evolith";
        user = "sgroot";
        password = "a6yaRypnDU-29cBS";
        try {
            myConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection made of user: " + user + " with password " + password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<ArrayList<Object>> getRanking() throws SQLException {

        ArrayList<ArrayList<Object>> myRanking = new ArrayList<ArrayList<Object>>();

        try {
            myStatement = myConnection.createStatement();
            myResult = JDBC.myStatement.executeQuery("SELECT species_name, game_duration, MAX(species_intelligence) FROM game G JOIN species S ON S.game_id = G.game_id GROUP BY S.species_id ORDER BY S.species_intelligence DESC, G.game_duration ASC LIMIT 10;");
            while (myResult.next()) {
                ArrayList<Object> Res = new ArrayList<Object>(3);
                Res.add(myResult.getString(1));
                Res.add(Integer.toString(myResult.getInt(2)));
                Res.add(Integer.toString(myResult.getInt(3)));
                myRanking.add(Res);
                System.out.println(Res.get(0) + " "+ Res.get(1) + " " + Res.get(2));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return myRanking;
    }

    public int getLastGameID() throws SQLException {

        int gameID = 0;

        try {
            myStatement = myConnection.createStatement();
            myResult = JDBC.myStatement.executeQuery("SELECT game_id FROM game ORDER BY game_id DESC LIMIT 1;");
            myResult.next();
            gameID = myResult.getInt(1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return gameID;
    }

    public void insertGame(int gameID, int ticker) {
        int countinsert = 0;

        try {
            myStatement = myConnection.createStatement();
            countinsert = myStatement.executeUpdate("INSERT INTO game(game_id,game_duration) VALUES (" + Integer.toString(gameID) + "," + Integer.toString(ticker) + ");");
            System.out.println(countinsert + " records inserted.\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateTimeGame(int gameID, int ticker) {
        int countUpdated = 0;
        try {
            myStatement = myConnection.createStatement();
            countUpdated = myStatement.executeUpdate("UPDATE game SET game_duration = " + Integer.toString(ticker) + " WHERE game_id = " + Integer.toString(gameID) + ";");
            System.out.println(countUpdated + " records updated.\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void insertSpecies(int gameID, String name) {
        int countinsert = 0;
        try {
            myConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection made of user: " + user + " with password " + password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            myStatement = myConnection.createStatement();
            countinsert = myStatement.executeUpdate("INSERT INTO species(game_id,species_name) VALUES ( " + Integer.toString(gameID) + ", \" " + name + " \" );");
            System.out.println(countinsert + " records inserted.\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int getSpeciesID(int gameID) {
        int speciesID = 0;

        try {
            myStatement = myConnection.createStatement();
            myResult = JDBC.myStatement.executeQuery("SELECT species_id FROM species WHERE game_id =" + Integer.toString(gameID) + ";");
            myResult.next();
            speciesID = myResult.getInt(1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(speciesID + " is the SpeciesID.\n");
        return speciesID;

    }

    public void insertOrganism(int speciesID, int i, int generation, int speed, int stealth, int strength, int maxHealth) {
        int countinsert = 0;
        try {
            myStatement = myConnection.createStatement();
            countinsert = myStatement.executeUpdate("INSERT INTO backup_organism(backup_organism_alive,backup_organism_generation,backup_organism_speed,backup_organism_stealth,backup_organism_strength,backup_organism_max_health,species_id) VALUES(" + Integer.toString(i) + "," + Integer.toString(generation) + "," + Integer.toString(speed) + "," + Integer.toString(stealth) + "," + Integer.toString(strength) + "," + Integer.toString(maxHealth) + "," + Integer.toString(speciesID) + ");");
            System.out.println(countinsert + " records inserted.\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateOrganisms(OrganismManager om) {
        
        int countUpdated = 0;
        try {
            myStatement = myConnection.createStatement();

            for (int i = 0; i < om.getAmount(); i++) {
                countUpdated += myStatement.executeUpdate("UPDATE backup_organism SET backup_organism_alive = " + Integer.toString(om.getOrganism(i).isDead() ? 0 : 1) + ", backup_organism_generation = " + Integer.toString(om.getOrganism(i).getGeneration()) + ", backup_organism_speed =" + Integer.toString(om.getOrganism(i).getSpeed()) + ", backup_organism_stealth =" + Integer.toString(om.getOrganism(i).getStealth()) + ", backup_organism_strength = " + Integer.toString(om.getOrganism(i).getStrength()) + ", backup_organism_max_health = " + Integer.toString(om.getOrganism(i).getMaxHealth()) + " WHERE backup_organism_id = " + Integer.toString(om.getOrganism(i).getId()) + ";");
            }
            System.out.println(countUpdated + " records updated.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        String updateIntelligence = "UPDATE species SET species_intelligence = " + Integer.toString(om.getMaxIntelligence())  +"WHERE species_id = " + Integer.toString(om.getSpeciesID());
        try {
            myStatement = myConnection.createStatement();

                countUpdated = myStatement.executeUpdate(updateIntelligence);
  
            System.out.println(countUpdated + " records updated.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateSpeciesName(int gameID, String name) {
        int countUpdated;
        try {
            myStatement = myConnection.createStatement();
            countUpdated = myStatement.executeUpdate("UPDATE species SET species_name = \" " + name + " \" WHERE game_id = " + Integer.toString(gameID) + ";");
            System.out.println(countUpdated + " records updated.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void insertSpecies(int gameID) {
        int countinsert = 0;
        try {
            myStatement = myConnection.createStatement();
            countinsert = myStatement.executeUpdate("INSERT INTO species(game_id) VALUES (" + Integer.toString(gameID) + ");");
            System.out.println(countinsert + " records inserted.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateBackup() {
        int countUpdated = 0;
        String mysqlDelete = "DELETE FROM backup_organism;";
        String modifyAutoIncrement = " ALTER TABLE backup_organism AUTO_INCREMENT = 1;";
        try {
            myStatement = myConnection.createStatement();

            countUpdated = myStatement.executeUpdate("INSERT INTO organism(organism_alive,organism_generation,organism_speed,organism_stealth,organism_strength,organism_max_health,species_id) SELECT backup_organism_alive,backup_organism_generation,backup_organism_speed,backup_organism_stealth,backup_organism_strength,backup_organism_max_health,species_id FROM backup_organism ;");
            System.out.println(countUpdated + " records updated.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            myStatement = myConnection.createStatement();

            countUpdated = myStatement.executeUpdate(mysqlDelete);
            System.out.println(countUpdated + " records deleted.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            myStatement = myConnection.createStatement();
            countUpdated = myStatement.executeUpdate(modifyAutoIncrement);
            System.out.println(countUpdated + " records updated.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    /*
    public void saveOrganisms(OrganismManager om) {
    int countUpdated = 0;
    
    try {
    myStatement = myConnection.createStatement();
    
    countUpdated = myStatement.executeUpdate("INSERT INTO backup_organism(backup_organism_alive,backup_organism_generation,backup_organism_speed,backup_organism_stealth,backup_organism_strength,backup_organism_max_health,backup_species_id) SELECT organism_alive,organism_generation,organism_speed,organism_stealth,organism_strength,organism_max_health,species_id FROM organism WHERE specied_id = " +Integer.toString(om.getSpeciesID()) + ";");
    System.out.println(countUpdated + " records updated.\n");
    } catch (Exception e) {
    System.out.println(e.getMessage());
    }}
     */

 /*
    public void loadOrganisms(OrganismManager om, int idCounter) {
        int countUpdated = 0;

        String sqlDelete = "DELETE FROM backup_organism where backup_organism_id >" + Integer.toString(idCounter) + ";";
        try {
            myStatement = myConnection.createStatement();

            countUpdated = myStatement.executeUpdate(sqlDelete);
            System.out.println(countUpdated + " records updated.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
     */
    public int[] getAverage()
    {
        int avg[] = new int[4];
        
        try {
            myStatement = myConnection.createStatement();
            myResult = JDBC.myStatement.executeQuery("SELECT AVG(organism_speed),AVG(organism_strength),AVG(organism_speed),AVG(organism_max_health) FROM organism;");
            while(myResult.next()){
             avg[0] = myResult.getInt(1);
             avg[1] = myResult.getInt(2);
             avg[2] = myResult.getInt(3);
             avg[3] = myResult.getInt(4);
             }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return avg;
    }
}
