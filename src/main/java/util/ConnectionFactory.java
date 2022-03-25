package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    /**
     * Used to get a connection to our database
     * @return a connection to our postgres database
     */
    public static Connection getConnection(){
        // storing sensitive data in environmental variables on my machine
        final String url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/Project1";
        final String username = System.getenv("RDS_USERNAME");
        final String password = System.getenv("RDS_PASSWORD");

        Connection conn = null;

        try{
            conn = DriverManager.getConnection(url,username,password);
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return conn;
    }
}