package com.parceltracking.service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.parceltracking.dto.ParcelEventRequest;
import com.parceltracking.model.Parcel;
import com.parceltracking.model.ParcelEvent;

@Service 
public class ParcelService {
    private final Map<String, Parcel> parcels = new ConcurrentHashMap<>();

    public Parcel createParcel (String trackingNumber) {
        if (trackingNumber == null || trackingNumber.isBlank()) {
            throw new IllegalArgumentException("Tracking number cannot be blank!");
        }

        Parcel createdParcel = new Parcel(trackingNumber);
        if (parcels.putIfAbsent(trackingNumber, createdParcel) != null) {
            throw new DuplicateTrackingNumberException("Tracking number already exists: " + trackingNumber);
        }

        return createdParcel;
    }

    public Parcel getParcel(String trackingNumber) {
        Parcel parcel = parcels.get(trackingNumber);
        if (parcel == null) {
            throw new NoSuchElementException("Parcel not found: " + trackingNumber);
        }
        return  parcel;
    }

    public Parcel addEvent(String trackingNumber, ParcelEventRequest request) {
        Parcel parcel = getParcel(trackingNumber);
        parcel.recordEvent(new ParcelEvent(request.getStatus(), request.getTimestamp()));
        return parcel;
    }
}
