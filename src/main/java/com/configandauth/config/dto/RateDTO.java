package com.configandauth.config.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RateDTO {

    private LocalDate date;

    private BigDecimal minPrice;
}
