package com.example.attendancems.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;

public class SwipeEventDeserializer implements Deserializer<SwipeEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SwipeEventDeserializer() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public SwipeEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, SwipeEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing SwipeEvent", e);
        }
    }
}
