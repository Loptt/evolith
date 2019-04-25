package evolith.database;

import java.sql.*;

/**
 *
 * @author ErickFrank
 */
public class ConnectionMySql {
        private String url;
        private String user;
        private String password;
        private Connection myConnection;

    public ConnectionMySql(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        
        try  {
            this.myConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection made of user: " + user + " with password " + password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getMyConnection() {
        return myConnection;
    }

    public void setMyConnection(Connection myConnection) {
        this.myConnection = myConnection;
    }
    
    
}
