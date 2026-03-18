package com.configandauth.config.config;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import com.configandauth.config.config.PricingRule;


@Getter

public class TenantPricingConfig {

    public static final Map<UUID, PricingRule> RULES = Map.of(
            UUID.fromString("11111111-1111-1111-1111-111111111111"),
            new PricingRule(new BigDecimal("100"), new BigDecimal("50"), new BigDecimal("0.3")),

            UUID.fromString("22222222-2222-2222-2222-222222222222"),
            new PricingRule(new BigDecimal("150"), new BigDecimal("75"), new BigDecimal("0.4")),

            UUID.fromString("33333333-3333-3333-3333-333333333333"),
            new PricingRule(new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("0.5")),

            UUID.fromString("44444444-4444-4444-4444-444444444444"),
            new PricingRule(new BigDecimal("250"), new BigDecimal("125"), new BigDecimal("0.23")) // Feel free to adjust these values
    );

    public static PricingRule getRule(UUID tenantId) {
        return RULES.getOrDefault(
                tenantId,
                new PricingRule(new BigDecimal("100"), new BigDecimal("50"), new BigDecimal("0.3"))
        );
    }
}