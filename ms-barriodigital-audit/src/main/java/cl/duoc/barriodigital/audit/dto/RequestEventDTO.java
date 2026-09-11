package cl.duoc.barriodigital.audit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestEventDTO {

    private String eventId;

    private String type;

    private Long requestId;

    private String userId;

    private String userRole;

    private String oldStatus;

    private String newStatus;

    private LocalDateTime timestamp;

    private String traceId;

    private String correlationId;

    private String details;
}