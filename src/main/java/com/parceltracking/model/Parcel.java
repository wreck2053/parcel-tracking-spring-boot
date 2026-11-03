package com.parceltracking.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParcelEvent> history = new ArrayList<>();

    public Parcel (String trackingNumber) {
        this.trackingNumber = trackingNumber;
        this.status = ParcelStatus.CREATED;
        addEventToHistory(new ParcelEvent(status, Instant.now()));
    }

    protected Parcel() {

    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public List<ParcelEvent> getHistory() {
        return List.copyOf(history);
    }

    private boolean isValidTransition(ParcelStatus newStatus) {
        switch (status) {
            case CREATED:
                return newStatus == ParcelStatus.COLLECTED;

            case COLLECTED:
                return newStatus == ParcelStatus.IN_TRANSIT;

            case IN_TRANSIT:
                return newStatus == ParcelStatus.OUT_FOR_DELIVERY;

            case OUT_FOR_DELIVERY:
                return newStatus == ParcelStatus.DELIVERY_ATTEMPTED
                        || newStatus == ParcelStatus.DELIVERED;

            case DELIVERY_ATTEMPTED:
                return newStatus == ParcelStatus.OUT_FOR_DELIVERY;

            case DELIVERED:
                return false;

            default:
                return false;
        }
    }

    public void recordEvent(ParcelEvent event) {
        for (ParcelEvent e : history) {
            if (e.getStatus() == event.getStatus() && e.getTimestamp().equals(event.getTimestamp())) {
                return;
            }
        }

        if (event.getStatus() != status) {
            if (!isValidTransition(event.getStatus())) {
                throw new IllegalArgumentException("Transition from status: " + status + " to " + event.getStatus() + " is not valid!");
            }
            status = event.getStatus();
        }

        addEventToHistory(event);
    }

    private void addEventToHistory(ParcelEvent event) {
        event.setParcel(this);
        history.add(event);
    }
}
