package com.example.daycaremanagement.database;

import java.sql.*;

import static com.example.daycaremanagement.database.Const.*;

public class Database {
    private static Database instance;
    private Connection connection;
    private static Statement statement;

    private Database(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME +"?serverTimezone=UTC", DB_USER, DB_PASS);
            createTable(TABLE_CITIES, CREATE_TABLE_CITIES, connection);
            createTable(TABLE_POSITIONS, CREATE_TABLE_POSITIONS, connection);
            createTable(TABLE_ROOMS, CREATE_TABLE_ROOMS, connection);
            createTable(TABLE_GUARDIANS, CREATE_TABLE_GUARDIANS, connection);
            createTable(TABLE_STAFF, CREATE_TABLE_STAFF, connection);
            createTable(TABLE_STUDENTS, CREATE_TABLE_STUDENTS, connection);
            createTable(TABLE_GUARDIAN_STUDENT_RELATION, CREATE_TABLE_GUARDIAN_STUDENT_RELATIONS, connection);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {return connection;}

    public static Database getInstance() throws SQLException {
        try {
            if (instance == null) {
                instance = new Database();
                statement = instance.connection.createStatement();
            }
            return instance;
        } catch (Exception e){
            System.out.println("Failed");
            return null;

        }
    }

    public String insertData(String database, String[] datatypes, Object[] data) throws SQLException {
        if (instance != null) {
            // Building the string with a for loop since the arrays are different sizes
            String sql = "INSERT INTO " + database + " (";
            // Using a for loop to add all the datatypes inside the arrays of various sizes
            for (int i = 0; i < datatypes.length; i++) {
                sql += datatypes[i];
                if (i != datatypes.length - 1) {
                    sql += ", ";
                } else {
                    sql += ")";
                }
            }
            sql += ") VALUES (";
            // Doing the same thing again
            for (int i = 0; i < data.length; i++) {
                if (data[i] instanceof Integer || data[i] instanceof Double){
                    // if it's an integer or double, don't give it quotes
                    sql+= data[i];
                } else {
                    // if it's a string, give it single quotes
                    sql+= "'" + data[i] + "'";
                }

                // If it's not the last in the array, give it a comma
                // If it's the last in the array, give it a closing bracket
                if (i != data.length - 1) {
                    sql += ", ";
                } else {
                    sql += ")";
                }
            }

            try {
                // Return amount of inserted data
                return "Inserted " + statement.executeUpdate(sql) + " records into " + database;
            } catch (Exception e){
                return "Query failed";
            }
        }
        return "Instance does not exist";
    }


    public ResultSet fetchArray(String database) throws SQLException {

        String sql = "SELECT * FROM " + database;
        if (instance != null){
            // Returning the rows from the query
            return statement.executeQuery(sql);
        }
        // if instance doesn't exist, return null
        return null;
    }

    public ResultSet find(String database, String category, String searchQuery) throws SQLException {
        String sql = "SELECT * FROM " + database + " WHERE " + category + " = '" + searchQuery + "'";
        if (instance != null){
            return statement.executeQuery(sql);
        }
        // if instance doesn't exist, return null
        return null;
    }

    // Same method that can accept an integer instead of a string in case the query is an int or double

    public ResultSet find(String database, String category, double searchQuery) throws SQLException {
        // If the double is an int, cast it to int
        if (searchQuery == Math.floor(searchQuery)){
            searchQuery = (int) searchQuery;
        }
        String sql = "SELECT * FROM " + database + " WHERE " + category + " = " + searchQuery;
        if (instance != null){
            return statement.executeQuery(sql);
        }
        // if instance doesn't exist, return null
        return null;
    }

    public String update(String database, String whereCategory, String category, double oldData, double updatedData) throws SQLException {
        // If the doubles are integers, cast them to int (to be able to use doubles in the queries without putting a double where an int should be)
        if (oldData == Math.floor(oldData)){
            oldData = (int) oldData;
        }
        if (updatedData == Math.floor(updatedData)){
            updatedData = (int) updatedData;
        }

        String sql = "UPDATE " + database + " SET " + category + "=" + updatedData + " WHERE" + whereCategory + "=" + oldData;

        if (instance != null) {
            try {
                // Returning the amount of rows effected
                return "Updated " + category + " to " + updatedData + " in " + statement.executeUpdate(sql) + " rows in " + database + "!";
            } catch (Exception e){
                //returning this if we get an error
                return "Invalid query";
            }
        }
        // if the instance isn't created, return this
        return "Instance does not exist";
    }

    // Copy of update that accepts a string instead of an int, so it can update both datatypes

    public String update(String database, String whereCategory, String category, String oldData, String updatedData) throws SQLException {
        String sql = "UPDATE " + database + " SET " + category + "='" + updatedData + "' WHERE" + whereCategory + "='" + oldData+ "'";

        if (instance != null) {
            try {
                // Returning the amount of rows effected
                return "Updated " + category + " to " + updatedData + " in " + statement.executeUpdate(sql) + " rows in " + database + "!";
            } catch (Exception e){
                //returning this if we get an error
                return "Invalid query";
            }
        }
        // if the instance isn't created, return this
        return "Instance does not exist";
    }

    public String delete(String database, int id) throws SQLException {
        // Getting the id for the statement depending on the database being used, building the statement after
        String dbId = getId(database);
        String sql = "DELETE FROM " + database + " WHERE " + dbId + "=" + id;

        if (instance != null) {
            try{
                // If the statement deleted a row, return that it succeeded, if it didn't return that it failed
                if (statement.executeUpdate(sql) == 1) {
                    return "Deleted id " + id + " successfully!";
                } else {
                    return "Deletion failed!";
                }
            } catch (Exception e){
                return "Invalid query";
            }
        }
        // if the instance isn't created, return this
        return "Instance does not exist";
    }



    // Databases have different names for their ids,
    // This returns the correct one depending on what database is being used
    // Thought I would use this in multiple methods, didn't end up needing to,
    // Keeping it as a method anyway
    private String getId(String database){
        return switch (database) {
            case "Staff" -> "staff_id";
            case "Positions" -> "position_id";
            case "Rooms" -> "room_id";
            case "Students" -> "student_id";
            case "Guardians" -> "guardian_id";
            case "Cities" -> "city_id";
            default -> "id";
        };
    }

    public void createTable(String tableName, String tableQuery, Connection connection) throws SQLException{
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
        }
    }
}
