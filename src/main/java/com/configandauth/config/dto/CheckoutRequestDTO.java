package com.configandauth.config.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CheckoutRequestDTO {

    private String propertyName;
    private String roomType;

    private int rooms;
    private int adults;
    private int teens;
    private int kids;

    private LocalDate checkin;
    private LocalDate checkout;

    private String deal;
}