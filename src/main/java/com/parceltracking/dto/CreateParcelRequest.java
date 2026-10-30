package com.parceltracking.dto;

public class CreateParcelRequest {
    private String trackingNumber;

    public CreateParcelRequest() {

    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
