package dao;

import com.example.daycaremanagement.database.Staff;

import java.util.ArrayList;

public interface StaffDAO {
    public ArrayList<Staff> getAllStaff();
    public Staff getStaff(int id);
}
