package abdu.tumg.utils;

import java.sql.*;

public class JdbcUtils {
    private static String URL_BASE = "jdbc:mysql://localhost:3306/crud_table";
    private static String USER = "root";
    private static String PASSWORD = "Zvezda002";

    private JdbcUtils() {
    }

    private static Connection connection;

    public static PreparedStatement getPreparedStatement(String sql){
        try {
            connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }

    public static void closeConnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }
    public static PreparedStatement getPreparedStatementWithKeys(String sql){
        try {
            connection = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
            return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e){
            e.getErrorCode();
        }
        return null;
    }
}
