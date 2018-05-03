package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FriendDao;
import com.gmail.netcracker.application.dto.model.Friend;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private Friend friendship;

    @Override
    public List<User> getAllFriends(Long id) {
        return friendDao.friendList(id);
    }

    @Override
    public List<User> searchFriends(Long id, String search) {
        String[] input = search.split(" ");
        return input.length > 1 ? friendDao.getFriendsByNameAndSurname(id, input[0].toLowerCase(), input[1].toLowerCase()) : friendDao.getFriendsByNameOrSurname(id, input[0].toLowerCase());
    }


    @Override
    public void addFriend(Long personId, Long friendId) {
        friendship = friendDao.getFriendshipById(personId, friendId);
        if (friendship == null) {
            friendDao.addFriend(personId, friendId);
        }
    }

    @Override
    public List<User> getOutgoingRequests(Long id) {
        return friendDao.getOutgoingRequests(id);
    }

    @Override
    public void cancelRequest(Long id, Long friendId) {
        friendDao.cancelRequest(id, friendId);
    }

    @Override
    public List<User> getIncomingRequests(Long id) {
        return friendDao.getIncomingRequests(id);
    }

    @Override
    public void acceptRequest(Long id, Long friendId) {
        friendDao.acceptRequest(id, friendId);
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        friendDao.deleteFriend(id, friendId);
    }

    @Override
    public List<User> searchUsers(Long id, String search) {
        String[] input = search.split(" ");
        return input.length > 1 ? friendDao.searchUsersByNameAndSurname(id, input[0].toLowerCase(), input[1].toLowerCase()) : friendDao.searchUsersByNameOrSurname(id, input[0].toLowerCase());
    }

    @Override
    public List<User> intersect(List<User> friendList, List<User> foundUsers) {
        for (User item : friendList) {
            if (foundUsers.contains(item)) {
                foundUsers.remove(item);
            }
        }
        return foundUsers;
    }
}
