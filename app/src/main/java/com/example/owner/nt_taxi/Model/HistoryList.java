package com.example.owner.nt_taxi.Model;

/**
 * Created by Owner on 8/10/2017.
 */

public class HistoryList {

    public String DriverName;
    public String Location;
    public String DropLocation;
    public String Accept;

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

    public HistoryList(String driverName, String location, String dropLocation, String accept) {
        DriverName = driverName;
        Location = location;
        DropLocation = dropLocation;
        Accept = accept;
    }


}
