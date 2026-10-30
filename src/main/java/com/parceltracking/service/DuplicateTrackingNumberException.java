package com.parceltracking.service;

public class DuplicateTrackingNumberException extends RuntimeException{
    public DuplicateTrackingNumberException(String message) {
        super(message);
    }
}
