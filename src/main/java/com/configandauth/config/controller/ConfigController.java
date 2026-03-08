package com.configandauth.config.controller;

import com.configandauth.config.dto.ConfigResponseDTO;
import com.configandauth.config.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @GetMapping
    public ConfigResponseDTO getConfig() throws Exception {
        return configService.getConfig();
    }
}
