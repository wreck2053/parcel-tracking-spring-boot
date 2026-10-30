package com.parceltracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.parceltracking.model.Parcel;
import com.parceltracking.model.ParcelEvent;
import com.parceltracking.model.ParcelStatus;

public class ParcelTest {
    @Test
    void newParcelShouldHaveCreatedStatus() {
        Parcel parcel = new Parcel("RM123456789GB");

        assertEquals(ParcelStatus.CREATED, parcel.getStatus());
    }

    @Test 
    void newParcelShouldHaveInitialCreatedEvent() {
        Parcel parcel = new Parcel("RM123456789GB");

        assertEquals(1, parcel.getHistory().size());
        assertEquals(ParcelStatus.CREATED, parcel.getHistory().get(0).getStatus());
    }
    @Test
    void validTransitionShouldUpdateStatusAndHistory() {
        Parcel parcel = new Parcel("RM123456789GB");
        parcel.recordEvent(new ParcelEvent(ParcelStatus.COLLECTED, Instant.now()));

        assertEquals(ParcelStatus.COLLECTED, parcel.getStatus());
        assertEquals(2, parcel.getHistory().size());
        assertEquals(ParcelStatus.COLLECTED, parcel.getHistory().get(1).getStatus());
    }

    @Test
    void invalidTransitionShouldNotChangeParcel() {
        Parcel parcel = new Parcel("RM123456789GB");

        assertThrows(IllegalArgumentException.class, () -> {
            parcel.recordEvent(new ParcelEvent(ParcelStatus.DELIVERED, Instant.now()));
        });
        assertEquals(ParcelStatus.CREATED, parcel.getStatus());
        assertEquals(1, parcel.getHistory().size());
    }

    @Test
    void duplicateEventShouldBeIgnored() {
        Parcel parcel = new Parcel("RM123456789GB");
        ParcelEvent parcelEvent = new ParcelEvent(ParcelStatus.COLLECTED, Instant.MIN);
        parcel.recordEvent(parcelEvent);
        assertEquals(2, parcel.getHistory().size());

        parcel.recordEvent(parcelEvent);
        assertEquals(2, parcel.getHistory().size());
        assertEquals(ParcelStatus.COLLECTED, parcel.getStatus());
    }

    @Test
    void sameStatusWithDifferentTimestampShouldBeRecorded() {
        Parcel parcel = new Parcel("RM123456789GB");
        Instant timestamp = Instant.now();

        parcel.recordEvent(new ParcelEvent(ParcelStatus.COLLECTED, timestamp));
        assertEquals(2, parcel.getHistory().size());
        assertEquals(ParcelStatus.COLLECTED, parcel.getStatus());

        parcel.recordEvent(new ParcelEvent(ParcelStatus.COLLECTED, timestamp.plusSeconds(60)));
        assertEquals(3, parcel.getHistory().size());
        assertEquals(ParcelStatus.COLLECTED, parcel.getStatus());
    }
}
