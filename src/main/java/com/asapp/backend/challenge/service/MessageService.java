package com.asapp.backend.challenge.service;

import com.asapp.backend.challenge.repository.MessageRepository;
import com.asapp.backend.challenge.repository.MetadataRepository;
import com.asapp.backend.challenge.resources.MessageResource;
import com.asapp.backend.challenge.resources.MetadataResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MetadataRepository metadataRepository;

    public MessageService(MessageRepository messageRepository, MetadataRepository metadataRepository) {
        this.messageRepository = messageRepository;
        this.metadataRepository = metadataRepository;
    }

    public MessageResource save(MessageResource messageResource) {
        return messageRepository.save(messageResource);
    }


    public List<MessageResource> getMessagesByRecipient(Long recipientId, Long start, Long limit) {
        return messageRepository.findByRecipient(recipientId, start, limit);
    }

    @Transactional
    public void updateMetadata() {
        messageRepository.findAllWithoutCrawlerFlag()
                .filter(m -> m.isMetadataSupported()).forEach(m -> {
            m.setHasCrawlerPassed(true);
            MetadataResource metadataResource = new MetadataResource();
            metadataResource.setType("XML");
            metadataResource.setMetadata("<xml>hola</xml>");
            metadataResource.setMessageResource(m);
            m.addMetadataResource(metadataResource);
            metadataRepository.save(metadataResource);
        });
    }

}
