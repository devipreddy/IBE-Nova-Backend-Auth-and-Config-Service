package com.configandauth.config.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface RateCalendarProjection {

    UUID getPropertyId();

    LocalDate getDate();

    BigDecimal getMinPrice();
}