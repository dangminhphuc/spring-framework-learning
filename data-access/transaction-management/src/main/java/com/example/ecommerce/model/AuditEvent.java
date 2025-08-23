package com.example.ecommerce.model;

import java.time.LocalDateTime;

public class AuditEvent {
    private long id;
    private String eventName;
    private LocalDateTime eventTimestamp;

    public AuditEvent(long id, String eventName, LocalDateTime eventTimestamp) {
        this.id = id;
        this.eventName = eventName;
        this.eventTimestamp = eventTimestamp;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public LocalDateTime getEventTimestamp() { return eventTimestamp; }
    public void setEventTimestamp(LocalDateTime eventTimestamp) { this.eventTimestamp = eventTimestamp; }

    @Override
    public String toString() {
        return "AuditEvent{"
                + "id=" + id + 
                ", eventName='" + eventName + "'" + 
                ", eventTimestamp=" + eventTimestamp + 
                '}';
    }
}