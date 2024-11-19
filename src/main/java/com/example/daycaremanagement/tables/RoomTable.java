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
    Database db;
    {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
        String query = "SELECT * FROM "+ TABLE_ROOMS + " WHERE " + ROOMS_COLUMN_ID + " = " + id;
        try{
            Statement getRoom = db.getConnection().createStatement();
            ResultSet data = getRoom.executeQuery(query);
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
        String query = "UPDATE " + TABLE_ROOMS + " SET " + ROOMS_COLUMN_NAME + " = '" + room.getName() + "' WHERE " + ROOMS_COLUMN_ID + " = " + room.getId();
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRoom(Room room) {
        String query = "DELETE FROM "+TABLE_ROOMS+" WHERE "+ ROOMS_COLUMN_ID + " = " + room.getId() + " LIMIT 1";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createRoom(Room room) {
        String query = "INSERT INTO " + TABLE_ROOMS + " ("+ROOMS_COLUMN_ID+", "+ROOMS_COLUMN_NAME+") VALUES (0, '" + room.getName() +"');";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
