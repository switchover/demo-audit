package com.github.switchover.example.audit.autoconfigure.dbms;

import com.github.switchover.example.audit.core.AuditSink;
import com.github.switchover.example.audit.dbms.AuditRepository;
import com.github.switchover.example.audit.dbms.DatabaseAuditSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DatabaseAuditSink.class)
@EnableJpaRepositories("com.github.switchover.example.audit")
@EntityScan("com.github.switchover.example.audit")
public class DbmsConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AuditSink dbmsAuditSink(AuditRepository auditRepository) {
        log.info("📔 Using database audit sink");
        return new DatabaseAuditSink(auditRepository);
    }
}
