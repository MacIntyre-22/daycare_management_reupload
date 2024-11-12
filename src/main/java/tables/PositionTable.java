package tables;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.database.Position;
import dao.PositionDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.daycaremanagement.database.Const.*;

public class PositionTable implements PositionDAO {
    Database db;
    {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
        String query = "SELECT * FROM "+ TABLE_POSITIONS + " WHERE " + POSITIONS_COLUMN_ID + " = " + id;
        try{
            Statement getPosition = db.getConnection().createStatement();
            ResultSet data = getPosition.executeQuery(query);
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
        String query = "UPDATE " + TABLE_POSITIONS + " SET " + POSITIONS_COLUMN_NAME + " = '" + position.getName() + "' WHERE " + POSITIONS_COLUMN_ID + " = " + position.getId();
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deletePosition(Position position) {
        String query = "DELETE FROM "+TABLE_POSITIONS+" WHERE "+ POSITIONS_COLUMN_ID + " = " + position.getId() + " LIMIT 1";
        try{
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
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
}
