package edu.wsiiz.repairshop.payments.domain.settlement;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

    @Embeddable
    @Data
    public class Reminder {
        private String email;
        private String message;
        private LocalDate sentDate;
    }
