package com.configandauth.config.controller;

import com.configandauth.config.dto.CheckoutRequestDTO;
import com.configandauth.config.dto.CheckoutResponseDTO;
import com.configandauth.config.entity.CheckoutSession;
import com.configandauth.config.entity.CheckoutSessionId;
import com.configandauth.config.service.CheckoutService;
import com.configandauth.config.context.TenantContext;
import com.configandauth.config.repository.CheckoutSessionRepository;
import com.configandauth.config.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final CheckoutSessionRepository checkoutSessionRepository;

    @PostMapping
    public CheckoutResponseDTO checkout(@RequestBody CheckoutRequestDTO request) {
        return checkoutService.createCheckout(request);
    }

    @GetMapping("/session/{id}")
    public CheckoutSession getSession(@PathVariable UUID id) {

        UUID tenantId = TenantContext.getTenantId();

        CheckoutSessionId sessionId = new CheckoutSessionId();
        sessionId.setTenantId(tenantId);
        sessionId.setSessionId(id);

        return checkoutSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
    }
}