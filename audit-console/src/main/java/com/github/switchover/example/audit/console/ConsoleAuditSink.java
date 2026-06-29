package com.github.switchover.example.audit.console;

import com.github.switchover.example.audit.core.AuditEvent;
import com.github.switchover.example.audit.core.AuditSink;

public class ConsoleAuditSink implements AuditSink {
    @Override
    public void send(AuditEvent event) {
        System.out.printf("AUDIT: %s %s %s took %d ms%n",
            event.method(),
            event.path(),
            event.applicationName(),
            event.elapsedMs()
        );
    }
}
