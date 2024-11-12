package com.example.daycaremanagement.dao;

import com.example.daycaremanagement.pojo.Staff;

import java.util.ArrayList;

public interface StaffDAO {
    public ArrayList<Staff> getAllStaff();
    public Staff getStaff(int id);
    public void updateStaff(Staff staff);
    public void deleteStaff(Staff staff);
    public void createStaff(Staff staff);
}
