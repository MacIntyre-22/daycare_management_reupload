package tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.database.Student;
import dao.StudentDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.Const.*;

public class StudentTable implements StudentDAO {
    Database db = Database.getInstance();
    ArrayList<Student> students;

    public StudentTable() throws SQLException {
    }

    @Override
    public ArrayList<Student> getAllStudents() {
        students = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_STUDENTS;
        try{
            Statement getStudents = db.getConnection().createStatement();
            ResultSet data = getStudents.executeQuery(query);

            while(data.next()){
                students.add(new Student(
                        data.getInt(STUDENTS_COLUMN_ID),
                        data.getString(STUDENTS_COLUMN_FIRST_NAME),
                        data.getString(STUDENTS_COLUMN_LAST_NAME),
                        data.getString(STUDENTS_COLUMN_BIRTHDATE),
                        data.getInt(STUDENTS_COLUMN_ROOM_ID)
                        ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student getStudent(int id) {
        String query = "SELECT * FROM "+ TABLE_STUDENTS + " WHERE " + STUDENTS_COLUMN_ID + " = " + id;
        try{
            Statement getStudent = db.getConnection().createStatement();
            ResultSet data = getStudent.executeQuery(query);
            if (data.next()){
                Student student = new Student(
                        data.getInt(STUDENTS_COLUMN_ID),
                        data.getString(STUDENTS_COLUMN_FIRST_NAME),
                        data.getString(STUDENTS_COLUMN_LAST_NAME),
                        data.getString(STUDENTS_COLUMN_BIRTHDATE),
                        data.getInt(STUDENTS_COLUMN_ROOM_ID)
                );
                return student;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
