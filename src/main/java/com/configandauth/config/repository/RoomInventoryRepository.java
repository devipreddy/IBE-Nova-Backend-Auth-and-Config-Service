
package com.configandauth.config.repository;

import com.configandauth.config.entity.RoomInventory;
import com.configandauth.config.entity.RateCalendarProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RoomInventoryRepository extends JpaRepository<RoomInventory, UUID> {

    @Query(value = """
    SELECT
        property_id as propertyId,
        date as date,
        CASE
            WHEN SUM(available_inventory) = 0 THEN 0
            ELSE MIN(CASE WHEN available_inventory > 0 THEN price END)
        END AS minPrice
    FROM room_inventory
    WHERE tenant_id = :tenantId
    GROUP BY property_id, date
    ORDER BY property_id, date
    """, nativeQuery = true)
    List<RateCalendarProjection> findMinRates(@Param("tenantId") UUID tenantId);
}