package com.github.switchover.example.order.audit;

import com.github.switchover.example.audit.core.AuditEvent;
import com.github.switchover.example.audit.core.AuditSink;
import org.springframework.stereotype.Component;

//@Component
public class CustomAuditSink implements AuditSink {
    @Override
    public void send(AuditEvent event) {
        // Custom logic to handle the audit event, e.g., send to an external system
        System.out.printf("Custom Audit: %s %s %s took %d ms%n",
            event.method(),
            event.path(),
            event.applicationName(),
            event.elapsedMs()
        );
    }
}
