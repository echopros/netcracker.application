package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventMessageDao;
import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.utilites.Utilites;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class EventMessageDaoImp extends ModelDao implements EventMessageDao {

    @Value("${sql.message.pkColumnName}")
    private String PK_COLUMN_NAME;

    @Value("${sql.message.add}")
    private String SQL_INSERT;


    protected EventMessageDaoImp(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    @Transactional
    public void insertMessage(Event event, EventMessage message, User user, Chat chat) {
        event.setEventId(
                (insertEntity(
                        SQL_INSERT,
                        PK_COLUMN_NAME,
                        message.getText(),
                        Utilites.parseTimeWithSeconds(message.getTime()),
                        user.getId(),
                        chat.getChatId())));
    }
}