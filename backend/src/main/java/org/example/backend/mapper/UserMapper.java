package org.example.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.User;

import java.util.List;
@Mapper
public interface UserMapper {

    List<User> findAllUsers();

    User findUserById(int id);

    User findUserByName(String name);

    void insertUser(User user);

    void updateUserById(User user);
}
