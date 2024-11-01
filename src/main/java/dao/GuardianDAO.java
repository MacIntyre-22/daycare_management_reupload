package dao;

import com.example.daycaremanagement.database.Guardian;
import com.example.daycaremanagement.database.Staff;

import java.util.ArrayList;

public interface GuardianDAO {
    public ArrayList<Guardian> getAllGuardians();
    public Guardian getGuardian(int id);
}
