package dao;

import com.example.daycaremanagement.database.Student;

import java.util.ArrayList;

public interface StudentDAO {
    public ArrayList<Student> getAllStudents();
    public Student getStudent(int id);
}
