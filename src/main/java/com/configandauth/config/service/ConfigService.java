package com.configandauth.config.service;

import com.configandauth.config.context.TenantContext;
import com.configandauth.config.dto.RateDTO;
import com.configandauth.config.entity.RateCalendarProjection;
import com.configandauth.config.entity.TenantConfig;
import com.configandauth.config.dto.ConfigResponseDTO;
import com.configandauth.config.entity.Property;
import com.configandauth.config.exception.MissingTenantException;
import com.configandauth.config.exception.ResourceNotFoundException;
import com.configandauth.config.repository.PropertyRepository;
import com.configandauth.config.repository.RoomInventoryRepository;
import com.configandauth.config.repository.TenantConfigRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final TenantConfigRepository tenantConfigRepository;
    private final RoomInventoryRepository roomInventoryRepository;
    private final PropertyRepository propertyRepository;
    private final ObjectMapper objectMapper;


    @Cacheable(value = "tenant-config", key = "#tenantId")
    public Map<String, Object> getConfigCached(UUID tenantId) throws Exception {

        TenantConfig tenantConfig = tenantConfigRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant config not found"));

        Map<String, Object> config =
                objectMapper.readValue(tenantConfig.getConfig(), Map.class);

        List<Property> properties = propertyRepository.findByIdTenantId(tenantId);

        Map<String, UUID> propertyNameToId = new HashMap<>();

        for (Property p : properties) {
            propertyNameToId.put(p.getName().toLowerCase(), p.getId().getPropertyId());
        }

        List<RateCalendarProjection> rates =
                roomInventoryRepository.findMinRates(tenantId);

        Map<UUID, List<RateDTO>> propertyRates = new HashMap<>();

        for (RateCalendarProjection rate : rates) {

            propertyRates
                    .computeIfAbsent(rate.getPropertyId(), k -> new ArrayList<>())
                    .add(new RateDTO(rate.getDate(), rate.getMinPrice()));

        }

        Object propertiesObj = config.get("properties");

        if (propertiesObj instanceof List<?>) {

            List<?> propertyNames = (List<?>) propertiesObj;

            for (Object nameObj : propertyNames) {

                String propertyName = nameObj.toString();

                UUID propertyId =
                        propertyNameToId.get(propertyName.toLowerCase());

                if (propertyId == null)
                    continue;

                Map<String, Object> propertyConfig =
                        (Map<String, Object>) config.get(propertyName);

                if (propertyConfig == null)
                    continue;

                List<RateDTO> dates =
                        propertyRates.getOrDefault(propertyId, new ArrayList<>());

                propertyConfig.put("dates", dates);
            }
        }

        return config;
    }


    public ConfigResponseDTO getConfig() throws Exception {

        UUID tenantId = TenantContext.getTenantId();

        if (tenantId == null)
            throw new MissingTenantException("Tenant not set in context");

        Map<String, Object> config = getConfigCached(tenantId);

        ConfigResponseDTO response = new ConfigResponseDTO();
        response.setConfig(config);

        return response;
    }
}