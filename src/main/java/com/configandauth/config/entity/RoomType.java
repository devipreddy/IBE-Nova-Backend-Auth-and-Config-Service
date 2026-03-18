package com.configandauth.config.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.configandauth.config.entity.RoomTypeId;

@Entity
@Table(name = "room_types")
@Data
public class RoomType {

    @EmbeddedId
    private RoomTypeId id;

    @Column(name = "title")
    private String title;
}