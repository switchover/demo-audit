package com.github.switchover.example.audit.core;

public interface AuditSink {
    void send(AuditEvent event);
}
