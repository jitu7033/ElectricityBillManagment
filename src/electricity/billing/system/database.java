package src.electricity.billing.system;

import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class database {
    Connection connection; // it is come direct with sql package manager
    Statement statement;
    database() {
        try { // put try and catch because it has to handle so many exception
            Properties props = new Properties();
            props.load(new FileInputStream(".env"));
            String url = props.getProperty("DB_URL");
            String root = props.getProperty("DB_USER");
            String password = props.getProperty("DB_PASSWORD");
            connection = DriverManager.getConnection(url, root, password);
            statement = connection.createStatement();
            System.out.println("connect databases ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new database();
    }

}
