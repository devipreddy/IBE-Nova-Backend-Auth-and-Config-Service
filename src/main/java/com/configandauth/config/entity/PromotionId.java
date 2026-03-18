package com.configandauth.config.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PromotionId implements Serializable {

    private UUID tenantId;
    private UUID promoId;
}