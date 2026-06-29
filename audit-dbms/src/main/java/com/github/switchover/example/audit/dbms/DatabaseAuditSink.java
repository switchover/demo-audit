package com.github.switchover.example.audit.dbms;

import com.github.switchover.example.audit.core.AuditEvent;
import com.github.switchover.example.audit.core.AuditSink;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseAuditSink implements AuditSink {
    private final AuditRepository auditRepository;

    @Override
    public void send(AuditEvent event) {
        AuditEntity entity = new AuditEntity();
        entity.setPath(event.path());
        entity.setMethod(event.method());
        entity.setApplicationName(event.applicationName());
        entity.setElapsedMs(event.elapsedMs());

        auditRepository.save(entity);
    }
}
