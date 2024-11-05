package com.example.daycaremanagement.database;

public class Const {
    // take this out later, read the login info from a file
    // temporary
    public static final String DB_NAME = "azarankinmd";
    public static final String DB_USER = "azarankin";
    public static final String DB_PASS = "";


    // Students table
    public static final String TABLE_STUDENTS = "students";
    public static final String STUDENTS_COLUMN_ID = "student_id";
    public static final String STUDENTS_COLUMN_FIRST_NAME = "first_name";
    public static final String STUDENTS_COLUMN_LAST_NAME = "last_name";
    public static final String STUDENTS_COLUMN_BIRTHDATE = "birthdate";
    public static final String STUDENTS_COLUMN_ROOM_ID = "room_id";

    // Staff table
    public static final String TABLE_STAFF = "staff";
    public static final String STAFF_COLUMN_ID = "staff_id";
    public static final String STAFF_COLUMN_FIRST_NAME = "first_name";
    public static final String STAFF_COLUMN_LAST_NAME = "last_name";
    public static final String STAFF_COLUMN_WAGE = "wage";
    public static final String STAFF_COLUMN_ROOM_ID = "room_id";
    public static final String STAFF_COLUMN_POSITION_ID = "position_id";

    // Rooms table
    public static final String TABLE_ROOMS = "rooms";
    public static final String ROOMS_COLUMN_ID = "room_id";
    public static final String ROOMS_COLUMN_NAME = "room_name";

    // Positions table
    public static final String TABLE_POSITIONS = "positions";
    public static final String POSITIONS_COLUMN_ID = "positions_id";
    public static final String POSITIONS_COLUMN_NAME = "positions_name";

    // Guardians table
    public static final String TABLE_GUARDIANS = "guardians";
    public static final String GUARDIANS_COLUMN_ID = "guardian_id";
    public static final String GUARDIANS_COLUMN_FIRST_NAME = "first_name";
    public static final String GUARDIANS_COLUMN_LAST_NAME = "last_name";
    public static final String GUARDIANS_COLUMN_PHONE = "phone";
    public static final String GUARDIANS_COLUMN_EMAIL = "email";
    public static final String GUARDIANS_COLUMN_CITY_ID = "city_id";
    public static final String GUARDIANS_COLUMN_STREET_NUM = "street_num";
    public static final String GUARDIANS_COLUMN_STREET_NAME = "street_name";

    // Guardian Student relation table
    public static final String TABLE_GUARDIAN_STUDENT_RELATION = "guardian_student_relation";
    public static final String GUARDIAN_STUDENT_RELATION_COLUMN_ID = "id";
    public static final String GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID = "guardian_id";
    public static final String GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID = "student_id";

    // Cities table
    public static final String TABLE_CITIES = "cities";
    public static final String CITIES_COLUMN_ID = "city_id";
    public static final String CITIES_COLUMN_NAME = "city_name";

    // Create Student table
    public static final String CREATE_TABLE_STUDENTS = " CREATE TABLE " + TABLE_STUDENTS + " ("+
            STUDENTS_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
            STUDENTS_COLUMN_FIRST_NAME + " VARCHAR(20), " +
            STUDENTS_COLUMN_LAST_NAME + " VARCHAR(20), " +
            STUDENTS_COLUMN_BIRTHDATE + " DATE, " +
            STUDENTS_COLUMN_ROOM_ID + " int, " +
            "PRIMARY KEY(" + STUDENTS_COLUMN_ID + "), "+
            "FOREIGN KEY(" + STUDENTS_COLUMN_ROOM_ID + ")"+
                " REFERENCES" + TABLE_ROOMS + "(" + ROOMS_COLUMN_ID + "));";

    // Create Staff table
    public static final String CREATE_TABLE_STAFF = " CREATE TABLE " + TABLE_STAFF + " ("+
            STAFF_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
            STAFF_COLUMN_FIRST_NAME + " VARCHAR(20), " +
            STAFF_COLUMN_LAST_NAME + " VARCHAR(20), " +
            STAFF_COLUMN_WAGE + " DECIMAL(5, 2), " +
            STAFF_COLUMN_ROOM_ID + " int, " +
            STAFF_COLUMN_POSITION_ID + " int, " +
            "PRIMARY KEY(" + STAFF_COLUMN_ID + "), " +
            "FOREIGN KEY(" + STAFF_COLUMN_ROOM_ID + ")"+
                " REFERENCES" + TABLE_ROOMS + "(" + ROOMS_COLUMN_ID + ")," +
            "FOREIGN KEY(" + STAFF_COLUMN_ROOM_ID + ")"+
                " REFERENCES" + TABLE_POSITIONS + "(" + POSITIONS_COLUMN_ID + "));";

    // Create Rooms table
    public static final String CREATE_TABLE_ROOMS = " CREATE TABLE " + TABLE_ROOMS + " ("+
            ROOMS_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
            ROOMS_COLUMN_NAME + " VARCHAR(20), " +
            "PRIMARY KEY(" + ROOMS_COLUMN_ID + "));";

    // Create Positions table
    public static final String CREATE_TABLE_POSITIONS = " CREATE TABLE " + TABLE_POSITIONS + " ("+
            POSITIONS_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
            POSITIONS_COLUMN_NAME + " VARCHAR(20), " +
            "PRIMARY KEY(" + POSITIONS_COLUMN_ID + "));";

    // Create Guardians table
    public static final String CREATE_TABLE_GUARDIANS = " CREATE TABLE " + TABLE_GUARDIANS + " ("+
            GUARDIANS_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
            GUARDIANS_COLUMN_FIRST_NAME + " VARCHAR(20), " +
            GUARDIANS_COLUMN_LAST_NAME + " VARCHAR(20), " +
            GUARDIANS_COLUMN_PHONE + " VARCHAR(15), " +
            GUARDIANS_COLUMN_EMAIL + " VARCHAR(50), " +
            GUARDIANS_COLUMN_CITY_ID + " int, " +
            GUARDIANS_COLUMN_STREET_NUM + " int, " +
            GUARDIANS_COLUMN_STREET_NAME + " VARCHAR(20), " +
            "PRIMARY KEY(" + GUARDIANS_COLUMN_ID + "), " +
            "FOREIGN KEY (" + GUARDIANS_COLUMN_CITY_ID + ")"+
                " REFERENCES " + TABLE_CITIES + "(" + CITIES_COLUMN_ID + "));";

    // Create guardian student relations table
    public static final String CREATE_TABLE_GUARDIAN_STUDENT_RELATIONS = " CREATE TABLE " + TABLE_GUARDIAN_STUDENT_RELATION + " ("+
            GUARDIAN_STUDENT_RELATION_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
            GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID + " int NOT NULL, " +
            GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID + " int NOT NULL, " +
            "PRIMARY KEY(" + GUARDIAN_STUDENT_RELATION_COLUMN_ID + "), " +
            "FOREIGN KEY (" + GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID + ")"+
                " REFERENCES " + TABLE_GUARDIANS + "(" + GUARDIANS_COLUMN_ID + ")," +
            "FOREIGN KEY (" + GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID + ")"+
                    " REFERENCES " + TABLE_STUDENTS + "(" + STUDENTS_COLUMN_ID + "));";

    // Create cities table
    public static final String CREATE_TABLE_CITIES = " CREATE TABLE " + TABLE_CITIES + " ("+
            CITIES_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
            CITIES_COLUMN_NAME + " VARCHAR(20), " +
            "PRIMARY KEY(" + CITIES_COLUMN_ID + "));";
}
