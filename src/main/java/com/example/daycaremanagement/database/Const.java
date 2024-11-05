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



    // Insert statement arrays

    // TODO: Create insert arrays for other tables, change columns to the constant strings
    String[] insertRooms = {
            "INSERT INTO rooms (room_id, room_name) VALUES (0, 'english');",
            "INSERT INTO rooms (room_id, room_name) VALUES (0, 'math');",
            "INSERT INTO rooms (room_id, room_name) VALUES (0, 'science');",
            "INSERT INTO rooms (room_id, room_name) VALUES (0, 'library');",
            "INSERT INTO rooms (room_id, room_name) VALUES (0, 'office');",
            "INSERT INTO rooms (room_id, room_name) VALUES (0, 'gym');",
            "INSERT INTO rooms (room_id, room_name) VALUES (0, 'geography');",
            "INSERT INTO rooms (room_id, room_name) VALUES (0, 'history');"
    };

    String[] insertStudents = {
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Andrea', 'Pittendreigh', STR_TO_DATE('11-18-2018', '%m-%d-%Y'), 8);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Lowrance', 'Mitskevich', STR_TO_DATE('7-19-2019', '%m-%d-%Y'), 8);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Allister', 'Orae', STR_TO_DATE('4-1-2019', '%m-%d-%Y'), 1);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Tod', 'Coffelt', STR_TO_DATE('7-6-2019', '%m-%d-%Y'), 3);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Arman', 'Tarte', STR_TO_DATE('3-8-2021', '%m-%d-%Y'), 3);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Walden', 'Scirman', STR_TO_DATE('9-24-2019', '%m-%d-%Y'), 4);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Lethia', 'Worvell', STR_TO_DATE('7-12-2017', '%m-%d-%Y'), 4);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Raymond', 'Cutcliffe', STR_TO_DATE('8-16-2019', '%m-%d-%Y'), 2);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Rudolph', 'Gurnett', STR_TO_DATE('2-28-2018', '%m-%d-%Y'), 1);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Fabian', 'Ramel', STR_TO_DATE('1-19-2019', '%m-%d-%Y'), 4);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Abbye', 'Dunridge', STR_TO_DATE('2-9-2018', '%m-%d-%Y'), 4);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Humberto', 'Bartoshevich', STR_TO_DATE('1-10-2019', '%m-%d-%Y'), 2);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Nicolle', 'Currey', STR_TO_DATE('1-23-2020', '%m-%d-%Y'), 8);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Bettine', 'Isacq', STR_TO_DATE('4-26-2017', '%m-%d-%Y'), 6);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Tim', 'Magog', STR_TO_DATE('7-29-2018', '%m-%d-%Y'), 2);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Archambault', 'Fishwick', STR_TO_DATE('3-4-2021', '%m-%d-%Y'), 9);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Magda', 'Domerc', STR_TO_DATE('2-26-2018', '%m-%d-%Y'), 3);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Stephen', 'Myhan', STR_TO_DATE('12-6-2017', '%m-%d-%Y'), 2);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Gertie', 'Laurencot', STR_TO_DATE('11-19-2020', '%m-%d-%Y'), 6);",
            "INSERT INTO students (student_id, first_name, last_name, birthdate, room_id) VALUES (0, 'Kale', 'Jeaffreson', STR_TO_DATE('10-13-2017', '%m-%d-%Y'), 2);"
    };
}
