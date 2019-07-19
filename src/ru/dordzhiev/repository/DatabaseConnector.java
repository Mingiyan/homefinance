package ru.dordzhiev.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final static String DB_URL = "jdbc:postgresql://localhost:5432/home_finance";
    private final static String DB_USER = "**";
    private final static String DB_PASSWORD = "****";

    public Connection getConnection() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
