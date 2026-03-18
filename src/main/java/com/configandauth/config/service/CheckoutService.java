package com.configandauth.config.service;

import com.configandauth.config.client.SearchClient;
import com.configandauth.config.config.PricingRule;
import com.configandauth.config.config.TenantPricingConfig;
import com.configandauth.config.context.TenantContext;
import com.configandauth.config.dto.CheckoutRequestDTO;
import com.configandauth.config.dto.CheckoutResponseDTO;
import com.configandauth.config.dto.InternalPricingRequest;
import com.configandauth.config.dto.InternalPricingResponse;
import com.configandauth.config.entity.CheckoutSession;
import com.configandauth.config.entity.CheckoutSessionId;
import com.configandauth.config.entity.Property;
import com.configandauth.config.exception.ResourceNotFoundException;
import com.configandauth.config.repository.CheckoutSessionRepository;
import com.configandauth.config.repository.PropertyRepository;
import com.configandauth.config.repository.PromotionRepository;
import com.configandauth.config.repository.RoomTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final PropertyRepository propertyRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final PromotionRepository promotionRepository;
    private final SearchClient searchClient;
    private final CheckoutSessionRepository checkoutSessionRepository;
    private final SessionCacheService sessionCacheService;

    private final ObjectMapper objectMapper;

    @Transactional
    public CheckoutResponseDTO createCheckout(CheckoutRequestDTO request) {

        UUID tenantId = TenantContext.getTenantId();

        if (tenantId == null) {
            throw new RuntimeException("Tenant not resolved");
        }

        Property property = propertyRepository.findByIdTenantId(tenantId)
                .stream()
                .filter(p -> p.getName().equalsIgnoreCase(request.getPropertyName()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        UUID propertyId = property.getId().getPropertyId();

        UUID roomTypeId = roomTypeRepository
                .findByIdTenantIdAndIdPropertyId(tenantId, propertyId)
                .stream()
                .filter(rt -> rt.getTitle().equalsIgnoreCase(request.getRoomType()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Room type not found"))
                .getId().getRoomTypeId();

        UUID promoId = null;

        if (request.getDeal() != null) {
            promoId = promotionRepository.findByIdTenantId(tenantId)
                    .stream()
                    .filter(p -> p.getTitle().equalsIgnoreCase(request.getDeal()))
                    .findFirst()
                    .map(p -> p.getId().getPromoId())
                    .orElse(null);
        }

        InternalPricingRequest pricingRequest = new InternalPricingRequest();
        pricingRequest.setPropertyId(propertyId);
        pricingRequest.setRoomTypeId(roomTypeId);
        pricingRequest.setRooms(request.getRooms());
        pricingRequest.setCheckin(request.getCheckin());
        pricingRequest.setCheckout(request.getCheckout());
        pricingRequest.setDeal(request.getDeal());

        InternalPricingResponse pricing;

        try {
            pricing = searchClient.getPricing(pricingRequest);
        } catch (Exception ex) {
            throw new RuntimeException("Search service unavailable");
        }

        if (!pricing.isInventoryAvailable()) {
            throw new RuntimeException("Inventory not available");
        }

        PricingRule rule = TenantPricingConfig.getRule(tenantId);

        BigDecimal taxPerNight = rule.getTaxPerNight();
        BigDecimal surchargePerNight = rule.getSurchargePerNight();
        BigDecimal dueNowPercent = rule.getDueNowPercentage();

        BigDecimal subtotal = pricing.getTotalPrice();

        int nights = pricing.getDateBreakdown().size();

        BigDecimal totalTax = taxPerNight.multiply(BigDecimal.valueOf(nights));
        BigDecimal totalSurcharge = surchargePerNight.multiply(BigDecimal.valueOf(nights));

        BigDecimal taxSurcharges = totalTax.add(totalSurcharge);
        BigDecimal finalTotal = subtotal.add(taxSurcharges);

        BigDecimal dueNow = finalTotal.multiply(dueNowPercent);
        BigDecimal dueResort = finalTotal.subtract(dueNow);

        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
        taxSurcharges = taxSurcharges.setScale(2, RoundingMode.HALF_UP);
        finalTotal = finalTotal.setScale(2, RoundingMode.HALF_UP);
        dueNow = dueNow.setScale(2, RoundingMode.HALF_UP);
        dueResort = dueResort.setScale(2, RoundingMode.HALF_UP);

        UUID sessionId = UUID.randomUUID();

        CheckoutResponseDTO response = CheckoutResponseDTO.builder()
                .sessionId(sessionId)
                .propertyName(request.getPropertyName())
                .roomType(request.getRoomType())
                .rooms(request.getRooms())
                .adults(request.getAdults())
                .teens(request.getTeens())
                .kids(request.getKids())
                .checkin(request.getCheckin())
                .checkout(request.getCheckout())
                .deal(pricing.getAppliedDeal())
                .dateBreakdown(pricing.getDateBreakdown())
                .subtotalPrice(subtotal)
                .taxSurcharges(taxSurcharges)
                .taxPerNight(taxPerNight)
                .surchargePerNight(surchargePerNight)
                .totalPrice(finalTotal)
                .dueNow(dueNow)
                .dueResort(dueResort)
                .build();

        CheckoutSession session = new CheckoutSession();

        CheckoutSessionId id = new CheckoutSessionId();
        id.setTenantId(tenantId);
        id.setSessionId(sessionId);

        session.setId(id);
        session.setSessionData(response);
        session.setPropertyId(propertyId); 
        session.setRoomTypeId(roomTypeId);
        session.setCheckin(request.getCheckin());
        session.setCheckout(request.getCheckout());
        session.setPromoId(promoId);
        session.setPriceSnapshot(finalTotal);

        session.setCreatedAt(Instant.now());
        session.setExpiresAt(Instant.now().plusSeconds(900));

        checkoutSessionRepository.save(session);


        sessionCacheService.store("session:" + sessionId, response);

        return response;
    }
}