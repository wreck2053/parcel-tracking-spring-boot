package com.parceltracking.service;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parceltracking.dto.ParcelEventRequest;
import com.parceltracking.model.Parcel;
import com.parceltracking.model.ParcelEvent;
import com.parceltracking.repository.ParcelRepository;

@Service 
public class ParcelService {
    private final ParcelRepository repository;

    public ParcelService(ParcelRepository repository) {
        this.repository = repository;
    }

    public Parcel createParcel () {
        String trackingNumber = "PK" + UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return repository.save(new Parcel(trackingNumber));
    }

    public Parcel getParcel(String trackingNumber) {
        return repository.findByTrackingNumber(trackingNumber).orElseThrow(() -> new NoSuchElementException("Parcel not found: " + trackingNumber));
    }

    @Transactional
    public Parcel addEvent(String trackingNumber, ParcelEventRequest request) {
        Parcel parcel = getParcel(trackingNumber);
        parcel.recordEvent(new ParcelEvent(request.getStatus(), request.getTimestamp()));
        return repository.save(parcel);
    }
}
