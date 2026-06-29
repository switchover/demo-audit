package com.github.switchover.example.audit.core.metrics.micrometer;

import com.github.switchover.example.audit.core.metrics.MetricCollector;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
public class MicrometerCollector implements MetricCollector {
    private final MeterRegistry meterRegistry;

    private final List<CounterRecord> counterRecords;
    private final List<TimerRecord> timerRecords;

    @Override
    public void counting(String name) {
        meterRegistry.counter(name).increment();
    }

    @Override
    public void timing(String name, Duration duration) {
        meterRegistry.timer(name).record(duration);
    }

    @PostConstruct
    public void postConstruct() {
        counterRecords.forEach(record -> {
            Counter.builder(record.name())
                .description(record.description())
                .baseUnit(record.unit())
                .register(meterRegistry);
        });
        timerRecords.forEach(record -> {
            Timer.builder(record.name())
                .description(record.description())
                .register(meterRegistry);
        });
    }

    public record CounterRecord(String name, String description, String unit) {}
    public record TimerRecord(String name, String description) {}
}
