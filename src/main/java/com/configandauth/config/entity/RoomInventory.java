package com.configandauth.config.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.configandauth.config.entity.RoomInventoryId;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "room_inventory")
public class RoomInventory {

    @EmbeddedId
    private RoomInventoryId id;

    @Column(name = "total_inventory")
    private Integer totalInventory;

    @Column(name = "available_inventory")
    private Integer availableInventory;

    private BigDecimal price;

}