
package com.example.owner.nt_taxi.Model.getLocationParser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * contains an array of steps denoting information about each separate step of the leg of the journey.
 *
 */

public class Step {

    @SerializedName("distance")
    @Expose
    private StepDistance distance;
    @SerializedName("duration")
    @Expose
    private StepDuration duration;
    @SerializedName("end_location")
    @Expose
    private StepEndLocation endLocation;
    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;
    @SerializedName("polyline")
    @Expose
    private Polyline polyline;
    @SerializedName("start_location")
    @Expose
    private StepStartLocation startLocation;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;

    public StepDistance getDistance() {
        return distance;
    }

    public void setDistance(StepDistance distance) {
        this.distance = distance;
    }

    public StepDuration getDuration() {
        return duration;
    }

    public void setDuration(StepDuration duration) {
        this.duration = duration;
    }

    public StepEndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(StepEndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public StepStartLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StepStartLocation startLocation) {
        this.startLocation = startLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

}
