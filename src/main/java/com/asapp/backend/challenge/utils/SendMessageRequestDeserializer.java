package com.asapp.backend.challenge.utils;

import com.asapp.backend.challenge.resources.MessageResource;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class SendMessageRequestDeserializer extends StdDeserializer<MessageResource> {


    public SendMessageRequestDeserializer() {
        this(null);
    }

    public SendMessageRequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MessageResource deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        MessageResource messageResource = new MessageResource();
        if(node.get("id")!=null) {
            messageResource.setId(node.get("id").asLong());
        }
        messageResource.setSender(node.get("sender").asLong());
        messageResource.setRecipient(node.get("recipient").asLong());
        messageResource.setType(node.get("content").get("type").asText());
        messageResource.setText(node.get("content").get("text").asText());

        //We are not sending the metadata, so it's not included here, a real world server would do this work in case it would be image/video content
        //Just for general testing Ill assume the endpoint the server is reading/writing will hit the same endpoint
        //In the future we should have to provide some validation if we want to prevent the user to add metadata to images/videos
        return messageResource;
    }

}
