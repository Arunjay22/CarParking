import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingSpaceAvailability {

    protected static String parkingSpaceGenerator(Connection connection) {
        String query = "select ID,ParkingID from parkingSpace where Status_Of_ParkingArea = 0";

        String returnIndex = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> availableParkingIDs = new ArrayList<>();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String addToList = resultSet.getString("ParkingID");
                    availableParkingIDs.add(addToList);
                }
            }
            int size = availableParkingIDs.size();

            if (size > 0) {
                Random r = new Random();

                int indexValue = r.nextInt(size);
                returnIndex = availableParkingIDs.get(indexValue);
            } else {
                System.out.println("No Parking Available , Please TryAgain Sometime .");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return returnIndex;
    }

    protected static void parkingSpaceAssigner(Connection connection, String selectedParkingID) {


    }
}
