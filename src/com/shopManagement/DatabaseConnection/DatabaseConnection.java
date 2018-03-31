package com.shopManagement.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final String USERNAME = "root";
    private final String PASSWORD = "admin";
    private final String CONN = "jdbc:";

    private static final String SQLITECONN = "jdbc:sqlite:medicalDB.sqlite";

    public static Connection getConnection(){
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQLITECONN);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
