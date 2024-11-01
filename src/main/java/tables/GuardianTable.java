package tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.database.Guardian;
import dao.GuardianDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.Const.*;

public class GuardianTable implements GuardianDAO {
    Database db;
    {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    @Override
    public Guardian getGuardian(int id) {
        String query = "SELECT * FROM "+ TABLE_GUARDIANS + " WHERE " + GUARDIANS_COLUMN_ID + " = " + id;
        try{
            Statement getGuardian = db.getConnection().createStatement();
            ResultSet data = getGuardian.executeQuery(query);
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
}
