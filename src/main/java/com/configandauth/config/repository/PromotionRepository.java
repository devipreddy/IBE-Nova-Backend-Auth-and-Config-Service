package com.configandauth.config.repository;

import com.configandauth.config.entity.Promotion;
import com.configandauth.config.entity.PromotionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PromotionRepository extends JpaRepository<Promotion, PromotionId> {

    List<Promotion> findByIdTenantId(UUID tenantId);
}