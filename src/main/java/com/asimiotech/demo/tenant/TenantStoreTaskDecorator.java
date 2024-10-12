package com.asimiotech.demo.tenant;

import org.springframework.core.task.TaskDecorator;

public class TenantStoreTaskDecorator implements TaskDecorator {

    private final TenantStore tenantStore;

    public TenantStoreTaskDecorator(TenantStore tenantStore) {
        this.tenantStore = tenantStore;
    }

    @Override
    public Runnable decorate(Runnable task) {
        String tenantId = this.tenantStore.getTenantId();
        return () -> {
            try {
                this.tenantStore.setTenantId(tenantId);
                task.run();
            } finally {
                this.tenantStore.setTenantId(null);
            }
        };
    }

}