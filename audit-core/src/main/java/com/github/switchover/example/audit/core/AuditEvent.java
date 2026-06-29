package com.github.switchover.example.audit.core;

public record AuditEvent(
    String applicationName,
    String path,
    String method,
    long elapsedMs
) {
}
