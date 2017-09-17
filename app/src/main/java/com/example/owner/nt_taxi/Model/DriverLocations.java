
package com.example.owner.nt_taxi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DriverLocations {

    @SerializedName("location")
    @Expose
    private ArrayList<Location> location = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("image")
    @Expose
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
