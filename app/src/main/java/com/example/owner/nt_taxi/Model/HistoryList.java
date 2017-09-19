package com.example.owner.nt_taxi.Model;



public class HistoryList {

    public String DriverName;
    public String Location;
    public String DropLocation;
    public String Accept;
    public double Cost;

    public HistoryList(String driverName, String location, String dropLocation, String accept,
                       double cost) {
        DriverName = driverName;
        Location = location;
        DropLocation = dropLocation;
        Accept = accept;
        Cost = cost;
    }

    public String getDriverName() {
        return DriverName;
    }

    public String getLocation() {
        return Location;
    }

    public String getDropLocation() {
        return DropLocation;
    }

    public String getAccept() {
        return Accept;
    }

    public double getCost() {
        return Cost;
    }


}
