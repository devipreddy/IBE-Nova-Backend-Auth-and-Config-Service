package com.configandauth.config.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.configandauth.config.entity.PromotionId;

@Entity
@Table(name = "promotions")
@Data
public class Promotion {

    @EmbeddedId
    private PromotionId id;

    @Column(name = "title")
    private String title;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_value")
    private java.math.BigDecimal discountValue;
}