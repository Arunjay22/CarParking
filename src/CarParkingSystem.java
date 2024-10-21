import java.sql.Connection;
import java.util.Scanner;

public class CarParkingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Database db = new Database();
        Connection connection = db.getConnection();

        ParkingArea parkingArea = new ParkingArea(scanner, connection);
        while (true) {
            System.out.println("Car Parking");
            System.out.println("1.");
            System.out.println("2.");
            System.out.println("3.");
            System.out.println("4.");
            System.out.println("5.");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    ParkingArea.addCar(connection, scanner);
                    break;
                case 2:
                    System.out.println("");
                    break;
                default:
                    System.out.println("  ");
                    break;
            }

        }
    }
}