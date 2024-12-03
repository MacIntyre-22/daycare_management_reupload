package com.example.daycaremanagement.tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.pojo.Room;
import com.example.daycaremanagement.dao.RoomDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.DBConst.*;

public class RoomTable implements RoomDAO {
    private static RoomTable instance;
    private RoomTable() throws SQLException { db = Database.getInstance(); }
    Database db;

    ArrayList<Room> rooms;

    @Override
    public ArrayList<Room> getAllRooms() {
        rooms = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_ROOMS;
        try{
            Statement getRooms = db.getConnection().createStatement();
            ResultSet data = getRooms.executeQuery(query);

            while(data.next()){
                rooms.add(new Room(
                        data.getInt(ROOMS_COLUMN_ID),
                        data.getString(ROOMS_COLUMN_NAME)
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public Room getRoom(int id) {
        String query = "SELECT * FROM "+ TABLE_ROOMS + " WHERE " + ROOMS_COLUMN_ID + " = ?";
        try{
            PreparedStatement getRoom = db.getConnection().prepareStatement(query);
            getRoom.setInt(1, id);
            ResultSet data = getRoom.executeQuery();
            if (data.next()){
                Room room = new Room(
                        data.getInt(ROOMS_COLUMN_ID),
                        data.getString(ROOMS_COLUMN_NAME)
                );
                return room;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateRoom(Room room) {
        String query = "UPDATE " + TABLE_ROOMS + " SET " + ROOMS_COLUMN_NAME + " = ? WHERE " + ROOMS_COLUMN_ID + " = ?";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, room.getName());
            statement.setInt(1, room.getId());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRoom(Room room) {
        String query = "DELETE FROM "+TABLE_ROOMS+" WHERE "+ ROOMS_COLUMN_ID + " = ? LIMIT 1";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, room.getId());
            statement.executeUpdate();
        } catch (Exception e){
            System.out.println("Invalid Room ID or the ID is in use in another table");
        }
    }

    @Override
    public void createRoom(Room room) {
        String query = "INSERT INTO " + TABLE_ROOMS + " ("+ROOMS_COLUMN_ID+", "+ROOMS_COLUMN_NAME+") VALUES (0, ?);";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, room.getName());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static RoomTable getInstance() throws SQLException {
        if (instance == null){
            instance = new RoomTable();
        }
        return instance;
    }
}
