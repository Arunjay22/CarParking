public class InformationTable {
    String carInformation = "create table if not exists carInformation (carID int auto_increment not null, Car_Number varchar(255),Car_Color varchar (255) , Date_Of_Parking Date , Time_Of_Parking Time , Time_Of_Exiting Time,primary Key (CarID)); ";
    String maximumParkingSpace = "create table if not exists ParkingSpace (ID int auto_increment not null, ParkingID varchar(255),Status_Of_ParkingArea TINYINT,primary key (Id) ,CarID int ,constraint fk_ParkingSpace foreign key(carID) references carInformation(carID)); ";

    String parkingCharges = "create table if not exists ParkingCharges (ID int auto_increment not null,Parking_Charges int ,primary key (Id),CarID int ,constraint fk_ParkingCharges foreign key (CarID) references carInformation(CarID) );";

    String insertDefaultData = "insert into parkingSpace (ParkingID,Status_Of_ParkingArea) values ('A1',0),('A2',0),('A3',0),('A4',0),('A5',0); ";

}
