package com.configandauth.config.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.configandauth.config.context.TenantContext;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor tenantInterceptor() {
        return requestTemplate -> {
            if (TenantContext.getTenantId() != null) {
                requestTemplate.header(
                        "X-Tenant-ID",
                        TenantContext.getTenantId().toString()
                );
            }
        };
    }
}