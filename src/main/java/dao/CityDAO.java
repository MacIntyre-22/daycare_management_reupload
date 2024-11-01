package dao;

import com.example.daycaremanagement.database.City;

import java.util.ArrayList;

public interface CityDAO {
    public ArrayList<City> getAllCities();
    public City getCity(int id);
}
