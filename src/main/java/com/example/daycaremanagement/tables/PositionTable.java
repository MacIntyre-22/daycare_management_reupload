package com.example.daycaremanagement.tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.pojo.Position;
import com.example.daycaremanagement.dao.PositionDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.DBConst.*;

public class PositionTable implements PositionDAO {
    private static PositionTable instance;
    private PositionTable() throws SQLException { db = Database.getInstance(); }
    Database db;
    ArrayList<Position> positions;


    @Override
    public ArrayList<Position> getAllPositions() {
        positions = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_POSITIONS;
        try{
            Statement getPosition = db.getConnection().createStatement();
            ResultSet data = getPosition.executeQuery(query);

            while(data.next()){
                positions.add(new Position(
                        data.getInt(POSITIONS_COLUMN_ID),
                        data.getString(POSITIONS_COLUMN_NAME)
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return positions;
    }

    @Override
    public Position getPosition(int id) {
        String query = "SELECT * FROM "+ TABLE_POSITIONS + " WHERE " + POSITIONS_COLUMN_ID + " = ?";
        try{
            PreparedStatement getPosition = db.getConnection().prepareStatement(query);
            getPosition.setInt(1, id);
            ResultSet data = getPosition.executeQuery();
            if (data.next()){
                Position position = new Position(
                        data.getInt(POSITIONS_COLUMN_ID),
                        data.getString(POSITIONS_COLUMN_NAME)
                );
                return position;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePosition(Position position) {
        String query = "UPDATE " + TABLE_POSITIONS + " SET " + POSITIONS_COLUMN_NAME + " = ? WHERE " + POSITIONS_COLUMN_ID + " = ?";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setString(1, position.getName());
            statement.setInt(2, position.getId());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deletePosition(Position position) {
        String query = "DELETE FROM "+TABLE_POSITIONS+" WHERE "+ POSITIONS_COLUMN_ID + " = ? LIMIT 1";
        try{
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, position.getId());
            statement.executeUpdate();
        } catch (Exception e){
            System.out.println("Invalid Position ID or the ID is in use in another table.");
        }
    }

    @Override
    public void createPosition(Position position) {
        String query = "INSERT INTO " + TABLE_POSITIONS + " ("+POSITIONS_COLUMN_ID+", "+POSITIONS_COLUMN_NAME+") VALUES (0, '" + position.getName() +"');";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static PositionTable getInstance() throws SQLException {
        if (instance == null){
            instance = new PositionTable();
        }
        return instance;
    }
}
