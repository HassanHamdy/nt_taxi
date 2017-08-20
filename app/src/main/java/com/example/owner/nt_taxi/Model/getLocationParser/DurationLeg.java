
package com.example.owner.nt_taxi.Model.getLocationParser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * duration indicates the total duration of this leg, as a field with the following elements:
 * value indicates the duration in seconds.
 * text contains a human-readable representation of the duration.
 */

public class DurationLeg {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private Integer value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
