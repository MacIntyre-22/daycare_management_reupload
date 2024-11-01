package dao;

import com.example.daycaremanagement.database.Position;

import java.util.ArrayList;

public interface PositionDAO {
    public ArrayList<Position> getAllPositions();
    public Position getPosition(int id);
}
