package com.parkease.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "slots")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String slotName;

    @Column(nullable = false)
    private String zone;

    @Column(nullable = false)
    private Integer row;

    @Column(nullable = false)
    private Integer col;

    @Enumerated(EnumType.STRING)
    private SlotType type;

    @Enumerated(EnumType.STRING)
    private SlotStatus status;

    @Column(nullable = false)
    private BigDecimal pricePerHour;

    private Long lastBookedBy;

    @Version
    private Integer version;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PreUpdate
    @PrePersist
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Slot() {
        this.status = SlotStatus.AVAILABLE;
        this.updatedAt = LocalDateTime.now();
        this.version = 0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSlotName() { return slotName; }
    public void setSlotName(String slotName) { this.slotName = slotName; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public Integer getRow() { return row; }
    public void setRow(Integer row) { this.row = row; }

    public Integer getCol() { return col; }
    public void setCol(Integer col) { this.col = col; }

    public SlotType getType() { return type; }
    public void setType(SlotType type) { this.type = type; }

    public SlotStatus getStatus() { return status; }
    public void setStatus(SlotStatus status) { this.status = status; }

    public BigDecimal getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(BigDecimal pricePerHour) { this.pricePerHour = pricePerHour; }

    public Long getLastBookedBy() { return lastBookedBy; }
    public void setLastBookedBy(Long lastBookedBy) { this.lastBookedBy = lastBookedBy; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public enum SlotType { REGULAR, VIP, DISABLED }
    public enum SlotStatus { AVAILABLE, BOOKED, BLOCKED }
}