package com.gmail.netcracker.application.config;

import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.utilites.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

import static com.gmail.netcracker.application.utilites.ResultSetColumnValueExtractor.*;
import static com.gmail.netcracker.application.utilites.Utilities.parseDateToString;
import static com.gmail.netcracker.application.utilites.Utilities.parseDateToStringWithSeconds;

@Configuration
@ComponentScan("com.gmail.netcracker.application.*")
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
public class RootConfig  {

    private final Environment env;

    @Bean
    public EmailConstructor emailConstructor() {
        return new EmailConstructor();
    }

    @Autowired
    public RootConfig(Environment env) {
        this.env = env;
    }


    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() throws Exception {
        ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        Locale.setDefault(Locale.ENGLISH);
        resourceBundleMessageSource.setBasename("classpath:message");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return resourceBundleMessageSource;
    }


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        return new SchedulerFactoryBean();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(env.getProperty("postgres.driver"));
        driver.setUrl(env.getProperty("postgres.url"));
        driver.setUsername(env.getProperty("postgres.username"));
        driver.setPassword(env.getProperty("postgres.password"));
        return driver;
    }

    @Bean(initMethod = "migrate")
    @Autowired
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations(env.getProperty("flyway.migration.path"));
        flyway.setDataSource(dataSource);
        return flyway;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RowMapper<EventMessage> eventMessageRowMapper() {
        return (resultSet, i) -> {
            EventMessage eventMessage = new EventMessage();
            eventMessage.setFrom(getString(resultSet, "name"));
            eventMessage.setText(getString(resultSet, "text"));
            eventMessage.setTime(parseDateToStringWithSeconds(getTimestamp(resultSet, "date")));
            eventMessage.setSenderPhoto(getString(resultSet, "photo"));
            eventMessage.setSenderId(getLong(resultSet, "sender_id"));
            return eventMessage;
        };
    }

    @Bean
    public RowMapper<Chat> chatRowMapper() {
        return (resultSet, i) -> {
            Chat chat = new Chat();
            chat.setChatId(getLong(resultSet, "chat_id"));
            chat.setName(getString(resultSet, "name"));
            chat.setEventId(getLong(resultSet, "event_id"));
            chat.setState(getBoolean(resultSet, "is_with_creator"));
            return chat;
        };
    }

    @Bean
    public RowMapper<ChatId> notificationRowMapper() {
        return (resultSet, i) -> {
            ChatId chatId = new ChatId();
            chatId.setChatId(getLong(resultSet, "chat_id"));
            return chatId;
        };
    }

    @Bean
    public RowMapper<Notification> chatUserRowMapper() {
        return (resultSet, i) -> {
            Notification notification = new Notification();
            EventMessage eventMessage = new EventMessage();
            Event event = new Event();
            User user = new User();
            user.setId(getLong(resultSet, "user_id"));
            event.setEventId(getLong(resultSet, "event_id"));
            event.setName(getString(resultSet, "name"));
            event.setCreator(getLong(resultSet, "creator_id"));
            event.setPhoto(getString(resultSet, "photo"));
            eventMessage.setText(getString(resultSet, "text"));
            eventMessage.setTime(Utilities.parseDateToStringWithSeconds(getTimestamp(resultSet, "date")));
            eventMessage.setChatId(getLong(resultSet, "chat_id"));
            eventMessage.setSenderId(getLong(resultSet, "sender_id"));
            eventMessage.setFrom(getString(resultSet, "sender_name"));
            eventMessage.setSenderPhoto(getString(resultSet, "sender_photo"));
            notification.setCreatorEvent(getBoolean(resultSet, "is_with_creator"));
            notification.setChatId(getLong(resultSet, "chat_id"));
            notification.setEvent(event);
            notification.setUser(user);
            notification.setLastMessage(eventMessage);
            return notification;
        };
    }

    @Bean
    public RowMapper<User> userRowMapper() {
        return (resultSet, i) -> {
            User user = new User();
            user.setId(getLong(resultSet, "user_id"));
            user.setName(getString(resultSet, "name"));
            user.setSurname(getString(resultSet, "surname"));
            user.setEmail(getString(resultSet, "email"));
            user.setPassword(getString(resultSet, "password"));
            user.setRole(getString(resultSet, "role"));
            user.setPhone(getString(resultSet, "phone"));
            user.setBirthdayDate(parseDateToString(getDate(resultSet, "birthdate")));
            user.setPhoto(getString(resultSet, "photo"));
            user.setGender(getString(resultSet, "gender"));
            user.setNotificationPeriodicity(getString(resultSet, "notification_periodicity"));
            user.setNotificationStartDate(getString(resultSet, "notification_start_date"));
            user.setNotificationEndDate(getString(resultSet, "notification_end_date"));
            return user;
        };
    }

    @Bean
    public RowMapper<VerificationToken> verificationTokenRowMapper() {
        return (resultSet, i) -> {
            VerificationToken verificationToken = new VerificationToken();
            User user = new User();
            verificationToken.setId(getString(resultSet, "token_id"));
            user.setId(getLong(resultSet, "user_id"));
            user.setName(getString(resultSet, "name"));
            user.setSurname(getString(resultSet, "surname"));
            user.setEmail(getString(resultSet, "email"));
            user.setPassword(getString(resultSet, "password"));
            user.setRole(getString(resultSet, "role"));
            user.setPhone(getString(resultSet, "phone"));
            user.setBirthdayDate(parseDateToString(getDate(resultSet, "birthdate")));
            user.setGender(getString(resultSet, "gender"));
            verificationToken.setUser(user);
            return verificationToken;
        };
    }

    @Bean
    public RowMapper<Event> eventRowMapper() {
        return (rs, i) -> {
            Event event = new Event();
            event.setEventId(getLong(rs, "event_id"));
            event.setName(getString(rs, "name"));
            event.setDescription(getString(rs, "description"));
            event.setCreator(getLong(rs, "creator_id"));
            event.setDateStart(getString(rs, "start_date"));
            event.setDuration(getLong(rs, "duration"));
            event.setEndRepeat(getString(rs, "end_repeat"));
            event.setTypeId(getLong(rs, "type_id"));
            event.setDraft(getBoolean(rs, "is_draft"));
            event.setWidth(getDouble(rs, "latitude"));
            event.setLongitude(getDouble(rs, "longitude"));
            event.setEventPlaceName(getString(rs, "place_name"));
            event.setPeriodicity(getString(rs, "periodicity"));
            event.setPhoto(getString(rs, "photo"));
            event.setPriorityId(getLong(rs, "priority_id"));
            return event;
        };
    }

    @Bean
    public RowMapper<EventType> eventTypeRowMapper() {
        return (resultSet, i) -> {
            EventType eventType = new EventType();
            eventType.setTypeId(getLong(resultSet, "type_id"));
            eventType.setName(getString(resultSet, "name"));
            return eventType;
        };
    }

    @Bean
    public RowMapper<Folder> folderRowMapper() {
        return (resultSet, i) -> {
            Folder folder = new Folder();
            folder.setFolderId(resultSet.getLong("folder_id"));
            folder.setName(resultSet.getString("name"));
            folder.setCreator(resultSet.getLong("creator_id"));
            return folder;
        };
    }

    @Bean
    public RowMapper<Priority> priorityRowMapper() {
        return (resultSet, i) -> {
            Priority priority = new Priority();
            priority.setPriorityId(getLong(resultSet, "priority_id"));
            priority.setName(getString(resultSet, "name"));
            return priority;
        };
    }

    @Bean
    public RowMapper<Participant> participantRowMapper() {
        return (resultSet, i) -> {
            Participant participant = new Participant();
            participant.setEventId(getLong(resultSet, "event_id"));
            participant.setPriority(getLong(resultSet, "priority_id"));
            return participant;
        };
    }

    @Bean
    public RowMapper<Note> noteRowMapper() {
        return (resultSet, i) -> {
            Note note = new Note();
            note.setNoteId(getLong(resultSet, "note_id"));
            note.setName(getString(resultSet, "name"));
            note.setDescription(getString(resultSet, "description"));
            note.setCreator(getLong(resultSet, "creator_id"));
            note.setFolder(getLong(resultSet, "folder_id"));
            return note;
        };
    }

    @Bean
    RowMapper<Note> notesIntoFolderRowMapper() {
        return (resultSet, i) -> {
            Note note = new Note();
            note.setNoteId(resultSet.getLong("note_id"));
            note.setName(resultSet.getString("name"));
            note.setFolder(resultSet.getLong("folder_id"));
            return note;
        };
    }

    @Bean
    public RowMapper<Friend> friendshipRowMapper() {
        return (resultSet, i) -> {
            Friend friendship = new Friend();
            friendship.setRecipient(getLong(resultSet, "recipient_id"));
            friendship.setSender(getLong(resultSet, "sender_id"));
            friendship.setIsAccepted(getBoolean(resultSet, "is_accepted"));
            return friendship;
        };
    }

    @Bean
    public RowMapper<Item> itemRowMapper() {
        return (resultSet, i) -> {
            Item item = new Item();
            item.setItemId(getLong(resultSet, "item_id"));
            item.setPersonId(getLong(resultSet, "user_id"));
            item.setBooker(getLong(resultSet, "booker_id"));
            item.setName(getString(resultSet, "name"));
            item.setDescription(getString(resultSet, "description"));
            item.setLink(getString(resultSet, "link"));
            item.setDueDate(getString(resultSet, "due_date"));
            item.setPriority(getLong(resultSet, "priority_id"));
            item.setRoot(getLong(resultSet, "root_id"));
            item.setEvent(getLong(resultSet, "event_id"));
            item.setImage(getString(resultSet, "image"));
            item.setIsLiked(getBoolean(resultSet, "is_liked"));
            return item;
        };
    }

    @Bean
    public RowMapper<Tag> tagRowMapper() {
        return (resultSet, i) -> {
            Tag tag = new Tag();
            tag.setTagId(getLong(resultSet, "tag_id"));
            tag.setName(getString(resultSet, "name"));
            return tag;
        };
    }

    @Bean
    public RowMapper<Like> likeRowMapper() {
        return (resultSet, i) -> {
            Like like = new Like();
            like.setItemId(getLong(resultSet, "item_id"));
            like.setUserId(getLong(resultSet, "user_id"));
            return like;
        };

    }

    @Bean
    public Gson gsonTimeline() {
        return new GsonBuilder()
                .registerTypeAdapter(Event.class, new TimelineSerializer())
                .create();
    }

    @Bean
    public Gson gsonEvents() {
        return new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventSerializer())
                .create();
    }

}