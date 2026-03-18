package com.configandauth.config.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.configandauth.config.dto.CheckoutResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "checkout_sessions")
@Data
public class CheckoutSession {

    @EmbeddedId
    private CheckoutSessionId id;

    @Column(name = "property_id")
    private UUID propertyId;

    @Column(name = "room_type_id")
    private UUID roomTypeId;

    @Column(name = "promo_id")
    private UUID promoId;

    private LocalDate checkin;
    private LocalDate checkout;

    @Column(name = "price_snapshot")
    private BigDecimal priceSnapshot;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "session_data", columnDefinition = "jsonb")
    private CheckoutResponseDTO sessionData;

    private Instant createdAt;
    private Instant expiresAt;
}