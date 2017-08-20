
package com.example.owner.nt_taxi.Model.getLocationParser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * value indicates the distance in meters
 * text contains a human-readable representation of the distance,
 * displayed in units as used at the origin (or as overridden within the units parameter in the request).
 * (For example, miles and feet will be used for any origin within the United States.)
 * Note that regardless of what unit system is displayed as text,
 * the distance.value field always contains a value expressed in meters.
 */

public class LegDistance {

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
