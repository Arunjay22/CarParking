import java.sql.*;

public class Database extends InformationTable {

    public Connection getConnection() {
        Connection connection;

        try {
           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_parking", "root", "8610425056aA@");

            addTableInfo(carInformation,connection);
            addTableInfo(maximumParkingSpace,connection);
            addTableInfo(parkingCharges,connection);
            insertDefaultValue(insertDefaultData, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }

    private void insertDefaultValue(String query, Connection connection) {

        String checkValues = "select count(ID) from ParkingSpace;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkValues);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) == 0) {
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query);
                    preparedStatement2.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void addTableInfo(String query, Connection connection) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 0) {
                System.out.println("Some Error In The DataBase ");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
