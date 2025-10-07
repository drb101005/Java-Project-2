package com.parkease.config;

import com.parkease.model.Slot;
import com.parkease.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SlotRepository slotRepository;

    @Override
    public void run(String... args) {
        if (slotRepository.count() == 0) {
            // Ground Floor
            createSlot("A1", "Ground Floor", 1, 1, Slot.SlotType.REGULAR, new BigDecimal("20"));
            createSlot("A2", "Ground Floor", 1, 2, Slot.SlotType.VIP, new BigDecimal("50"));
            createSlot("A3", "Ground Floor", 1, 3, Slot.SlotType.REGULAR, new BigDecimal("20"));
            createSlot("A4", "Ground Floor", 1, 4, Slot.SlotType.REGULAR, new BigDecimal("20"));
            
            createSlot("B1", "Ground Floor", 2, 1, Slot.SlotType.REGULAR, new BigDecimal("20"));
            createSlot("B2", "Ground Floor", 2, 2, Slot.SlotType.REGULAR, new BigDecimal("20"));
            createSlot("B3", "Ground Floor", 2, 3, Slot.SlotType.VIP, new BigDecimal("50"));
            createSlot("B4", "Ground Floor", 2, 4, Slot.SlotType.REGULAR, new BigDecimal("20"));

            // First Floor
            createSlot("C1", "First Floor", 3, 1, Slot.SlotType.VIP, new BigDecimal("50"));
            createSlot("C2", "First Floor", 3, 2, Slot.SlotType.REGULAR, new BigDecimal("25"));
            createSlot("C3", "First Floor", 3, 3, Slot.SlotType.REGULAR, new BigDecimal("25"));
            createSlot("C4", "First Floor", 3, 4, Slot.SlotType.REGULAR, new BigDecimal("25"));

            createSlot("D1", "First Floor", 4, 1, Slot.SlotType.REGULAR, new BigDecimal("25"));
            createSlot("D2", "First Floor", 4, 2, Slot.SlotType.VIP, new BigDecimal("50"));
            createSlot("D3", "First Floor", 4, 3, Slot.SlotType.REGULAR, new BigDecimal("25"));
            createSlot("D4", "First Floor", 4, 4, Slot.SlotType.DISABLED, new BigDecimal("15"));

            System.out.println("✅ Sample parking slots created!");
        }
    }

    private void createSlot(String name, String zone, int row, int col, Slot.SlotType type, BigDecimal price) {
        Slot slot = new Slot();
        slot.setSlotName(name);
        slot.setZone(zone);
        slot.setRow(row);
        slot.setCol(col);
        slot.setType(type);
        slot.setStatus(Slot.SlotStatus.AVAILABLE);
        slot.setPricePerHour(price);
        slotRepository.save(slot);
    }
}