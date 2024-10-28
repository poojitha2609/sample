package org.example.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "pooja";
        return DriverManager.getConnection(url, username, password);
    }
}
