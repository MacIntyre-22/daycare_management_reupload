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

    @Override
    public void updateCity(City city) {
        String query = "UPDATE " + TABLE_CITIES + " SET " + CITIES_COLUMN_NAME + " = '" + city.getName() + "' WHERE " + CITIES_COLUMN_ID + " = " + city.getId();
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCity(City city) {
        String query = "DELETE FROM "+TABLE_CITIES+" WHERE "+ CITIES_COLUMN_ID + " = " + city.getId() + " LIMIT 1";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createCity(City city) {
        String query = "INSERT INTO " + TABLE_CITIES + " ("+CITIES_COLUMN_ID+", "+CITIES_COLUMN_NAME+") VALUES (0, '" + city.getName() +"');";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
