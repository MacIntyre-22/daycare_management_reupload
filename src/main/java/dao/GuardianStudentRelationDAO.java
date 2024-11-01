package dao;

import com.example.daycaremanagement.database.GuardianStudentRelation;

import java.util.ArrayList;

public interface GuardianStudentRelationDAO {
    public ArrayList<GuardianStudentRelation> getAllRelations();
    public GuardianStudentRelation getRelation(int id);
}
