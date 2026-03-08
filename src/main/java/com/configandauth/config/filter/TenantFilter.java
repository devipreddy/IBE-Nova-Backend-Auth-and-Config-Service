package com.configandauth.config.filter;

import com.configandauth.config.context.TenantContext;
import com.configandauth.config.exception.MissingTenantException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.UUID;

import java.io.IOException;

public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String tenantHeader = request.getHeader("X-Tenant-ID");

        if (tenantHeader == null || tenantHeader.isBlank()) {
            throw new MissingTenantException("X-Tenant-ID header is required");
        }

        UUID tenantId = UUID.fromString(tenantHeader);

        try {

            TenantContext.setTenantId(tenantId);

            filterChain.doFilter(request, response);

        } finally {

            TenantContext.clear();

        }
    }
}
