package com.parceltracking.controller;

import org.springframework.web.bind.annotation.RestController;

import com.parceltracking.dto.CreateParcelRequest;
import com.parceltracking.dto.ParcelEventRequest;
import com.parceltracking.model.Parcel;
import com.parceltracking.model.ParcelEvent;
import com.parceltracking.service.ParcelService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
public class ParcelController {
    private final ParcelService service;

    public ParcelController(ParcelService service) {
        this.service = service;
    }
    
    @PostMapping("/parcels")
    public Parcel createParcel(@RequestBody CreateParcelRequest request) {
        return service.createParcel(request.getTrackingNumber());
    }

    @GetMapping("/parcels/{trackingNumber}")
    public Parcel getParcel(@PathVariable  String trackingNumber) {
        return service.getParcel(trackingNumber);
    }

    @PostMapping("/parcels/{trackingNumber}/events")
    public Parcel createEvent(@PathVariable String trackingNumber, @RequestBody ParcelEventRequest request) {
        return service.addEvent(trackingNumber, request);
    }
}
