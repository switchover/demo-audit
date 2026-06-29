package com.github.switchover.example.audit.core.aspect;

import com.github.switchover.example.audit.core.AuditLogger;
import com.github.switchover.example.audit.core.metrics.MetricCollector;
import com.github.switchover.example.audit.core.metrics.micrometer.MicrometerCollector;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@RequiredArgsConstructor
public class ControllerAuditAspect {
    private final AuditLogger auditLogger;
    private final List<MetricCollector> metricCollectors;

    private static final String CONTROLLER_CALLS_METRIC = "demo.audit.controller.calls";
    private static final String CONTROLLER_DURATION_METRIC = "demo.audit.controller.duration";

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object auditControllerCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long elapsedNanos = System.nanoTime() - start;
            String path = joinPoint.getSignature().getDeclaringTypeName() + "#" + joinPoint.getSignature().getName();
            auditLogger.log(path, "CONTROLLER", TimeUnit.NANOSECONDS.toMillis(elapsedNanos));

            metricCollectors.forEach(collector -> {
                collector.counting(CONTROLLER_CALLS_METRIC);
                collector.timing(CONTROLLER_DURATION_METRIC, Duration.ofNanos(elapsedNanos));
            });
        }
    }

    public static List<MicrometerCollector.CounterRecord> getCounterRecords() {
        return List.of(
            new MicrometerCollector.CounterRecord(CONTROLLER_CALLS_METRIC, "Number of controller calls", "calls")
        );
    }

    public static List<MicrometerCollector.TimerRecord> getTimerRecords() {
        return List.of(
            new MicrometerCollector.TimerRecord(CONTROLLER_DURATION_METRIC, "Duration of controller calls")
        );
    }
}
