package com.example.viaporter.models;

public class PorterBidRequest {

    private String porterUid;
    private String porterName;
    private Double bidAmount;

    public String getPorterName() { return porterName; }
    public Double getBidAmount() { return bidAmount; }
    public String getPorterUid() { return porterUid; }

    public PorterBidRequest(String porterUid, String porterName, Double bidAmount) {
        this.porterUid = porterUid;
        this.porterName = porterName;
        this.bidAmount = bidAmount;
    }
}
