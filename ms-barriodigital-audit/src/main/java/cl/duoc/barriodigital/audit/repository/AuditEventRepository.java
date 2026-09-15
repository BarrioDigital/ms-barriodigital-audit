package cl.duoc.barriodigital.audit.repository;

import cl.duoc.barriodigital.audit.entity.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {

    boolean existsByEventId(String eventId);

    List<AuditEvent> findByRequestIdOrderByEventTimestampAsc(Long requestId);

    List<AuditEvent> findByUserIdOrderByEventTimestampDesc(String userId);

    List<AuditEvent> findByEventTypeOrderByEventTimestampDesc(String eventType);

    List<AuditEvent> findByEventTimestampBetweenOrderByEventTimestampDesc(
            Instant start,
            Instant end
    );
}