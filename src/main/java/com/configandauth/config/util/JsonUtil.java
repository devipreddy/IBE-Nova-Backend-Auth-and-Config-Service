package com.configandauth.config.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static ObjectMapper mapper() {
        return MAPPER;
    }
}
