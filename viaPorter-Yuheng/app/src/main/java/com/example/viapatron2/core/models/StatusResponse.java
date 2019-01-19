package com.example.viapatron2.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lim Zhiming on 10/1/19.
 * Adapting from Auto
 */

public class StatusResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("error_code")
    @Expose
    private StatusCode statusCode;

    @SerializedName("reason")
    @Expose
    private String reason;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getReason() {
        // TODO: need to add new getReasonText(Context context) instead for language support
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
