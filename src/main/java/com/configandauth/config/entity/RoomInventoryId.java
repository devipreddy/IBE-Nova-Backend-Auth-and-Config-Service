package com.configandauth.config.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Embeddable
public class RoomInventoryId implements Serializable {

    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(name = "room_type_id")
    private UUID roomTypeId;

    @Column(name = "date")
    private LocalDate date;
}