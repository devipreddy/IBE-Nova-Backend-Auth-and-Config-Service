package com.configandauth.config.client;

import com.configandauth.config.dto.InternalPricingRequest;
import com.configandauth.config.dto.InternalPricingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.configandauth.config.config.FeignConfig;


@FeignClient(name = "searchClient", url = "${search.service.url}", configuration = FeignConfig.class)
public interface SearchClient {

    @PostMapping("/internal/pricing")
    InternalPricingResponse getPricing(@RequestBody InternalPricingRequest request);
}