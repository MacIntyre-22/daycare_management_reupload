package com.example.daycaremanagement.database;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.example.daycaremanagement.database.DbConst.*;

public class Database {
    private static Database instance;
    private Connection connection;

    private Database() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME +"?serverTimezone=UTC", DB_USER, DB_PASS);
    }

    public Connection getConnection() {return connection;}

    public static Database getInstance() throws Exception{
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

}
