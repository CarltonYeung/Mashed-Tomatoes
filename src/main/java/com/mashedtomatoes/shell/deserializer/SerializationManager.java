package com.mashedtomatoes.shell.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mashape.unirest.http.Unirest;
import com.mashedtomatoes.model.media.Movie;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Component
public class SerializationManager {
    private final static DateFormat df = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);

    public SerializationManager() {
        Unirest.setObjectMapper(new ObjectMapper());
    }

    public static class ObjectMapper implements com.mashape.unirest.http.ObjectMapper {
        private com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        public ObjectMapper() {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.setDateFormat(df);
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Movie.class, new MovieDeserializer());
            module.addDeserializer(CharacterTalentIdSet.class, new CharacterTalentIdSetDeserializer());
            objectMapper.registerModule(module);
        }

        public <T> T readValue(String value, Class<T> valueType) {
            try {
                return objectMapper.readValue(value, valueType);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

        public String writeValue(Object value) {
            try {
                return objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

    }
}