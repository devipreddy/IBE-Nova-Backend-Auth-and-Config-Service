package com.configandauth.config.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import lombok.Data;

@Data
public class InternalPricingResponse {

    private Map<LocalDate, BigDecimal> dateBreakdown;
    private BigDecimal totalPrice;
    private String appliedDeal;
    private boolean inventoryAvailable;
}