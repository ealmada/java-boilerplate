package com.asapp.backend.challenge.utils;

import com.asapp.backend.challenge.resources.MessageResource;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SendMessageResponseSerializer extends StdSerializer<MessageResource> {


    public SendMessageResponseSerializer() {
        this(null);
    }

    public SendMessageResponseSerializer(Class<MessageResource> t) {
        super(t);
    }

    @Override
    public void serialize(
            MessageResource messageResource, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", messageResource.getId());
        jsonGenerator.writeStringField("timestamp", messageResource.getTimestamp().toString());
        jsonGenerator.writeEndObject();
    }
}
