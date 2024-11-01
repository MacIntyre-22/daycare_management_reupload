package tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.database.GuardianStudentRelation;
import dao.GuardianStudentRelationDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.Const.*;

public class GuardianStudentRelationTable implements GuardianStudentRelationDAO {
    Database db;
    {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
}
