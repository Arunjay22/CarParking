import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingSpaceAvailability {

    protected static String parkingSpaceGenerator(Connection connection) {
        String query = "select ParkingID from parkingSpace where Status_Of_ParkingArea = 0";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<String> availableParkingIDs = new ArrayList<>();

            while (resultSet.next()) {
                availableParkingIDs.add(resultSet.getString(1));
            }

            int size = availableParkingIDs.size();

            if (size > 0) {
                Random r = new Random();
                int indexValue = r.nextInt(size);
                return availableParkingIDs.get(indexValue);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    protected static void parkingSpaceAssigner(Connection connection, String setTrueOnID, int carID) {

        String query = "UPDATE parkingSpace SET Status_Of_ParkingArea = 1, carId = ? WHERE parkingID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(2, setTrueOnID);
            preparedStatement.setInt(1, carID);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows <= 0) {
                // Handle case when no rows were updated
                System.out.println("No parking space found with the given ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
