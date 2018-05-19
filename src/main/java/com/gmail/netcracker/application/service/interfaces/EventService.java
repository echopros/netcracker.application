package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.*;

import java.util.List;
import java.util.Map;

public interface EventService {

    void update(Event event);

    void delete(Long eventId);

    void insertEvent(Event event);

    Event getEvent(Long eventId);

    List<Event> eventList();

    List<Event> findPublicEvents();

    List<Event> findPrivateEvents(Long userId);

    List<Event> findFriendsEvents(Long userId);

    List<Event> findDrafts(Long userId);

    List<EventType> getAllEventTypes();

    void setPersonId(Event event);

    List<Event> getAllMyEvents();

    void participate(Long userId, Long eventId);

    int countParticipants(Long eventId);

    List<User> getParticipants(Long eventId);

    List<Priority> getAllPriorities();

    boolean isParticipated(Long id, Long eventId);

    void unsubscribe(Long id, Long eventId);

    List<Event> findCreatedFriendsEvents(Long id);

    List<Event> findCreatedPublicEvents(Long id);

    int getMaxId();

    void setPriority(Long priority, Long eventId, Long userId);

    boolean allowAccess(Long personId, Long eventId);

    boolean isCreator(Long personId, Long eventId);

    List<User> getUsersToInvite(Long id, Long eventId);

    List<User> subtraction(List<User> minuend, List<User> subtrahend);

    List<User> getFriendsToInvite(Long id, Long eventId);

    void transferNoteToEvent(Long noteId,Long userId, Event event);

    List<Event> myEventsWithPriority();

    Event getMyEventWithPriority(Long eventId);

    Participant getParticipation(Long eventId);

    void convertDraftToEvent(Long eventId);

    Event copyEvent(Event toCopy);
}
