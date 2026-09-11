package cl.duoc.barriodigital.audit.controller;

import cl.duoc.barriodigital.audit.entity.AuditEvent;
import cl.duoc.barriodigital.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService service;

    @GetMapping
    public ResponseEntity<List<AuditEvent>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<List<AuditEvent>> findByRequestId(
            @PathVariable Long requestId) {

        return ResponseEntity.ok(
                service.findByRequestId(requestId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditEvent>> findByUserId(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                service.findByUserId(userId)
        );
    }

    @GetMapping("/type/{eventType}")
    public ResponseEntity<List<AuditEvent>> findByEventType(
            @PathVariable String eventType) {

        return ResponseEntity.ok(
                service.findByEventType(eventType)
        );
    }

   @GetMapping("/date")
    public ResponseEntity<List<AuditEvent>> findByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) {
        return ResponseEntity.ok(
                service.findByDateRange(start, end)
        );
    }
}