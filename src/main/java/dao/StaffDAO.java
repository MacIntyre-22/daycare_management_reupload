package dao;

import com.example.daycaremanagement.database.Staff;
import com.example.daycaremanagement.database.Student;

import java.util.ArrayList;

public interface StaffDAO {
    public ArrayList<Staff> getAllStaff();
    public Staff getStaff(int id);
    public void updateStaff(Staff staff);
    public void deleteStaff(Staff staff);
    public void createStaff(Staff staff);
}
