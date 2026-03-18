package com.configandauth.config.repository;

import com.configandauth.config.entity.RoomType;
import com.configandauth.config.entity.RoomTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoomTypeRepository extends JpaRepository<RoomType, RoomTypeId> {

    List<RoomType> findByIdTenantIdAndIdPropertyId(UUID tenantId, UUID propertyId);
}