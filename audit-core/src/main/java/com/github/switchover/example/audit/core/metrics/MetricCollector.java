package com.github.switchover.example.audit.core.metrics;

import java.time.Duration;

public interface MetricCollector {
    void counting(String name);
    void timing(String name, Duration duration);
}
