package evolith.database;

import java.sql.*;

/**
 *
 * @author ErickFrank
 */
public class ConnectionMySql {

    public static String url;
    public static String user;
    public static String password;
    public static Connection myConnection;
    public static Statement myStatement;
    public static ResultSet myResult;

    public static void connect() throws SQLException {

        url = "jdbc:mysql://SG-Evolith-496-master.servers.mongodirector.com:3306/Evolith";
        user = "evadmin";
        password = "Evoadmin1$";
        try {
            myConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection made of user: " + user + " with password " + password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            myStatement = myConnection.createStatement();
            myResult = ConnectionMySql.myStatement.executeQuery("SELECT player_name, species_name, game_duration, game_score,SUM(organism_alive), MAX(organism_generation), SUM(organism_kills), MAX(organism_lifespan) FROM player P JOIN game G ON P.player_id = G.player_id JOIN species S ON S.game_id = G.game_id JOIN organism O ON O.species_id = S.species_id GROUP BY O.species_id ORDER BY G.game_score DESC LIMIT 10;");
            while(myResult.next())
            {
                
                
                
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void getMutation() throws SQLException {
        myResult = ConnectionMySql.myStatement.executeQuery("SELECT player_name, species_name, game_duration, game_score,SUM(organism_alive), MAX(organism_generation), SUM(organism_kills), MAX(organism_lifespan) FROM player P JOIN game G ON P.player_id = G.player_id JOIN species S ON S.game_id = G.game_id JOIN organism O ON O.species_id = S.species_id GROUP BY O.species_id ORDER BY G.game_score DESC LIMIT 10;");
    }

}
