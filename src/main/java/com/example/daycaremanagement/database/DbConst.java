package com.example.daycaremanagement.database;

public class DbConst {
    // take this out later, read the login info from a file
    // temporary
    public static String DB_NAME = "";
    public static String DB_USER = "";
    public static String DB_PASS = "";

    // Setters and Getters

    public static void setDbName(String dbName) {
        DB_NAME = dbName;
    }

    public static void setDbUser(String dbUser) {
        DB_USER = dbUser;
    }

    public static void setDbPass(String dbPass) {
        DB_PASS = dbPass;
    }
}
