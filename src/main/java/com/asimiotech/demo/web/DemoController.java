package com.asimiotech.demo.web;

import com.asimiotech.demo.tenant.TenantStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/demo", produces = MediaType.TEXT_PLAIN_VALUE)
public class DemoController {

    Logger log = LoggerFactory.getLogger(DemoController.class);

    private final TenantStore tenantStore;
    private final AsyncTaskExecutor taskExecutor;

    public DemoController(TenantStore tenantStore, AsyncTaskExecutor taskExecutor) {
        this.tenantStore = tenantStore;
        this.taskExecutor = taskExecutor;
    }

    @RequestMapping(path = "/sync", method = RequestMethod.GET)
    public String getDemo() {
        return this.formattedString("Sync Task");
    }

    @RequestMapping(path = "/async", method = RequestMethod.GET)
    public String getAsyncDemo() throws Exception {
        this.formattedString("Before Async");
        Future<String> futureTask = this.calculateAsync();
        String result = futureTask.get();
        this.formattedString("After Async");
        return result;
    }

    private Future<String> calculateAsync() {
        return this.taskExecutor.submit(() -> this.formattedString("Async Task"));
    }

    private String formattedString(String prefix) {
        String result = String.format("%s. Tenant: %s", prefix, this.tenantStore.getTenantId());
        log.debug(result);
        return result;
    }

}