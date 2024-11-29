package com.example.daycaremanagement.pojo.display;

public class DisplayStaff {
    private int id;
    private String first_name;
    private String last_name;
    private double wage;
    private String room;
    private String position;

    public DisplayStaff(int id, String first_name, String last_name, double wage, String room, String position) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.wage = wage;
        this.room = room;
        this.position = position;
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

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString(){
        return first_name + " " + last_name;
    }
}
