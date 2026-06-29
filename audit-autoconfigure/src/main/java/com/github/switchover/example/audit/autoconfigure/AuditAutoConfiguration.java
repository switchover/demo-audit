package com.github.switchover.example.audit.autoconfigure;

import com.github.switchover.example.audit.autoconfigure.dbms.DbmsConfiguration;
import com.github.switchover.example.audit.autoconfigure.micrometer.MicrometerConfiguration;
import com.github.switchover.example.audit.console.ConsoleAuditSink;
import com.github.switchover.example.audit.console.LoggerAuditSink;
import com.github.switchover.example.audit.core.AuditLogger;
import com.github.switchover.example.audit.core.AuditSink;
import com.github.switchover.example.audit.core.NoOpAuditSink;
import com.github.switchover.example.audit.core.aspect.ControllerAuditAspect;
import com.github.switchover.example.audit.core.metrics.MetricCollector;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(AuditProperties.class)
@ConditionalOnProperty(prefix = "demo.audit",
    name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({DbmsConfiguration.class, MicrometerConfiguration.class})
public class AuditAutoConfiguration {
    @Bean
    @ConditionalOnClass(name = "com.github.switchover.example.audit.console.ConsoleAuditSink")
    @ConditionalOnMissingBean
    public AuditSink consoleAuditSink(AuditProperties properties) {
        if (properties.getConsoleType() == AuditProperties.ConsoleType.LOGGER) {
            log.info("📗 Using logger audit sink");
            return new LoggerAuditSink();
        }
        log.info("🖥️ Using console audit sink");
        return new ConsoleAuditSink();
    }

    @Bean
    @ConditionalOnMissingClass({
        "com.github.switchover.example.audit.console.ConsoleAuditSink",
        "com.github.switchover.example.audit.dbms.DatabaseAuditSink"
    })
    @ConditionalOnMissingBean
    public AuditSink noOpAuditSink() {
        log.warn("❌ No audit sink configured, auditing disabled");
        return new NoOpAuditSink();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuditLogger auditLogger(AuditProperties properties, AuditSink auditSink) {
        return new AuditLogger(properties.getApplicationName(), auditSink);
    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.web.bind.annotation.RestController")
    @ConditionalOnMissingBean
    public ControllerAuditAspect controllerAuditAspect(AuditLogger auditLogger, List<MetricCollector> metricCollectors) {
        return new ControllerAuditAspect(auditLogger, metricCollectors);
    }
}
