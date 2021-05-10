package com.asapp.backend.challenge.controller;

import com.asapp.backend.challenge.exceptions.MessageTypeException;
import com.asapp.backend.challenge.exceptions.UserIdMissingException;
import com.asapp.backend.challenge.resources.GetMessageResourceRequestDTO;
import com.asapp.backend.challenge.resources.MessageResource;
import com.asapp.backend.challenge.resources.UserResource;
import com.asapp.backend.challenge.service.MessageService;
import com.asapp.backend.challenge.service.UserService;
import com.asapp.backend.challenge.utils.GetMessageResponseSerializer;
import com.asapp.backend.challenge.utils.JSONUtil;
import com.asapp.backend.challenge.utils.SendMessageRequestDeserializer;
import com.asapp.backend.challenge.utils.SendMessageResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Optional;

@Component
public class MessagesController {


    private static UserService userService;

    private static MessageService messageService;

    public static Route sendMessage = (Request req, Response rep) -> {
        MessageResource message = (MessageResource) JSONUtil.jsonToDataWithCustomDeserializer(req.body(), new SendMessageRequestDeserializer(), MessageResource.class);


        userService.getUser(message.getSender()).orElseThrow(() -> new UserIdMissingException());
        userService.getUser(message.getRecipient()).orElseThrow(() -> new UserIdMissingException());
        if(message.getType() == null || (!message.getType().equals("text") && !message.getType().equals("image") && !message.getType().equals("video"))){
            throw new MessageTypeException();
        }
        return JSONUtil.dataToJsonWithCustomSerializer(messageService.save(message), new SendMessageResponseSerializer(), MessageResource.class);
    };
    public static Route getMessages = (Request req, Response rep) -> {
        GetMessageResourceRequestDTO message = (GetMessageResourceRequestDTO) JSONUtil.jsonToData(req.body(), GetMessageResourceRequestDTO.class);

        List<MessageResource> messages = messageService.getMessagesByRecipient(message.getRecipient(), message.getStart(), message.getLimit());

        return JSONUtil.dataToJsonWithCustomSerializer(messages, new GetMessageResponseSerializer(), List.class);
    };

    @Autowired
    public MessagesController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }


}
