package com.asapp.backend.challenge.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Utils {
    public static String getRequestBodyForCreatingAUser(String username, String password) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("username", username);
        requestBody.put("password", password);

        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody);
        return prettyJson;
    }


}
