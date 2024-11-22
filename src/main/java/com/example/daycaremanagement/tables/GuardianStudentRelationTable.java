package com.example.daycaremanagement.tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.pojo.GuardianStudentRelation;
import com.example.daycaremanagement.dao.GuardianStudentRelationDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.DBConst.*;

public class GuardianStudentRelationTable implements GuardianStudentRelationDAO {
    private static GuardianStudentRelationTable instance;
    private GuardianStudentRelationTable() throws SQLException { db = Database.getInstance(); }
    Database db;

    ArrayList<GuardianStudentRelation> relations;
    @Override
    public ArrayList<GuardianStudentRelation> getAllRelations() {
        relations = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_GUARDIAN_STUDENT_RELATION;
        try{
            Statement getCities = db.getConnection().createStatement();
            ResultSet data = getCities.executeQuery(query);

            while(data.next()){
                relations.add(new GuardianStudentRelation(
                        data.getInt(GUARDIAN_STUDENT_RELATION_COLUMN_ID),
                        data.getInt(GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID),
                        data.getInt(GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID)
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return relations;
    }

    @Override
    public GuardianStudentRelation getRelation(int id) {
        String query = "SELECT * FROM "+ TABLE_GUARDIAN_STUDENT_RELATION + " WHERE " + GUARDIAN_STUDENT_RELATION_COLUMN_ID + " = " + id;
        try{
            Statement getRelation = db.getConnection().createStatement();
            ResultSet data = getRelation.executeQuery(query);
            if (data.next()){
                GuardianStudentRelation relation = new GuardianStudentRelation(
                        data.getInt(GUARDIAN_STUDENT_RELATION_COLUMN_ID),
                        data.getInt(GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID),
                        data.getInt(GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID)
                );
                return relation;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // TODO: Rework this
    // true if searching by guardian id, false if searching by student id
    public ArrayList<GuardianStudentRelation> getRelationByEitherId(int searchId, boolean guardian) {
        String searchCriteria;
        relations = new ArrayList<>();
        if (guardian) { searchCriteria = GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID; } else { searchCriteria = GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID; }
        String query = "SELECT * FROM "+ TABLE_GUARDIAN_STUDENT_RELATION + " WHERE " + searchCriteria + " = " + searchId;
        try{
            Statement getRelation = db.getConnection().createStatement();
            ResultSet data = getRelation.executeQuery(query);
            while (data.next()){
                GuardianStudentRelation relation = new GuardianStudentRelation(
                        data.getInt(GUARDIAN_STUDENT_RELATION_COLUMN_ID),
                        data.getInt(GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID),
                        data.getInt(GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID)
                );
                relations.add(relation);
            }
            return relations;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateRelation(GuardianStudentRelation relation) {
        String query = "UPDATE "+TABLE_GUARDIAN_STUDENT_RELATION + " SET " + GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID + " = " + relation.getStudent_id() + ", " + GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID + " = " + relation.getGuardian_id() + " WHERE " + GUARDIAN_STUDENT_RELATION_COLUMN_ID + " = " + relation.getId();
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRelation(GuardianStudentRelation relation) {
        String query = "DELETE FROM "+TABLE_GUARDIAN_STUDENT_RELATION+" WHERE "+ GUARDIAN_STUDENT_RELATION_COLUMN_ID + " = " + relation.getId() + " LIMIT 1";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createRelation(GuardianStudentRelation relation) {
        String query = "INSERT INTO " +TABLE_GUARDIAN_STUDENT_RELATION + " ("+GUARDIAN_STUDENT_RELATION_COLUMN_ID+", "+GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID+", "+GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID+") VALUES (0, " + relation.getStudent_id() + ", "+relation.getGuardian_id()+");";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static GuardianStudentRelationTable getInstance() throws SQLException {
        if (instance == null){
            instance = new GuardianStudentRelationTable();
        }
        return instance;
    }
}
