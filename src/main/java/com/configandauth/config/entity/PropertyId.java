package com.configandauth.config.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class PropertyId implements Serializable {

    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(name = "property_id")
    private UUID propertyId;

}