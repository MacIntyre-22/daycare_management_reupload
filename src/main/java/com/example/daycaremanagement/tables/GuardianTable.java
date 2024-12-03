package com.example.daycaremanagement.tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.pojo.Guardian;
import com.example.daycaremanagement.dao.GuardianDAO;
import com.example.daycaremanagement.pojo.display.DisplayGuardian;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.DBConst.*;

public class GuardianTable implements GuardianDAO {
    private static GuardianTable instance;
    private GuardianTable() throws SQLException { db = Database.getInstance(); }
    Database db;

    ArrayList<Guardian> guardians;
    @Override
    public ArrayList<Guardian> getAllGuardians() {
        guardians = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_GUARDIANS;
        try{
            Statement getGuardians = db.getConnection().createStatement();
            ResultSet data = getGuardians.executeQuery(query);

            while(data.next()){
                guardians.add(new Guardian(
                        data.getInt(GUARDIANS_COLUMN_ID),
                        data.getString(GUARDIANS_COLUMN_FIRST_NAME),
                        data.getString(GUARDIANS_COLUMN_LAST_NAME),
                        data.getString(GUARDIANS_COLUMN_PHONE),
                        data.getString(GUARDIANS_COLUMN_EMAIL),
                        data.getInt(GUARDIANS_COLUMN_CITY_ID),
                        data.getInt(GUARDIANS_COLUMN_STREET_NUM),
                        data.getString(GUARDIANS_COLUMN_STREET_NAME)
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return guardians;
    }

    public ArrayList<DisplayGuardian> getAllDisplayGuardians(){
        ArrayList<DisplayGuardian> displayGuardians = new ArrayList<>();
        String query = "SELECT "+ TABLE_GUARDIANS +"."+GUARDIANS_COLUMN_ID+" as id, "
                + TABLE_GUARDIANS+"."+GUARDIANS_COLUMN_FIRST_NAME+" as first_name, "
                + TABLE_GUARDIANS+"."+GUARDIANS_COLUMN_LAST_NAME+" as last_name, "
                + TABLE_GUARDIANS+"."+GUARDIANS_COLUMN_PHONE+" as phone, "
                + TABLE_GUARDIANS+"."+GUARDIANS_COLUMN_EMAIL+" as email, "
                + TABLE_CITIES+"."+CITIES_COLUMN_NAME+" as city, "
                + TABLE_GUARDIANS+"."+GUARDIANS_COLUMN_STREET_NUM+" as street_num, "
                + TABLE_GUARDIANS+"."+GUARDIANS_COLUMN_STREET_NAME+" as street_name "
                + " from "+TABLE_GUARDIANS
                + " JOIN "+TABLE_CITIES+" on "+TABLE_GUARDIANS+"."+GUARDIANS_COLUMN_CITY_ID+" = "+TABLE_CITIES+"."+CITIES_COLUMN_ID
                + " ORDER BY id ASC";

        try {
            Statement getGuardians = db.getConnection().createStatement();
            ResultSet data = getGuardians.executeQuery(query);
            while(data.next()) {
                displayGuardians.add(new DisplayGuardian(data.getInt("id"),
                        data.getString("first_name"),
                        data.getString("last_name"),
                        data.getString("phone"),
                        data.getString("email"),
                        data.getString("city"),
                        data.getInt("street_num"),
                        data.getString("street_name")));
            }
            getGuardians.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return displayGuardians;
    }

    @Override
    public Guardian getGuardian(int id) {
        String query = "SELECT * FROM " + TABLE_GUARDIANS + " WHERE " + GUARDIANS_COLUMN_ID + " = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet data = statement.executeQuery();
            if (data.next()){
                Guardian guardian = new Guardian(
                        data.getInt(GUARDIANS_COLUMN_ID),
                        data.getString(GUARDIANS_COLUMN_FIRST_NAME),
                        data.getString(GUARDIANS_COLUMN_LAST_NAME),
                        data.getString(GUARDIANS_COLUMN_PHONE),
                        data.getString(GUARDIANS_COLUMN_EMAIL),
                        data.getInt(GUARDIANS_COLUMN_CITY_ID),
                        data.getInt(GUARDIANS_COLUMN_STREET_NUM),
                        data.getString(GUARDIANS_COLUMN_STREET_NAME)
                );
                return guardian;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Guardian getGuardianByRelation(int id) {
        String query = "SELECT * FROM "+ TABLE_GUARDIANS + " WHERE " + TABLE_GUARDIAN_STUDENT_RELATION +"."+GUARDIAN_STUDENT_RELATION_COLUMN_ID + " = ?";
        try{
            PreparedStatement getGuardian = db.getConnection().prepareStatement(query);
            getGuardian.setInt(1, id);
            ResultSet data = getGuardian.executeQuery();
            if (data.next()){
                Guardian guardian = new Guardian(
                        data.getInt(GUARDIANS_COLUMN_ID),
                        data.getString(GUARDIANS_COLUMN_FIRST_NAME),
                        data.getString(GUARDIANS_COLUMN_LAST_NAME),
                        data.getString(GUARDIANS_COLUMN_PHONE),
                        data.getString(GUARDIANS_COLUMN_EMAIL),
                        data.getInt(GUARDIANS_COLUMN_CITY_ID),
                        data.getInt(GUARDIANS_COLUMN_STREET_NUM),
                        data.getString(GUARDIANS_COLUMN_STREET_NAME)
                );
                return guardian;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateGuardian(Guardian guardian) {
        String query = "UPDATE " + TABLE_GUARDIANS + " SET " + GUARDIANS_COLUMN_FIRST_NAME + " = ?, " + GUARDIANS_COLUMN_LAST_NAME + " = ?, " + GUARDIANS_COLUMN_PHONE + " = ?, " + GUARDIANS_COLUMN_EMAIL + " = ?, " + GUARDIANS_COLUMN_CITY_ID + " = ?, " + GUARDIANS_COLUMN_STREET_NUM + " = ?, " + GUARDIANS_COLUMN_STREET_NAME + " = ?" +
                " WHERE " + GUARDIANS_COLUMN_ID + " = ?";
        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, guardian.getFirst_name());
            statement.setString(2, guardian.getLast_name());
            statement.setString(3, guardian.getPhone());
            statement.setString(4, guardian.getEmail());
            statement.setInt(5, guardian.getCity_id());
            statement.setInt(6, guardian.getStreet_num());
            statement.setString(7, guardian.getStreet_name());
            statement.setInt(8, guardian.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGuardian(Guardian guardian) {
        String query = "DELETE FROM "+TABLE_GUARDIANS+" WHERE "+ GUARDIANS_COLUMN_ID + " = ? LIMIT 1";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, guardian.getId());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createGuardian(Guardian guardian) {
        String query = "INSERT INTO " + TABLE_GUARDIANS + " (" +GUARDIANS_COLUMN_ID+", "+ GUARDIANS_COLUMN_FIRST_NAME + ", "+GUARDIANS_COLUMN_LAST_NAME+", "+GUARDIANS_COLUMN_PHONE+", "+GUARDIANS_COLUMN_EMAIL+", "+GUARDIANS_COLUMN_CITY_ID+", "+GUARDIANS_COLUMN_STREET_NUM+", "+GUARDIANS_COLUMN_STREET_NAME+") VALUES (0, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, guardian.getFirst_name());
            statement.setString(2, guardian.getLast_name());
            statement.setString(3, guardian.getPhone());
            statement.setString(4, guardian.getEmail());
            statement.setInt(5, guardian.getCity_id());
            statement.setInt(6, guardian.getStreet_num());
            statement.setString(7, guardian.getStreet_name());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GuardianTable getInstance() throws SQLException {
        if (instance == null){
            instance = new GuardianTable();
        }
        return instance;
    }
}
