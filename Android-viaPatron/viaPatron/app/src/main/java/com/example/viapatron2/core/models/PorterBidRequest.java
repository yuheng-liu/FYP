package com.example.viapatron2.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PorterBidRequest {
    @SerializedName("porter_name")
    @Expose
    private String porterName;

    @SerializedName("bid_amount")
    @Expose
    private String bidAmount;

    public String getPorterName() { return porterName; }
    public String getBidAmount() { return bidAmount; }
}
