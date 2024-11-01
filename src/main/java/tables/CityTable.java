package tables;

import com.example.daycaremanagement.database.City;
import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.database.Position;
import dao.CityDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.Const.*;

public class CityTable implements CityDAO {
    Database db;
    {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<City> cities;

    @Override
    public ArrayList<City> getAllCities() {
        cities = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_CITIES;
        try{
            Statement getCities = db.getConnection().createStatement();
            ResultSet data = getCities.executeQuery(query);

            while(data.next()){
                cities.add(new City(
                        data.getInt(CITIES_COLUMN_ID),
                        data.getString(CITIES_COLUMN_NAME)
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public City getCity(int id) {
        String query = "SELECT * FROM "+ TABLE_CITIES + " WHERE " + CITIES_COLUMN_ID + " = " + id;
        try{
            Statement getCity = db.getConnection().createStatement();
            ResultSet data = getCity.executeQuery(query);
            if (data.next()){
                City city = new City(
                        data.getInt(CITIES_COLUMN_ID),
                        data.getString(CITIES_COLUMN_NAME)
                );
                return city;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
