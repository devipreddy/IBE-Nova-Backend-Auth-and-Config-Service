package com.configandauth.config.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ConfigResponseDTO {

    private Map<String, Object> config;

}