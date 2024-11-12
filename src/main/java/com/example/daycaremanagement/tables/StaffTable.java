package com.example.daycaremanagement.tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.pojo.Staff;
import com.example.daycaremanagement.dao.StaffDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.DBConst.*;

public class StaffTable implements StaffDAO {
    Database db;
    {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<Staff> staff;


    @Override
    public ArrayList<Staff> getAllStaff() {
        staff = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_STAFF;
        try{
            Statement getStudents = db.getConnection().createStatement();
            ResultSet data = getStudents.executeQuery(query);

            while(data.next()){
                staff.add(new Staff(
                        data.getInt(STAFF_COLUMN_ID),
                        data.getString(STAFF_COLUMN_FIRST_NAME),
                        data.getString(STAFF_COLUMN_LAST_NAME),
                        data.getDouble(STAFF_COLUMN_WAGE),
                        data.getInt(STAFF_COLUMN_ROOM_ID),
                        data.getInt(STAFF_COLUMN_POSITION_ID)
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return staff;
    }

    @Override
    public Staff getStaff(int id) {
        String query = "SELECT * FROM "+ TABLE_STAFF + " WHERE " + STAFF_COLUMN_ID + " = " + id;
        try{
            Statement getStaff = db.getConnection().createStatement();
            ResultSet data = getStaff.executeQuery(query);
            if (data.next()){
                Staff singleStaff = new Staff(
                        data.getInt(STAFF_COLUMN_ID),
                        data.getString(STAFF_COLUMN_FIRST_NAME),
                        data.getString(STAFF_COLUMN_LAST_NAME),
                        data.getDouble(STAFF_COLUMN_WAGE),
                        data.getInt(STAFF_COLUMN_ROOM_ID),
                        data.getInt(STAFF_COLUMN_POSITION_ID)
                );
                return singleStaff;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateStaff(Staff staff) {
        String query = "UPDATE " + TABLE_STAFF + " SET " + STAFF_COLUMN_FIRST_NAME + " = '" + staff.getFirst_name() + "', " + STAFF_COLUMN_LAST_NAME + " = '" + staff.getLast_name() +"', " + STAFF_COLUMN_WAGE + " = " + staff.getWage() + ", " + STAFF_COLUMN_ROOM_ID + " = "+ staff.getRoom_id()+", " + STAFF_COLUMN_POSITION_ID + " = " + staff.getPosition_id() +
                " WHERE " + STAFF_COLUMN_ID + " = " + staff.getId();
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStaff(Staff staff) {
        String query = "DELETE FROM "+TABLE_STAFF+" WHERE "+ STAFF_COLUMN_ID + " = " + staff.getId() + " LIMIT 1";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createStaff(Staff staff) {
        String query = "INSERT INTO " + TABLE_STAFF + " ("+STAFF_COLUMN_ID+", "+ STAFF_COLUMN_FIRST_NAME+", "+STAFF_COLUMN_LAST_NAME+", "+STAFF_COLUMN_WAGE+", "+STAFF_COLUMN_ROOM_ID+", "+STAFF_COLUMN_POSITION_ID+") VALUES (0, '"+staff.getFirst_name()+"', '"+staff.getLast_name()+"', "+staff.getWage()+", "+staff.getRoom_id()+", "+staff.getPosition_id()+");";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
