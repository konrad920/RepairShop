package edu.wsiiz.repairshop.audit.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String action;
    private LocalDateTime timestamp;

    public AuditLog(String username, String action, LocalDateTime timestamp) {
        this.username = username;
        this.action = action;
        this.timestamp = timestamp;
    }
}