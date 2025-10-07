package com.parkease.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long slotId;

    private LocalDateTime timeIn;
    private LocalDateTime timeOut;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private BigDecimal totalCost;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = BookingStatus.ACTIVE;
    }

    // Constructors
    public Booking() {}

    public Booking(Long userId, Long slotId) {
        this.userId = userId;
        this.slotId = slotId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getSlotId() { return slotId; }
    public void setSlotId(Long slotId) { this.slotId = slotId; }

    public LocalDateTime getTimeIn() { return timeIn; }
    public void setTimeIn(LocalDateTime timeIn) { this.timeIn = timeIn; }

    public LocalDateTime getTimeOut() { return timeOut; }
    public void setTimeOut(LocalDateTime timeOut) { this.timeOut = timeOut; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public enum BookingStatus { ACTIVE, COMPLETED, CANCELLED }
}