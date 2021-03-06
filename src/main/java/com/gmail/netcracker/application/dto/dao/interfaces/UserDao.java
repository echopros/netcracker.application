package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.User;

public interface UserDao {

    void saveUser(User user);

    User findUserByEmail(String email);

    User findUserById(Long id);

    void changePassword(String password, String email);

    void updateUser(User user);

    void updateNotificationsSchedule(User user);

    void disableNotifications(Long userId);

}
