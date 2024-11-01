package tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.database.Staff;
import dao.StaffDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.Const.*;

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
}
