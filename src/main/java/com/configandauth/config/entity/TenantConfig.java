package com.configandauth.config.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tenant_config")
public class TenantConfig {

    @Id
    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(columnDefinition = "jsonb")
    private String config;
}