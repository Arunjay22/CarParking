import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ParkingArea extends ParkingSpaceAvailability {
    private final Scanner scanner;
    private final Connection connection;

    public ParkingArea(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public static void addCar(Connection connection, Scanner scanner) {

        String setTrueOnID = parkingSpaceGenerator(connection);

        if (setTrueOnID != null && !setTrueOnID.isEmpty()) {


            //collecting Information Part
            System.out.println("Enter Car Number :");
            String carNumber = scanner.nextLine();

            System.out.println("Enter Car Color :");
            String carColor = scanner.nextLine();

            Date entryDate = java.sql.Date.valueOf(LocalDate.now());
            Time entryTime = java.sql.Time.valueOf(LocalTime.now());

            //Inserting The Information To MySQL
            String query = "insert into carInformation (car_number , car_color ,Date_of_parking, time_of_parking) values (?,?,?,?)";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, carNumber);
                preparedStatement.setString(2, carColor);
                preparedStatement.setDate(3, entryDate);
                preparedStatement.setTime(4, entryTime);

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                        if (resultSet.next()) {

                            int carID = resultSet.getInt(1);
                            parkingSpaceAssigner(connection, setTrueOnID,carID);

                        }
                    }
                    System.out.println("Car Parked Successfully");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println("================================");
            System.out.println("|    No Parking Space here     |");
            System.out.println("================================");
        }

    }

    public static void exitingCar(Connection connection, Scanner scanner) {
        String query = "UPDATE carInformation SET Time_Of_Exiting = ? WHERE car_number= ?";

        System.out.println("Enter Car ID to exit:");
        String carNumber = scanner.nextLine();

        Time time = java.sql.Time.valueOf(LocalTime.now());

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTime(1, time);
            preparedStatement.setString(2, carNumber);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {

                ParkingSpaceAvailability.parkingSpaceRemover(connection,carNumber);
                System.out.println("Car exited successfully.");
            } else {
                System.out.println("No car found with the given ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating car exit time: " + e.getMessage(), e);
        }
    }
}
