package com.example.daycaremanagement.tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.pojo.GuardianStudentRelation;
import com.example.daycaremanagement.dao.GuardianStudentRelationDAO;

import java.sql.PreparedStatement;
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
        String query = "SELECT * FROM "+ TABLE_GUARDIAN_STUDENT_RELATION + " WHERE " + GUARDIAN_STUDENT_RELATION_COLUMN_ID + " = ?";
        try{
            PreparedStatement getRelation = db.getConnection().prepareStatement(query);
            getRelation.setInt(1, id);
            ResultSet data = getRelation.executeQuery();
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

    public ArrayList<GuardianStudentRelation> getRelationByEitherId(int searchId, boolean isGuardian) {
        String searchCriteria;
        relations = new ArrayList<>();
        if (isGuardian) { searchCriteria = GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID; } else { searchCriteria = GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID; }
        String query = "SELECT * FROM "+ TABLE_GUARDIAN_STUDENT_RELATION + " WHERE " + searchCriteria + " = ?";
        try{
            PreparedStatement getRelation = db.getConnection().prepareStatement(query);
            getRelation.setInt(1, searchId);
            ResultSet data = getRelation.executeQuery();
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
        String query = "UPDATE "+TABLE_GUARDIAN_STUDENT_RELATION + " SET " + GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID + " = ?, " + GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID + " = ? WHERE " + GUARDIAN_STUDENT_RELATION_COLUMN_ID + " = ?";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, relation.getStudent_id());
            statement.setInt(2, relation.getGuardian_id());
            statement.setInt(3, relation.getId());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRelation(GuardianStudentRelation relation) {
        String query = "DELETE FROM "+TABLE_GUARDIAN_STUDENT_RELATION+" WHERE "+ GUARDIAN_STUDENT_RELATION_COLUMN_ID + " = ? LIMIT 1";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1,relation.getId());
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createRelation(GuardianStudentRelation relation) {
        String query = "INSERT INTO " +TABLE_GUARDIAN_STUDENT_RELATION + " ("+GUARDIAN_STUDENT_RELATION_COLUMN_ID+", "+GUARDIAN_STUDENT_RELATION_COLUMN_STUDENT_ID+", "+GUARDIAN_STUDENT_RELATION_COLUMN_GUARDIAN_ID+") VALUES (0, ?, ?);";

        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, relation.getStudent_id());
            statement.setInt(2, relation.getGuardian_id());
            statement.execute();
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
