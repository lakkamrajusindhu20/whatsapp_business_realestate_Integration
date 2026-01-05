package com.whatsapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsapp.entity.ConversationMessage;
import com.whatsapp.entity.Lead;
@Repository
public interface ConversationMessageRepository
        extends JpaRepository<ConversationMessage, Long> {

    List<ConversationMessage> findByLeadOrderByTimestampAsc(Lead lead);
}
