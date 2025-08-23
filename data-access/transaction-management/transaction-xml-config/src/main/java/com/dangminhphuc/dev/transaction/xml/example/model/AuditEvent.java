package com.dangminhphuc.dev.transaction.xml.example.model;

import lombok.Setter;

import java.time.LocalDateTime;

public class AuditEvent {
    @Setter
    private long id;
    @Setter
    private String eventName;
    private LocalDateTime eventTimestamp;

    public AuditEvent(long id, String eventName, LocalDateTime eventTimestamp) {
        this.id = id;
        this.eventName = eventName;
        this.eventTimestamp = eventTimestamp;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    @Override
    public String toString() {
        return "AuditEvent{" +
                "id=" + id +
                ", eventName='" + eventName + "'" +
                ", eventTimestamp=" + eventTimestamp +
                '}';
    }
}