package dao;

import com.example.daycaremanagement.database.Student;

import java.util.ArrayList;

public interface StudentDAO {
    public ArrayList<Student> getAllStudents();
    public Student getStudent(int id);
    public void updateStudent(Student student);
    public void deleteStudent(Student student);
    public void createStudent(Student student);
}
