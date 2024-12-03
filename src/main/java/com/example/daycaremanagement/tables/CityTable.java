package com.example.daycaremanagement.tables;

import com.example.daycaremanagement.pojo.City;
import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.dao.CityDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.DBConst.*;

public class CityTable implements CityDAO {

    private static CityTable instance;
    private CityTable() throws SQLException { db = Database.getInstance(); }
    Database db;


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
        String query = "UPDATE " + TABLE_CITIES + " SET " + CITIES_COLUMN_NAME + " = ? WHERE " + CITIES_COLUMN_ID + " =  ?";
        try{
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setInt(2, city.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCity(City city) {
        String query = "DELETE FROM "+TABLE_CITIES+" WHERE "+ CITIES_COLUMN_ID + " = ? LIMIT 1";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, city.getId());
            statement.executeUpdate();
        } catch (Exception e){
            System.out.println("Invalid City ID or the ID is in use in another table.");
        }
    }

    @Override
    public void createCity(City city) {
        String query = "INSERT INTO " + TABLE_CITIES + " ("+CITIES_COLUMN_ID+", "+CITIES_COLUMN_NAME+") VALUES (0, ?);";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, city.getName());
            statement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static CityTable getInstance() throws SQLException {
        if (instance == null){
            instance = new CityTable();
        }
        return instance;
    }
}
