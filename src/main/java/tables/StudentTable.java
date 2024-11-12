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

    @Override
    public void updateStudent(Student student) {
        String query = "UPDATE " + TABLE_STUDENTS + " SET " + STUDENTS_COLUMN_FIRST_NAME + " = '" + student.getFirst_name() + "', " + STUDENTS_COLUMN_LAST_NAME + " = '" + student.getLast_name() +"', " + STUDENTS_COLUMN_BIRTHDATE + " = '" + student.getBirthdate() + "', " + STUDENTS_COLUMN_ROOM_ID + " = "+ student.getRoom_id()+
                " WHERE " + STUDENTS_COLUMN_ID + " = " + student.getId();
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(Student student) {
        String query = "DELETE FROM "+TABLE_STUDENTS+" WHERE "+ STUDENTS_COLUMN_ID + " = " + student.getId() + " LIMIT 1";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createStudent(Student student) {
        String query = "INSERT INTO " + TABLE_STUDENTS + " ("+STUDENTS_COLUMN_ID+", "+ STUDENTS_COLUMN_FIRST_NAME+", "+STUDENTS_COLUMN_LAST_NAME+", "+STUDENTS_COLUMN_BIRTHDATE+", "+STUDENTS_COLUMN_ROOM_ID+") VALUES (0, '"+student.getFirst_name()+"', '"+student.getLast_name()+"', '"+student.getBirthdate()+"', "+student.getRoom_id()+");";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
