package lk.codeschool.libry_management_system.controller.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;
    final Connection connection;     //final use krhm connection hadenne 1y

    private DBConnection() throws ClassNotFoundException, SQLException {         //private use krnwa wena thanaka access krnna bariwenna
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_project", "root", "4187");//data base connecting
    }

    public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
        if(dbConnection==null){
            dbConnection = new DBConnection();//methana wenne dbConnection eka null num aluth ekk hadena eka
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
