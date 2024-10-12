package com.asimiotech.demo.web;

import com.asimiotech.demo.ThreadLocalTargetSourceDemoApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ SpringExtension.class, OutputCaptureExtension.class })
@SpringBootTest(classes = ThreadLocalTargetSourceDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { ThreadLocalTargetSourceDemoApplication.class })
@Slf4j
public class DemoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void beforeEach() {
        RestAssured.port = this.port;
    }

    @Test
    public void shouldReturnTenant2Sync() {
        String actualContent = RestAssured
                .given()
                    .accept(ContentType.TEXT)
                    .header("X-TENANT-ID", "tenant_2")
                .when()
                    .get("/demo/sync")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract().asString();

        MatcherAssert.assertThat(actualContent, Matchers.equalTo("Sync Task. Tenant: tenant_2"));
    }

    @Test
    public void shouldTenant2Async(CapturedOutput output) {
        String actualContent = RestAssured
                .given()
                    .accept(ContentType.TEXT)
                    .header("X-TENANT-ID", "tenant_2")
                .when()
                    .get("/demo/async")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract().asString();

        MatcherAssert.assertThat(actualContent, Matchers.equalTo("Async Task. Tenant: tenant_2"));
        MatcherAssert.assertThat(output.getOut(), Matchers.containsString("auto-1-exec-1] com.asimiotech.demo.web.DemoController   : Before Async. Tenant: tenant_2"));
        MatcherAssert.assertThat(output.getOut(), Matchers.containsString("async-thread-1] com.asimiotech.demo.web.DemoController   : Async Task. Tenant: tenant_2"));
        MatcherAssert.assertThat(output.getOut(), Matchers.containsString("auto-1-exec-1] com.asimiotech.demo.web.DemoController   : After Async. Tenant: tenant_2"));
    }

}