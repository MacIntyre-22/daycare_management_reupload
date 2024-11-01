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
}
