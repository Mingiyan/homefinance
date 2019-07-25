package dordzhiev.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final static String DB_URL = "jdbc:postgresql://localhost:5432/geekfactory";
    private final static String DB_USER = "vvvd";
    private final static String DB_PASSWORD = "Vvvd010203";

    public Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
