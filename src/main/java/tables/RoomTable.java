package tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.database.Room;
import dao.RoomDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.Const.*;

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
}
