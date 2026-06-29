package com.github.switchover.example.audit.core;

public class NoOpAuditSink implements AuditSink {
    @Override
    public void send(AuditEvent event) {
        // No operation performed
    }
}
