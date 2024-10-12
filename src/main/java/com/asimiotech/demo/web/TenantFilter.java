package com.asimiotech.demo.web;

import com.asimiotech.demo.tenant.TenantStore;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class TenantFilter implements Filter {

	private static final String TENANT_HEADER_NAME = "X-TENANT-ID";

	@Autowired
	private TenantStore tenantStore;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// NOOP
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

	    // LOGGER.info("Thread had tenant data: {} from an old request", this.tenantStore.getTenantId());

	    HttpServletRequest request = (HttpServletRequest) servletRequest;
		String tenantId = request.getHeader(TENANT_HEADER_NAME);
		try {
			this.tenantStore.setTenantId(tenantId);
			chain.doFilter(servletRequest, servletResponse);
		} finally {
		    // Otherwise when a previously used container thread is used, it will have the old tenant id set and
		    // if for some reason this filter is skipped, tenantStore will hold an unreliable value
		    this.tenantStore.clear();
		}
	}

	@Override
	public void destroy() {
		// NOOP
	}

}