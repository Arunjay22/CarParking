import java.sql.Connection;
import java.util.Scanner;

public class CarParkingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Database db = new Database();
        Connection connection = db.getConnection();

        ParkingArea parkingArea = new ParkingArea(scanner, connection);
        while (true) {
            System.out.println("====== Car Parking System ======");
            System.out.println("1. Park a Car");
            System.out.println("2. View Available Parking Spaces");
            System.out.println("3. Exit a Car from Parking");
            System.out.println("4. View Parking Charges");
            System.out.println("5. Exit the System");
            System.out.println("================================");
            System.out.print("Please select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    ParkingArea.addCar(connection, scanner);
                    break;
                case 2:
                    ParkingArea.exitingCar(connection, scanner);
                    break;
                default:
                    System.out.println("Please Enter Valid Option");
                    break;
            }

        }
    }
}