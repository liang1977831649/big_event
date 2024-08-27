package com.javaweb.service;

import com.javaweb.entity.User;

public interface UserService {
    Boolean registerUser(User user);
    User findUserById(Integer id);

    String login(User user);

    User findUserByUsername(String username);
    User updateUserById(User user);
    void updateUserPicById(String picURL);
    String updateUserPwd(String oldPwd,String rePwd, String newPwd);
}
