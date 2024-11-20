package com.example.daycaremanagement.pojo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Student {
    private int id;
    private String first_name;
    private String last_name;
    private String birthdate;
    private int room_id;

    public Student(int id, String first_name, String last_name, String birthdate, int room_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthdate = birthdate;
        this.room_id = room_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    /**
     * Gets the students age by getting the difference between their birthdate and today.
     * @return age as double
     */
    public double getAge() {
        // Get student birthday
        // YYYY-MM-DD
        String birthday = this.getBirthdate();
        String[] birthdaySplit = birthday.split("-");
        LocalDate birthdayDate = LocalDate.of(Integer.parseInt(birthdaySplit[0]), Integer.parseInt(birthdaySplit[1]), Integer.parseInt(birthdaySplit[2]));
        LocalDate now = LocalDate.now();
        double age;

        // Get the difference to find age
        age = (double) ChronoUnit.YEARS.between(birthdayDate, now);
        System.out.println(age);
        // Return age
        return age;
    }

    @Override
    public String toString(){
        return first_name + " " + last_name;
    }
}
