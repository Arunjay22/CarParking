import java.sql.*;
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

    protected static void parkingSpaceRemover(Connection connection, String carNumber) {
        String selectQuery = "SELECT carID, Time_Of_Parking, Time_Of_Exiting FROM carInformation WHERE car_number = ?";

        try (PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery)) {
            selectPreparedStatement.setString(1, carNumber);

            try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int carID = resultSet.getInt("carID");
                    Time time_of_parking = resultSet.getTime("Time_Of_Parking");
                    Time time_of_exiting = resultSet.getTime("Time_Of_Exiting");

                    // Now update the parking space status
                    String updateQuery = "UPDATE parkingSpace SET Status_Of_ParkingArea = 0 WHERE carID = ?";
                    try (PreparedStatement updatePreparedStatement = connection.prepareStatement(updateQuery)) {
                        updatePreparedStatement.setInt(1, carID);
                        int affectedRows = updatePreparedStatement.executeUpdate();

                        if (affectedRows > 0) {
                            // Call the ChargesCalculation method after the update
                            ChargesCalculation.calculateCharges(carID, time_of_parking, time_of_exiting);
                            System.out.println("Parking space updated and charges calculated.");
                        } else {
                            System.out.println("Parking space update failed.");
                        }
                    }
                } else {
                    System.out.println("Car not found in the system.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error in parking space removal: " + e.getMessage(), e);
        }
    }
}