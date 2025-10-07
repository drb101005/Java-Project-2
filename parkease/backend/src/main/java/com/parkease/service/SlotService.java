package com.parkease.service;

import com.parkease.model.Booking;
import com.parkease.model.Slot;
import com.parkease.repository.BookingRepository;
import com.parkease.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    @Transactional
    public Map<String, Object> bookSlot(Long slotId, Long userId) {
        Slot slot = slotRepository.findByIdWithLock(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getStatus() != Slot.SlotStatus.AVAILABLE) {
            throw new RuntimeException("Slot already booked");
        }

        slot.setStatus(Slot.SlotStatus.BOOKED);
        slot.setLastBookedBy(userId);
        slotRepository.save(slot);

        Booking booking = new Booking(userId, slotId);
        bookingRepository.save(booking);

        // Broadcast update via WebSocket
        broadcastSlotUpdate(slot);

        Map<String, Object> response = new HashMap<>();
        response.put("bookingId", booking.getId());
        response.put("slotId", slot.getId());
        response.put("slotName", slot.getSlotName());
        response.put("message", "Booked successfully");

        return response;
    }

    private void broadcastSlotUpdate(Slot slot) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "SLOT_UPDATED");
        message.put("slot", convertToDTO(slot));
        messagingTemplate.convertAndSend("/topic/slots", message);
    }

    private Map<String, Object> convertToDTO(Slot slot) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", slot.getId());
        dto.put("slot_name", slot.getSlotName());
        dto.put("zone", slot.getZone());
        dto.put("row", slot.getRow());
        dto.put("col", slot.getCol());
        dto.put("type", slot.getType().name());
        dto.put("status", slot.getStatus().name());
        dto.put("price_per_hour", slot.getPricePerHour());
        dto.put("last_booked_by", slot.getLastBookedBy());
        dto.put("version", slot.getVersion());
        dto.put("updated_at", slot.getUpdatedAt().toString());
        return dto;
    }
}