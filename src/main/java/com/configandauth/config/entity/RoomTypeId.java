package com.configandauth.config.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RoomTypeId implements Serializable {

    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(name = "room_type_id")
    private UUID roomTypeId;

    @Column(name = "property_id")
    private UUID propertyId;
}