package dao;

import com.example.daycaremanagement.database.Room;
import com.example.daycaremanagement.database.Student;

import java.util.ArrayList;

public interface RoomDAO {
    public ArrayList<Room> getAllRooms();
    public Room getRoom(int id);
    public void updateRoom(Room room);
    public void deleteRoom(Room room);
    public void createRoom(Room room);
}
