
package com.example.owner.nt_taxi.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationParser {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("info")
    @Expose
    private List<loginInfoParser> info = null;

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

    public List<loginInfoParser> getInfo() {
        return info;
    }

    public void setInfo(List<loginInfoParser> info) {
        this.info = info;
    }

}
