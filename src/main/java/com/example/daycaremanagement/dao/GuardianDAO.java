package com.example.daycaremanagement.dao;

import com.example.daycaremanagement.pojo.Guardian;

import java.util.ArrayList;

public interface GuardianDAO {
    public ArrayList<Guardian> getAllGuardians();
    public Guardian getGuardian(int id);
    public void updateGuardian(Guardian guardian);
    public void deleteGuardian(Guardian guardian);
    public void createGuardian(Guardian guardian);
}
