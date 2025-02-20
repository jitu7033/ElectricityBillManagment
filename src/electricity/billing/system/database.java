package src.electricity.billing.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
    Connection connection; // it is come direct with sql package manager
    Statement statement;
    database() {
        try { // put try and catch because it has to handle so many exception
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bill_system", "root", "Ab@jitu123");
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
