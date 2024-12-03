package com.example.daycaremanagement.tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.pojo.Staff;
import com.example.daycaremanagement.dao.StaffDAO;
import com.example.daycaremanagement.pojo.display.DisplayStaff;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.DBConst.*;

public class StaffTable implements StaffDAO {
    private static StaffTable instance;
    private StaffTable() throws SQLException { db = Database.getInstance(); }
    Database db;

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

    public ArrayList<DisplayStaff> getAllDisplayStaff(){
        ArrayList<DisplayStaff> displayStaff = new ArrayList<>();
        String query = "SELECT "+TABLE_STAFF+"."+STAFF_COLUMN_ID+" as id, "
                + TABLE_STAFF+"."+STAFF_COLUMN_FIRST_NAME+" as first_name, "
                + TABLE_STAFF+"."+STAFF_COLUMN_LAST_NAME+" as last_name, "
                + TABLE_STAFF+"."+STAFF_COLUMN_WAGE+" as wage,"
                + TABLE_ROOMS+"."+ROOMS_COLUMN_NAME+" as room, "
                + TABLE_POSITIONS+"."+POSITIONS_COLUMN_NAME+" as position "
                + " from "+TABLE_STAFF
                + " JOIN "+TABLE_POSITIONS+" on "+TABLE_STAFF+"."+STAFF_COLUMN_POSITION_ID+" = "+TABLE_POSITIONS+"."+POSITIONS_COLUMN_ID
                + " JOIN "+TABLE_ROOMS+" on "+TABLE_STAFF+"."+STAFF_COLUMN_ROOM_ID+" = "+TABLE_ROOMS+"."+ROOMS_COLUMN_ID
                + " ORDER BY id ASC";

        try {
            Statement getStaff = db.getConnection().createStatement();
            ResultSet data = getStaff.executeQuery(query);
            while(data.next()) {
                displayStaff.add(new DisplayStaff(data.getInt("id"),
                        data.getString("first_name"),
                        data.getString("last_name"),
                        data.getDouble("wage"),
                        data.getString("room"),
                        data.getString("position")));
            }
            getStaff.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return displayStaff;
    }

    @Override
    public Staff getStaff(int id) {
        String query = "SELECT * FROM "+ TABLE_STAFF + " WHERE " + STAFF_COLUMN_ID + " = ?";
        try{
            PreparedStatement getStaff = db.getConnection().prepareStatement(query);
            getStaff.setInt(1, id);
            ResultSet data = getStaff.executeQuery();
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
        String query = "UPDATE " + TABLE_STAFF + " SET " + STAFF_COLUMN_FIRST_NAME + " = ?, " + STAFF_COLUMN_LAST_NAME + " = ?, " + STAFF_COLUMN_WAGE + " = ?, " + STAFF_COLUMN_ROOM_ID + " = ?, " + STAFF_COLUMN_POSITION_ID + " = ?" +
                " WHERE " + STAFF_COLUMN_ID + " = ?";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, staff.getFirst_name());
            statement.setString(2, staff.getLast_name());
            statement.setDouble(3, staff.getWage());
            statement.setInt(4, staff.getRoom_id());
            statement.setInt(5, staff.getPosition_id());
            statement.setInt(6, staff.getId());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStaff(Staff staff) {
        String query = "DELETE FROM "+TABLE_STAFF+" WHERE "+ STAFF_COLUMN_ID + " = ? LIMIT 1";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, staff.getId());
            statement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createStaff(Staff staff) {
        String query = "INSERT INTO " + TABLE_STAFF + " ("+STAFF_COLUMN_ID+", "+ STAFF_COLUMN_FIRST_NAME+", "+STAFF_COLUMN_LAST_NAME+", "+STAFF_COLUMN_WAGE+", "+STAFF_COLUMN_ROOM_ID+", "+STAFF_COLUMN_POSITION_ID+") VALUES (0, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, staff.getFirst_name());
            statement.setString(2, staff.getLast_name());
            statement.setDouble(3, staff.getWage());
            statement.setInt(4, staff.getRoom_id());
            statement.setInt(5, staff.getPosition_id());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static StaffTable getInstance() throws SQLException {
        if (instance == null){
            instance = new StaffTable();
        }
        return instance;
    }
}
