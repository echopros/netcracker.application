package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ChatDao;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDao chatDao;

    private Logger logger = Logger.getLogger(ChatServiceImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    public Chat getChatByEventId(Event event, Boolean state) {
        return chatDao.getChatByEventId(event, state);
    }

    @Override
    @Transactional(readOnly = true)
    public Chat getChatByChatId(Long chatId) {
        return chatDao.getChatByChatId(chatId);
    }

    @Override
    @Transactional
    public void createChatForEvent(Event event, Boolean creator) {
        chatDao.createChat(event, creator);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventMessage> getMessagesForEvent(Long eventId, Long chatId, Boolean state) {
        return chatDao.getMessages(eventId, chatId, state);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventMessage> getMessagesForEvent(Long eventId, Long chatId, Boolean state, Integer limit, Integer offset) {
        return chatDao.getMessages(eventId, chatId, state, limit, offset);
    }

    @Override
    @Transactional
    public void deleteEventChat(Event event) {
        chatDao.deleteChat(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatId> allUserChatId(Long userId) {
        return chatDao.allUserChatId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> allUserChats(Long userId) {
        logger.info(chatDao.allUserChats(userId).toString());
        return chatDao.allUserChats(userId);
    }
}
