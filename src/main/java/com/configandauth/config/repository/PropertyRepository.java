package com.configandauth.config.repository;

import com.configandauth.config.entity.Property;
import com.configandauth.config.entity.PropertyId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, PropertyId> {

    List<Property> findByIdTenantId(UUID tenantId);

}