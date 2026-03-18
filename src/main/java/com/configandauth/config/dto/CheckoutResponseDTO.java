package com.configandauth.config.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class CheckoutResponseDTO implements Serializable {

    private UUID sessionId;

    private String propertyName;
    private String roomType;

    private int rooms;
    private int adults;
    private int teens;
    private int kids;

    private LocalDate checkin;
    private LocalDate checkout;

    private String deal;

    private Map<LocalDate, BigDecimal> dateBreakdown;

    private BigDecimal subtotalPrice;
    private BigDecimal taxSurcharges;

    private BigDecimal taxPerNight;
    private BigDecimal surchargePerNight;

    private BigDecimal totalPrice;
    private BigDecimal dueNow;
    private BigDecimal dueResort;
}