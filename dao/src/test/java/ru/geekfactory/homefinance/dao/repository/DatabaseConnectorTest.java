package ru.geekfactory.homefinance.dao.repository;

import org.h2.tools.RunScript;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectorTest {
    private Properties properties = new Properties();
    private InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");

    public Connection getConnection() {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String DB_URL = properties.getProperty("dburl");
        String DB_USER = properties.getProperty("dbuser");
        String DB_PASSWORD = properties.getProperty("dbpassword");
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void initDB() {
        try (Connection connection = getConnection()){
            File script = new File(getClass().getResource("/init_ddl.sql").getFile());
            RunScript.execute(connection, new FileReader(script));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not initialize with script");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
