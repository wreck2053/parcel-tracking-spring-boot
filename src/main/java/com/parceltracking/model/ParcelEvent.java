package com.parceltracking.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ParcelEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ParcelStatus status;
    private Instant timestamp;

    @ManyToOne
    @JoinColumn(name = "parcel_id", nullable = false)
    @JsonIgnore 
    private Parcel parcel;

    public ParcelEvent(ParcelStatus status, Instant timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }

    protected ParcelEvent() {

    }

    public ParcelStatus getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }
}
