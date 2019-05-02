package evolith.database;

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
    public ArrayList<ArrayList<Object>> myRanking;

    public JDBC() {
         url = "jdbc:mysql://SG-Evolith-496-master.servers.mongodirector.com:3306/Evolith";
        user = "evadmin";
        password = "Evoadmin1$";
    }
    
    public  ArrayList<ArrayList<Object>> getRanking() throws SQLException {
        
        myRanking = new ArrayList<ArrayList<Object>>();
        try {
            myConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection made of user: " + user + " with password " + password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            myStatement = myConnection.createStatement();
            myResult = JDBC.myStatement.executeQuery("SELECT player_name, species_name, game_duration, game_score,SUM(organism_alive) as 'Survivor', MAX(organism_generation), SUM(organism_kills), MAX(organism_lifespan) FROM player P JOIN game G ON P.player_id = G.player_id JOIN species S ON S.game_id = G.game_id JOIN organism O ON O.species_id = S.species_id GROUP BY O.species_id ORDER BY G.game_score DESC LIMIT 10;");
            while(myResult.next())
            {
                System.out.println(  myResult.getString(1) + " " + myResult.getString(2 )+ " " + myResult.getInt(3)+ " " + myResult.getInt(4) +" " + myResult.getString(4) + " " + myResult.getString(5) +" " +  myResult.getString(6) + " " + myResult.getString(7));
                ArrayList<Object>  Res = new ArrayList<Object>(7);
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

    public void getMutation() throws SQLException {
        myResult = JDBC.myStatement.executeQuery("SELECT player_name, species_name, game_duration, game_score,SUM(organism_alive), MAX(organism_generation), SUM(organism_kills), MAX(organism_lifespan) FROM player P JOIN game G ON P.player_id = G.player_id JOIN species S ON S.game_id = G.game_id JOIN organism O ON O.species_id = S.species_id GROUP BY O.species_id ORDER BY G.game_score DESC LIMIT 10;");
    }

}
