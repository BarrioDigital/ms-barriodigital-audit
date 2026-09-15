package cl.duoc.barriodigital.audit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestEventDTO {

    private Long requestId;
    private Long procedureId;
    private String oldStatus;
    private String newStatus;

    @JsonProperty("timestamp")
    private Instant timestamp;
}