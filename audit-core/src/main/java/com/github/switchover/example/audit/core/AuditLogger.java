package com.github.switchover.example.audit.core;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuditLogger {
    private final String applicationName;
    private final AuditSink auditSink;

    public void log(String path, String method, long elapsedMs) {
        auditSink.send(new AuditEvent(applicationName, path, method, elapsedMs));
    }
}
