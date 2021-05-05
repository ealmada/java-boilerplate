package com.asapp.backend.challenge.utils;

import com.asapp.backend.challenge.resources.MessageResource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.io.StringWriter;

public class JSONUtil {
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOEXception while mapping object (" + data + ") to JSON");
        }
    }

    public static String dataToJsonWithCustomSerializer(Object data, StdSerializer stdSerializer, Class classz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
            SimpleModule module = new SimpleModule();
            module.addSerializer(classz, stdSerializer);
            mapper.registerModule(module);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOEXception while mapping object (" + data + ") to JSON with Custom Serializer(" + stdSerializer + ")");
        }
    }

    public static Object jsonToData(String json, Class classz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
            return mapper.readValue(json, classz);
        } catch (IOException e) {
            throw new RuntimeException("IOEXception while mapping json (" + json + ") to Object");
        }
    }

    public static Object jsonToDataWithCustomDeserializer(String json, StdDeserializer stdDeserializer, Class classz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
            SimpleModule module = new SimpleModule();
            module.addDeserializer(MessageResource.class, stdDeserializer);
            mapper.registerModule(module);
            StringWriter sw = new StringWriter();
            return mapper.readValue(json, classz);
        } catch (IOException e) {
            throw new RuntimeException("IOEXception while mapping object (" + json + ") to JSON with Custom Deserializer(" + classz + ") for Class(" + classz + ")");
        }
    }

    public static String readPropertyFromJson(String json, String property) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
            JsonNode jsonNode = mapper.readTree(json);
            return jsonNode.get(property).asText();
        } catch (IOException e) {
            throw new RuntimeException("IOEXception while getting property (" + property + ") from json (" + json + ")");
        }
    }

}
