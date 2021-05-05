package com.asapp.backend.challenge.utils;

import com.asapp.backend.challenge.resources.UserResource;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CreateUserResponseSerializer extends StdSerializer<UserResource> {


    public CreateUserResponseSerializer() {
        this(null);
    }

    public CreateUserResponseSerializer(Class<UserResource> t) {
        super(t);
    }

    @Override
    public void serialize(
            UserResource userResource, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", userResource.getId());
        jsonGenerator.writeEndObject();
    }
}
