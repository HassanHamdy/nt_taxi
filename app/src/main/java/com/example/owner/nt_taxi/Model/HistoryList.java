package com.example.owner.nt_taxi.Model;



public class HistoryList {

    public String DriverName;
    public String Location;
    public String DropLocation;
    public String Accept;

    public HistoryList(String driverName, String location, String dropLocation, String accept) {
        DriverName = driverName;
        Location = location;
        DropLocation = dropLocation;
        Accept = accept;
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


}
