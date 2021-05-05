package com.asapp.backend.challenge.utils;

import com.asapp.backend.challenge.resources.MessageResource;
import com.asapp.backend.challenge.resources.MetadataResource;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

public class GetMessageResponseSerializer extends StdSerializer<List<MessageResource>> {


    public GetMessageResponseSerializer() {
        this(null);
    }

    public GetMessageResponseSerializer(Class<List<MessageResource>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<MessageResource> messageResource, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart("messages");

        for (MessageResource message : messageResource) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", message.getId());
            jsonGenerator.writeStringField("timestamp", message.getTimestamp().toString());
            jsonGenerator.writeNumberField("sender", message.getSender());
            jsonGenerator.writeNumberField("recipient", message.getRecipient());

            jsonGenerator.writeObjectFieldStart("content");
            jsonGenerator.writeStringField("type", message.getType());
            jsonGenerator.writeStringField("text", message.getText());

            if(message.hasMetadata()){
                jsonGenerator.writeArrayFieldStart("metadata");
                for(MetadataResource metadata : message.getMetadataResourceList()){
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", metadata.getId());
                    jsonGenerator.writeStringField("type", metadata.getType());
                    jsonGenerator.writeStringField("metadata", metadata.getMetadata());
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndArray();
            }

            jsonGenerator.writeEndObject();

            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
