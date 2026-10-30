package com.parceltracking.model;

import java.time.Instant;

public class ParcelEvent {
    private final ParcelStatus status;
    private final Instant timestamp;

    public ParcelEvent(ParcelStatus status, Instant timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
