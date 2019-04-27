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
        user = "sgroot";
        password = "a6yaRypnDU-29cBS";
        try {
            myConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection made of user: " + user + " with password " + password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            myStatement = myConnection.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void getMutation() throws SQLException {
        myResult = ConnectionMySql.myStatement.executeQuery("Select * from Mutation;");
    }
    public void getSpecies() throws SQLException {
        myResult = ConnectionMySql.myStatement.executeQuery("Select * from Species;");
    }
    public void getGame() throws SQLException {
        myResult = ConnectionMySql.myStatement.executeQuery("Select * from Game;");
    }
    public void getEnemy() throws SQLException {
        myResult = ConnectionMySql.myStatement.executeQuery("Select * from Enemy;");
        while(myResult.next())
        {
         System.out.println( "Enemy id: "+  myResult.getString("enemy_id")+" Position x" + myResult.getString("enemy_position_x")+" Position y" + myResult.getString("enemy_position_y"));
        }
    }
    public void getOrganism() throws SQLException {
        myResult = ConnectionMySql.myStatement.executeQuery("Select * from Organism;");
    }

}
