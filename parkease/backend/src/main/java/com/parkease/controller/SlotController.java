package com.parkease.controller;

import com.parkease.model.Slot;
import com.parkease.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin(origins = "*")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @GetMapping("/map")
    public ResponseEntity<List<Slot>> getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<?> bookSlot(
            @PathVariable Long id,
            @RequestBody Map<String, Long> request) {
        try {
            Long userId = request.get("userId");
            Map<String, Object> result = slotService.bookSlot(id, userId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409)
                    .body(Map.of("error", e.getMessage(), "slotId", id));
        }
    }
}