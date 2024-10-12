package com.asimiotech.demo.config;

import com.asimiotech.demo.tenant.TenantStore;
import com.asimiotech.demo.tenant.TenantStoreTaskDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskDecorator;

@Configuration
@Slf4j
public class TaskExecutorConfig {

    private final TaskExecutionProperties taskExecutionProperties;
    private final TenantStore tenantStore;

    public TaskExecutorConfig(TaskExecutionProperties taskExecutionProperties, TenantStore tenantStore) {
        this.taskExecutionProperties = taskExecutionProperties;
        this.tenantStore = tenantStore;
    }

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutorBuilder()
                .corePoolSize(this.taskExecutionProperties.getPool().getCoreSize())
                .maxPoolSize(this.taskExecutionProperties.getPool().getMaxSize())
                .queueCapacity(this.taskExecutionProperties.getPool().getQueueCapacity())
                .threadNamePrefix(this.taskExecutionProperties.getThreadNamePrefix())
                .taskDecorator(this.tenantStoreTaskDecorator())
                .build();
    }

    @Bean
    public TaskDecorator tenantStoreTaskDecorator() {
        return new TenantStoreTaskDecorator(this.tenantStore);
    }

}