package com.example.viaporter.models;

public enum TripState {
    NOT_STARTED,
    PATRON_STARTED,
    PORTER_STARTED,
    IN_PROGRESS,
    PATRON_STOPPED,
    PORTER_STOPPED,
    PATRON_ARRIVED,
    ENDED,
    CANCELLED,
    PORTER_CANCELLED
}