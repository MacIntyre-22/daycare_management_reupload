package dao;

import com.example.daycaremanagement.database.Room;

import java.util.ArrayList;

public interface RoomDAO {
    public ArrayList<Room> getAllRooms();
    public Room getRoom(int id);
}
