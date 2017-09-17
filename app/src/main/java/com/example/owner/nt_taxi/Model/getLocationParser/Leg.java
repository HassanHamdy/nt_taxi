
package com.example.owner.nt_taxi.Model.getLocationParser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Leg {

    @SerializedName("distance")
    @Expose
    private LegDistance legDistance;
    @SerializedName("duration")
    @Expose
    private DurationLeg durationLeg;
    @SerializedName("end_address")
    @Expose
    private String endAddress;
    @SerializedName("end_location")
    @Expose
    private LegEndLocation legEndLocation;
    @SerializedName("start_address")
    @Expose
    private String startAddress;
    @SerializedName("start_location")
    @Expose
    private LegStartLocation legStartLocation;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("traffic_speed_entry")
    @Expose
    private List<Object> trafficSpeedEntry = null;
    @SerializedName("via_waypoint")
    @Expose
    private List<Object> viaWaypoint = null;

    public LegDistance getLegDistance() {
        return legDistance;
    }

    public void setLegDistance(LegDistance legDistance) {
        this.legDistance = legDistance;
    }

    public DurationLeg getDurationLeg() {
        return durationLeg;
    }

    public void setDurationLeg(DurationLeg durationLeg) {
        this.durationLeg = durationLeg;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LegEndLocation getLegEndLocation() {
        return legEndLocation;
    }

    public void setLegEndLocation(LegEndLocation legEndLocation) {
        this.legEndLocation = legEndLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public LegStartLocation getLegStartLocation() {
        return legStartLocation;
    }

    public void setLegStartLocation(LegStartLocation legStartLocation) {
        this.legStartLocation = legStartLocation;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Object> getTrafficSpeedEntry() {
        return trafficSpeedEntry;
    }

    public void setTrafficSpeedEntry(List<Object> trafficSpeedEntry) {
        this.trafficSpeedEntry = trafficSpeedEntry;
    }

    public List<Object> getViaWaypoint() {
        return viaWaypoint;
    }

    public void setViaWaypoint(List<Object> viaWaypoint) {
        this.viaWaypoint = viaWaypoint;
    }

}
