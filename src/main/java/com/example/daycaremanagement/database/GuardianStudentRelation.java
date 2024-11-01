package com.example.daycaremanagement.database;

public class GuardianStudentRelation {
    private int id;
    private int student_id;
    private int guardian_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GuardianStudentRelation(int id, int guardian_id, int student_id) {

        this.guardian_id = guardian_id;
        this.student_id = student_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getGuardian_id() {
        return guardian_id;
    }

    public void setGuardian_id(int guardian_id) {
        this.guardian_id = guardian_id;
    }

    @Override
    public String toString(){
        return "Student id: " + student_id+
                "\nGuardian id: " + guardian_id;
    }
}
