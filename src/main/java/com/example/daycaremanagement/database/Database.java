package com.example.daycaremanagement.database;

import java.sql.*;
import java.util.Arrays;

import static com.example.daycaremanagement.database.DBConst.*;

public class Database {
    private static Database instance;
    private Connection connection;

    /**
     * This connects the user to the database and store it into a connection
     * This will also try to make all the tables into the user's database
     */
    private Database(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME +"?serverTimezone=UTC", DB_USER, DB_PASS);
            createTable(TABLE_CITIES, CREATE_TABLE_CITIES, connection, INSERT_CITIES);
            createTable(TABLE_POSITIONS, CREATE_TABLE_POSITIONS, connection, INSERT_POSITIONS);
            createTable(TABLE_ROOMS, CREATE_TABLE_ROOMS, connection, INSERT_ROOMS);
            createTable(TABLE_GUARDIANS, CREATE_TABLE_GUARDIANS, connection, INSERT_GUARDIANS);
            createTable(TABLE_STAFF, CREATE_TABLE_STAFF, connection, INSERT_STAFF);
            createTable(TABLE_STUDENTS, CREATE_TABLE_STUDENTS, connection, INSERT_STUDENTS);
            createTable(TABLE_GUARDIAN_STUDENT_RELATION, CREATE_TABLE_GUARDIAN_STUDENT_RELATIONS, connection, INSERT_GUARDIAN_STUDENT_RELATION);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This will get the connection that we store in the constructor
     * @return Connection
     */
    public Connection getConnection() {return connection;}

    /**
     * This Checks if there is a connection
     * @return it will return true if the connection doesn't fail, and false if it does
     */
    public static boolean checkConnection()  {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME +"?serverTimezone=UTC", DB_USER, DB_PASS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This is to get an instance of the database
     * @return The instance of the database
     * @throws SQLException but will print to the terminal that it failed
     */
    public static Database getInstance() throws SQLException {
        try {
            if (instance == null) {
                instance = new Database();
            }
            return instance;
        } catch (Exception e){
            System.out.println("Database creation Failed");
            return null;

        }
    }

    public static void clearInstance(){
        instance = null;
    }

    /**
     * This is try to create the tables into the user's database
     * @param tableName
     * @param tableQuery
     * @param connection
     * @param inserts
     * @throws SQLException
     */
    public void createTable(String tableName, String tableQuery, Connection connection, String[] inserts) throws SQLException{
        Statement createTable;
        DatabaseMetaData md = connection.getMetaData();
        // Look inside the database for a table named tableName
        ResultSet resultSet = md.getTables(DB_NAME, null, tableName, null);
        // If the result set contains data
        if (resultSet.next()){
            System.out.println(tableName + " table already exists");
        } else {
            createTable = connection.createStatement();
            createTable.execute(tableQuery);
            System.out.println(tableName + " table has been created!");
            if (inserts.length>0){
                Statement statement = connection.createStatement();
                for (int i = 0; i < inserts.length; i++) {
                    statement.execute(inserts[i]);
                }
                statement.close();
            }
        }
    }
}
