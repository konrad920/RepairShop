package edu.wsiiz.repairshop.audit.application;

import edu.wsiiz.repairshop.audit.domain.AuditLog;
import edu.wsiiz.repairshop.audit.domain.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository repository;

    public void log(String username, String action) {
        repository.save(new AuditLog(username, action, LocalDateTime.now()));
    }

    public List<AuditLog> getAll() {
        return repository.findAll();
    }
}