package cl.duoc.barriodigital.audit.service;

import cl.duoc.barriodigital.audit.dto.RequestEventDTO;
import cl.duoc.barriodigital.audit.entity.AuditEvent;
import cl.duoc.barriodigital.audit.repository.AuditEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditEventRepository repository;

    @KafkaListener(
            topics = "requests.events",
            groupId = "audit-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(RequestEventDTO event) {

        String eventId =
                "request-" + event.getRequestId()
                        + "-" + event.getNewStatus()
                        + "-" + event.getTimestamp();

        if (repository.existsByEventId(eventId)) {
            return;
        }

        AuditEvent auditEvent = AuditEvent.builder()
                .eventId(eventId)
                .requestId(event.getRequestId())
                .procedureId(event.getProcedureId())
                .eventType("REQUEST_STATUS_CHANGED")
                .oldStatus(event.getOldStatus())
                .newStatus(event.getNewStatus())
                .eventTimestamp(event.getTimestamp())
                .details(
                        "Cambio de estado de "
                                + event.getOldStatus()
                                + " a "
                                + event.getNewStatus()
                )
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

    public List<AuditEvent> findByDateRange(
            LocalDateTime start,
            LocalDateTime end
    ) {
        return repository
                .findByEventTimestampBetweenOrderByEventTimestampDesc(
                        start,
                        end
                );
    }
}