
package com.example.owner.nt_taxi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RideListParser {

    @SerializedName("ridelist")
    @Expose
    private List<Ridelist> ridelist = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Ridelist> getRidelist() {
        return ridelist;
    }

    public void setRidelist(List<Ridelist> ridelist) {
        this.ridelist = ridelist;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
