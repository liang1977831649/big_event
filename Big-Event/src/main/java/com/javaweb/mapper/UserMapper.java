package com.javaweb.mapper;

import com.javaweb.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void registerUser(User user);
    User findUserById(Integer id);
    User findUserByUsername(String username);
    void updateUser(User user);
    void updateUserPic(String picURL,Integer id );
    void updateUserPwd(String newPwd,Integer id);
}
