package com.example.daycaremanagement.database;

import java.sql.*;

import static com.example.daycaremanagement.database.DBConst.*;

public class Database {
    private static Database instance;
    private Connection connection;

    private Database(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME +"?serverTimezone=UTC", DB_USER, DB_PASS);
            if (connection != null) {
                createTable(TABLE_CITIES, CREATE_TABLE_CITIES, connection, INSERT_CITIES);
                createTable(TABLE_POSITIONS, CREATE_TABLE_POSITIONS, connection, INSERT_POSITIONS);
                createTable(TABLE_ROOMS, CREATE_TABLE_ROOMS, connection, INSERT_ROOMS);
                createTable(TABLE_GUARDIANS, CREATE_TABLE_GUARDIANS, connection, INSERT_GUARDIANS);
                createTable(TABLE_STAFF, CREATE_TABLE_STAFF, connection, INSERT_STAFF);
                createTable(TABLE_STUDENTS, CREATE_TABLE_STUDENTS, connection, INSERT_STUDENTS);
                createTable(TABLE_GUARDIAN_STUDENT_RELATION, CREATE_TABLE_GUARDIAN_STUDENT_RELATIONS, connection, INSERT_GUARDIAN_STUDENT_RELATION);
            } else {
                System.out.println("no connection");
                System.out.println(connection);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {return connection;}

    public static Database getInstance() throws SQLException {
        try {
            if (instance == null) {
                instance = new Database();
            }
            return instance;
        } catch (Exception e){
            System.out.println("Failed");
            return null;

        }
    }

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
