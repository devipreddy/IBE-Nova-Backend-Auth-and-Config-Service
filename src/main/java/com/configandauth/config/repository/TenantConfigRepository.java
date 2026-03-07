package com.configandauth.config.repository;

import com.configandauth.config.entity.TenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TenantConfigRepository extends JpaRepository<TenantConfig, UUID> {
}