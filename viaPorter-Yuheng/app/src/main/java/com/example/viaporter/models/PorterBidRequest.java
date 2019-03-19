package com.example.viaporter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PorterBidRequest {

    @SerializedName("porter_id")
    @Expose
    private String porterID;

    @SerializedName("porter_name")
    @Expose
    private String porterName;

    @SerializedName("bid_amount")
    @Expose
    private Double bidAmount;

    public String getPorterName() { return porterName; }
    public Double getBidAmount() { return bidAmount; }
    public String getPorterID() { return porterID; }

    public PorterBidRequest(String id, String name, Double amount) {
        porterID = id;
        porterName = name;
        bidAmount = amount;
    }
}
