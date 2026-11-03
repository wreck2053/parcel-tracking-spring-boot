package com.parceltracking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.parceltracking.model.Parcel;
import com.parceltracking.model.ParcelEvent;
import com.parceltracking.model.ParcelStatus;

public class ParcelTest {
    @Test
    void newParcelShouldHaveCreatedStatus() {
        Parcel parcel = new Parcel("TEST123");
        assertEquals(ParcelStatus.CREATED, parcel.getStatus());
    }

    @Test
    void duplicateEventShouldBeIgnored() {
        Parcel parcel = new Parcel("TEST123");
        ParcelEvent event = new ParcelEvent(ParcelStatus.COLLECTED, Instant.now());

        parcel.recordEvent(event);
        assertEquals(2, parcel.getHistory().size());

        parcel.recordEvent(event);
        assertEquals(2, parcel.getHistory().size());
    }

    @Test
    void sameStatusWithDifferentTimestampsShouldBeRecorded() {
        Parcel parcel = new Parcel("TEST123");
        ParcelEvent firstEvent = new ParcelEvent(ParcelStatus.COLLECTED, Instant.parse("2026-11-02T10:00:00Z"));
        ParcelEvent secondEvent = new ParcelEvent(ParcelStatus.COLLECTED, Instant.parse("2026-11-02T10:05:00Z"));

        parcel.recordEvent(firstEvent);
        assertEquals(ParcelStatus.COLLECTED, parcel.getHistory().get(1).getStatus());
        assertEquals(2, parcel.getHistory().size());
        assertEquals(firstEvent.getTimestamp(), parcel.getHistory().get(1).getTimestamp());

        parcel.recordEvent(secondEvent);
        assertEquals(ParcelStatus.COLLECTED, parcel.getHistory().get(2).getStatus());
        assertEquals(3, parcel.getHistory().size());
        assertEquals(secondEvent.getTimestamp(), parcel.getHistory().get(2).getTimestamp());
    }
}
