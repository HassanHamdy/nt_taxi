
package com.example.owner.nt_taxi.Model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverLocations {

    @SerializedName("location")
    @Expose
    private ArrayList<Location> location = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public ArrayList<Location> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<Location> location) {
        this.location = location;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
