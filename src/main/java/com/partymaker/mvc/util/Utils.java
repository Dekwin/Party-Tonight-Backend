package com.partymaker.mvc.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

public class Utils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> parseEncodedCollection(String data, Class<T> valueType) throws IOException {
        String encoded = URLDecoder.decode(data, "UTF-8");

        return objectMapper.readValue(encoded, objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
    }
}
