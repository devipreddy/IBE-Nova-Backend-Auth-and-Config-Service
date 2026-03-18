package com.configandauth.config.repository;

import com.configandauth.config.entity.CheckoutSession;
import com.configandauth.config.entity.CheckoutSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutSessionRepository extends JpaRepository<CheckoutSession, CheckoutSessionId> {
}