package dao;

import com.example.daycaremanagement.database.GuardianStudentRelation;

import java.util.ArrayList;

public interface GuardianStudentRelationDAO {
    public ArrayList<GuardianStudentRelation> getAllRelations();
    public GuardianStudentRelation getRelation(int id);
    public void updateRelation(GuardianStudentRelation relation);
    public void deleteRelation(GuardianStudentRelation relation);
    public void createRelation(GuardianStudentRelation relation);
}
