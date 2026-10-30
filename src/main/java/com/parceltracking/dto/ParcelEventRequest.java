package com.parceltracking.dto;

import java.time.Instant;

import com.parceltracking.model.ParcelStatus;

public class ParcelEventRequest {
    private ParcelStatus status;
    private Instant timestamp;

    public ParcelEventRequest() {}

    public ParcelStatus getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setStatus(ParcelStatus status) {
        this.status = status;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
