package com.example.demo3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static private String DB_URL = "jdbc:mariadb://localhost:9898/books";
    static private String USER = "root";
    static private String PASS = "password";
    static private Driver driver = new org.mariadb.jdbc.Driver();

    public static Connection initDB() throws SQLException {
        // start a connection
        Connection conn = null;
        try {
            DriverManager.registerDriver(driver);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error connecting with database. Closing application");
            System.exit(1);
            return null;
        } finally {
            DriverManager.deregisterDriver(driver);
        }
    }


}
