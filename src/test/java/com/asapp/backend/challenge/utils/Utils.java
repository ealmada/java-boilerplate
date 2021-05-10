package com.asapp.backend.challenge.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Utils {
    public static String getRequestBodyForCreatingAUser(String username, String password) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String json = "{\"username\": \""+username+"\"," +
                "\"password\": \""+password+"\"}";

        return json;
    }


}
