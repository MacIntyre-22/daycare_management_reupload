package com.example.daycaremanagement.database;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.example.daycaremanagement.database.DbConst.*;

public class Database {
    private static Database instance;
    private Connection connection;

    private Database(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME +"?serverTimezone=UTC", DB_USER, DB_PASS);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {return connection;}

    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

}
