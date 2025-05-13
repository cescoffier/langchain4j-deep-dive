package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.jackson.ObjectMapperCustomizer;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class CaseInsensitiveObjectMapperCustomizer implements ObjectMapperCustomizer {
  @Override
  public void customize(ObjectMapper objectMapper) {
    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
  }
}
