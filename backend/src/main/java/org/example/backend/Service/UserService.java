package org.example.backend.service;


import org.example.backend.entity.User;
import org.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }

    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }

    public User insertUser(User user) {
        userMapper.insertUser(user);
        return user;
    }

    public User updateUserById(User user) {
        userMapper.updateUserById(user);
        return userMapper.findUserById(user.getId());
    }

}
