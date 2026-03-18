package com.configandauth.config.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class InternalPricingRequest {

    private UUID propertyId;
    private UUID roomTypeId;
    private int rooms;
    private LocalDate checkin;
    private LocalDate checkout;
    private String deal;
}