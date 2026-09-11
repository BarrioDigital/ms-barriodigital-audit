package cl.duoc.barriodigital.audit.service;

import cl.duoc.barriodigital.audit.dto.RequestEventDTO;
import cl.duoc.barriodigital.audit.entity.AuditEvent;
import cl.duoc.barriodigital.audit.repository.AuditEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditEventRepository repository;

    @KafkaListener(
            topics = "requests.events",
            groupId = "audit-service"
    )
    public void consume(RequestEventDTO event) {

        if (event.getEventId() != null
                && repository.existsByEventId(event.getEventId())) {
            return;
        }

        AuditEvent auditEvent = AuditEvent.builder()
                .eventId(event.getEventId())
                .requestId(event.getRequestId())
                .eventType(event.getType())
                .userId(event.getUserId())
                .userRole(event.getUserRole())
                .oldStatus(event.getOldStatus())
                .newStatus(event.getNewStatus())
                .eventTimestamp(event.getTimestamp())
                .traceId(event.getTraceId())
                .correlationId(event.getCorrelationId())
                .details(event.getDetails())
                .build();

        repository.save(auditEvent);
    }

    public List<AuditEvent> findAll() {
        return repository.findAll();
    }

    public List<AuditEvent> findByRequestId(Long requestId) {
        return repository.findByRequestIdOrderByEventTimestampAsc(requestId);
    }

    public List<AuditEvent> findByUserId(String userId) {
        return repository.findByUserIdOrderByEventTimestampDesc(userId);
    }

    public List<AuditEvent> findByEventType(String eventType) {
        return repository.findByEventTypeOrderByEventTimestampDesc(eventType);
    }
}