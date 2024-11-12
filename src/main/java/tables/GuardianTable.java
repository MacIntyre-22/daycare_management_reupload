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

    @Override
    public void updateGuardian(Guardian guardian) {
        String query = "UPDATE " + TABLE_GUARDIANS + " SET " + GUARDIANS_COLUMN_FIRST_NAME + " = '" + guardian.getFirst_name() + "', " + GUARDIANS_COLUMN_LAST_NAME + " = '" + guardian.getLast_name() +"', " + GUARDIANS_COLUMN_PHONE + " = " + guardian.getPhone() + ", " + GUARDIANS_COLUMN_EMAIL + " = '"+ guardian.getEmail()+"', " + GUARDIANS_COLUMN_CITY_ID + " = " + guardian.getCity_id() + ", " + GUARDIANS_COLUMN_STREET_NUM + " = " + guardian.getStreet_num() + ", " + GUARDIANS_COLUMN_STREET_NAME + " = '" + guardian.getStreet_name() + "'" +
                " WHERE " + GUARDIANS_COLUMN_ID + " = " + guardian.getId();
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGuardian(Guardian guardian) {
        String query = "DELETE FROM "+TABLE_GUARDIANS+" WHERE "+ GUARDIANS_COLUMN_ID + " = " + guardian.getId() + " LIMIT 1";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createGuardian(Guardian guardian) {
        String query = "INSERT INTO " + TABLE_GUARDIANS + " ("+GUARDIANS_COLUMN_ID+", "+ GUARDIANS_COLUMN_FIRST_NAME+", "+GUARDIANS_COLUMN_LAST_NAME+", "+GUARDIANS_COLUMN_PHONE+", "+GUARDIANS_COLUMN_EMAIL+", "+GUARDIANS_COLUMN_CITY_ID+", "+GUARDIANS_COLUMN_STREET_NUM+", "+GUARDIANS_COLUMN_STREET_NAME+") VALUES (0, '"+guardian.getFirst_name()+"', '"+guardian.getLast_name()+"', "+guardian.getPhone()+", '"+guardian.getEmail()+"', "+guardian.getCity_id()+", "+guardian.getStreet_num()+", '"+guardian.getStreet_name()+"');";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
