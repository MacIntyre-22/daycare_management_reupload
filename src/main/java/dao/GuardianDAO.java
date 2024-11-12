package dao;

import com.example.daycaremanagement.database.Guardian;
import com.example.daycaremanagement.database.Staff;

import java.util.ArrayList;

public interface GuardianDAO {
    public ArrayList<Guardian> getAllGuardians();
    public Guardian getGuardian(int id);
    public void updateGuardian(Guardian guardian);
    public void deleteGuardian(Guardian guardian);
    public void createGuardian(Guardian guardian);
}
