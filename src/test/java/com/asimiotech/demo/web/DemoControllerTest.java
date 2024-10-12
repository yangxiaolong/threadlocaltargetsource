package com.asimiotech.demo.web;

import com.asimiotech.demo.tenant.TenantStore;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DemoControllerTest {

	@Mock
	private TenantStore tenantStore;

	@InjectMocks
	private DemoController controller;

	@Test
	public void shouldReturnBlahTenantId() {
		// Given
		Mockito.when(this.tenantStore.getTenantId()).thenReturn("blahTenantId");
		// When
		String result = this.controller.getDemo();
		// Then
		MatcherAssert.assertThat(result, Matchers.equalTo("Sync Task. Tenant: blahTenantId"));
	}

}