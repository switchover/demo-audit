package com.github.switchover.example.audit.console;

import com.github.switchover.example.audit.core.AuditEvent;
import com.github.switchover.example.audit.core.AuditSink;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerAuditSink implements AuditSink {
    @Override
    public void send(AuditEvent event) {
        log.info("AUDIT: {} {} {} took {} ms",
            event.method(),
            event.path(),
            event.applicationName(),
            event.elapsedMs()
        );
    }
}
