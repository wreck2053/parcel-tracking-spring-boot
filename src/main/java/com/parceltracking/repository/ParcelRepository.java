package com.parceltracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parceltracking.model.Parcel;


public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    Optional<Parcel> findByTrackingNumber(String trackingNumber);
}
