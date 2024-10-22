import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public class ChargesCalculation {

    public static void calculateCharges(int carID, Time time_of_parking, Time time_of_exiting) {
        LocalTime parkingTime = time_of_parking.toLocalTime();
        LocalTime exitingTime = time_of_exiting.toLocalTime();

        // Calculate the duration between parking and exiting
        Duration duration = Duration.between(parkingTime, exitingTime);

        // Get the total minutes
        long totalMinutes = duration.toMinutes();

        // Define the charge rate for every minute after 30 minutes
        int freeMinutes = 30;
        double ratePerMinute = 2.67;

        if (totalMinutes > freeMinutes) {
            long chargeableMinutes = totalMinutes - freeMinutes;
            double charges = chargeableMinutes * ratePerMinute;
            System.out.println("Charges for car ID " + carID + " is: " + charges);
        } else {
            System.out.println("Parking is free for car ID " + carID + " (within 30 minutes).");
        }
    }
}
