package com.github.switchover.example.audit.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "demo.audit")
public class AuditProperties {
    /**
     * Whether audit logging is enabled.
     */
    private boolean enabled = true;

    /**
     * Application name used in audit logs.
     */
    private String applicationName = "unknown-service";

    /**
     * Output type for console-based audit sink.
     */
    private ConsoleType consoleType = ConsoleType.LOGGER;

    public enum ConsoleType {
        /**
         * Log through logger framework.
         */
        LOGGER,
        /**
         * Print audit records to standard output.
         */
        SYSTEM_OUT
    }

    // getters/setters
}
