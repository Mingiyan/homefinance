package ru.geekfactory.homefinance.dao.repository;

import org.h2.tools.RunScript;
import ru.geekfactory.homefinace.dao.HomeFinanceDaoException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectorTest {
    private final static String DB_URL = "jdbc:h2:mem:geekfactoryTest;MODE=PostgreSQL";
    private final static String DB_USER = "sa";
    private final static String DB_PASSWORD = "";

    public Connection getConnection() {
        try {
            Connection connectionTest = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connectionTest.setAutoCommit(false);
            return connectionTest;
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("Connection failed!");
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
