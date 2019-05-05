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
        user = "evadmin";
        password = "Evoadmin1$";
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
            myResult = JDBC.myStatement.executeQuery("SELECT player_name, species_name, game_duration, game_score,SUM(organism_alive) as 'Survivor', MAX(organism_generation), SUM(organism_kills), MAX(organism_lifespan) FROM player P JOIN game G ON P.player_id = G.player_id JOIN species S ON S.game_id = G.game_id JOIN organism O ON O.species_id = S.species_id GROUP BY O.species_id ORDER BY G.game_score DESC LIMIT 10;");
            while (myResult.next()) {
                System.out.println(myResult.getString(1) + " " + myResult.getString(2) + " " + myResult.getInt(3) + " " + myResult.getInt(4) + " " + myResult.getString(4) + " " + myResult.getString(5) + " " + myResult.getString(6) + " " + myResult.getString(7));
                ArrayList<Object> Res = new ArrayList<Object>(7);
                Res.add(myResult.getString(1));
                Res.add(myResult.getString(2));
                Res.add(Integer.toString(myResult.getInt(3)));
                Res.add(Integer.toString(myResult.getInt(4)));
                Res.add(myResult.getString(5));
                Res.add(myResult.getString(6));
                Res.add(myResult.getString(7));
                myRanking.add(Res);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        myConnection.close();
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
            countinsert = myStatement.executeUpdate("INSERT INTO organism(organism_alive,organism_generation,organism_speed,organism_stealth,organism_strength,organism_max_health,species_id) VALUES(" + Integer.toString(i)+","+ Integer.toString(generation)+","+ Integer.toString(speed)+","+ Integer.toString(stealth)+","+ Integer.toString(strength)+","+ Integer.toString(maxHealth)+","+ Integer.toString(speciesID)+");");
            System.out.println(countinsert + " records inserted.\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateOrganisms(Organism o, int speciesID) {
     
      int countUpdated = 0;

        try {
            myStatement = myConnection.createStatement();
            
            System.out.println(speciesID + " is the SpeciesID.\n");
            countUpdated = myStatement.executeUpdate("UPDATE organism SET organism_alive = " + Integer.toString(o.isDead() ? 0 : 1) + " WHERE species_id = " + Integer.toString(speciesID) + ";");
            System.out.println(countUpdated + " records updated.\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    
    
    
    
    }

    public void updateOrganisms(OrganismManager om) {
          int countUpdated = 0;
        try {
            myStatement = myConnection.createStatement();
            
            for(int i = 0; i < om.getAmount(); i++)
            {
            countUpdated += myStatement.executeUpdate("UPDATE organism SET organism_alive = " + Integer.toString(om.getOrganism(i).isDead() ? 0 : 1) +  ", organism_generation "+ Integer.toString(om.getOrganism(i).getGeneration()) +  ", organism_speed "+Integer.toString(om.getOrganism(i).getSpeed())+  ", organism_stealth"+Integer.toString(om.getOrganism(i).getStealth())+  ", organism_strength "+Integer.toString(om.getOrganism(i).getStrength())+  ", organism_max_health "+Integer.toString(om.getOrganism(i).getMaxHealth()) + " WHERE species_id = " + Integer.toString(om.getSpeciesID()) + " AND organism_id = " + Integer.toString(om.getOrganism(i).getId()) + ";");
            }
            System.out.println(countUpdated + " records updated.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }}

    public void updateSpeciesName(int gameID, String name) {
        int countUpdated;
        try {
            myStatement = myConnection.createStatement();
           countUpdated =  myStatement.executeUpdate("UPDATE species SET species_name = " + name + " WHERE game_id = " + Integer.toString(gameID) +";");
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

}
