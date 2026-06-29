package com.github.switchover.example.audit.autoconfigure.micrometer;

import com.github.switchover.example.audit.core.aspect.ControllerAuditAspect;
import com.github.switchover.example.audit.core.metrics.micrometer.MicrometerCollector;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MeterRegistry.class)
public class MicrometerConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Bean
    public MicrometerCollector micrometerCollector(MeterRegistry meterRegistry) {
        log.info("😊 MicrometerCollector bean created");
        return new MicrometerCollector(meterRegistry,
            ControllerAuditAspect.getCounterRecords(), ControllerAuditAspect.getTimerRecords());
    }
}
