package com.example.daycaremanagement.dao;

import com.example.daycaremanagement.pojo.Position;

import java.util.ArrayList;

public interface PositionDAO {
    public ArrayList<Position> getAllPositions();
    public Position getPosition(int id);
    public void updatePosition(Position position);
    public void deletePosition(Position position);
    public void createPosition(Position position);
}
