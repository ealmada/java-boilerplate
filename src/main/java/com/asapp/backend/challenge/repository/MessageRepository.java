package com.asapp.backend.challenge.repository;

import com.asapp.backend.challenge.resources.MessageResource;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface MessageRepository extends JpaRepository<MessageResource, Long> {

    List<MessageResource> findBySender(Long userId);

    @Query(value = "SELECT * FROM messages m WHERE m.recipient LIKE ?1 ORDER BY m.id limit ?3 offset ?2", nativeQuery = true)
    List<MessageResource> findByRecipient(Long recipientId, Long start, Long limit);

    @Query(value = "SELECT m FROM MessageResource m WHERE m.hasCrawlerPassed = false or m.hasCrawlerPassed is null")
    Stream<MessageResource> findAllWithoutCrawlerFlag();

}
