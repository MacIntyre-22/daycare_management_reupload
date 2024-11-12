package com.example.daycaremanagement.dao;

import com.example.daycaremanagement.pojo.Room;

import java.util.ArrayList;

public interface RoomDAO {
    public ArrayList<Room> getAllRooms();
    public Room getRoom(int id);
    public void updateRoom(Room room);
    public void deleteRoom(Room room);
    public void createRoom(Room room);
}
