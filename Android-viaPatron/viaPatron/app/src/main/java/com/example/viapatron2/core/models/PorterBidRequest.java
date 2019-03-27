package com.example.viapatron2.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PorterBidRequest {
    @SerializedName("porter_name")
    @Expose
    private String porterName;

    @SerializedName("porter_id")
    @Expose
    private String porterUid;

    @SerializedName("bid_amount")
    @Expose
    private Double bidAmount;

    public String getPorterUid() { return porterUid; }
    public void setPorterUid(String porterUid) { this.porterUid = porterUid; }
    public String getPorterName() { return porterName; }
    public void setPorterName(String porterName) {
        this.porterName = porterName;
    }
    public Double getBidAmount() { return bidAmount; }
    public void setBidAmount(Double bidAmount) {
        this.bidAmount = bidAmount;
    }
}
