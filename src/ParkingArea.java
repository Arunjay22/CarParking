import java.sql.Connection;
import java.util.Scanner;

public class ParkingArea {
    private final Scanner scanner;
    private final Connection connection;

    public ParkingArea(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public static void addCar(Connection connection, Scanner scanner) {

    }

    public static void exitingCar(Connection connection, Scanner scanner) {
    }
}
