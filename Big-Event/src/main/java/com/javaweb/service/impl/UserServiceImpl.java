package com.javaweb.service.impl;

import com.auth0.jwt.JWT;
import com.javaweb.entity.User;
import com.javaweb.mapper.UserMapper;
import com.javaweb.service.UserService;
import com.javaweb.utils.JwtUtil;
import com.javaweb.utils.Md5Util;
import com.javaweb.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public Boolean registerUser(User user) {
        //判断是否存在这个用户
        User userById = userMapper.findUserByUsername(user.getUsername());
        if(userById!=null){
            return false;
        }
        //不存在这个用户就直接注册
        String md5String = Md5Util.getMD5String(user.getPassword());
        user.setPassword(md5String);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.registerUser(user);
        System.out.println("注册用户成功");
        return true;
    }

    @Override
    public User findUserById(Integer id) {
        System.out.println("查询成功");
        return  userMapper.findUserById(id);
    }

    @Override
    public String login(User user) {
        //先查询这个用户是否存在
        User userByUsername = userMapper.findUserByUsername(user.getUsername());
        if(userByUsername==null){
            return "用户名错误";
        }
        //参数的user，加密一下密码，然后对比是否正确
        user.setPassword(Md5Util.getMD5String(user.getPassword()));
        if(userByUsername.getPassword().equals(user.getPassword())){
            //登陆成功,返回一个Token到控制器
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id",userByUsername.getId());
            hashMap.put("username",userByUsername.getUsername());
            //获取并返回Token
            String token = JwtUtil.genToken(hashMap);
            //存放在redis中
            redisTemplate.opsForValue().set("token_"+userByUsername.getId(),token,1,TimeUnit.HOURS);
            return token;
        }
        return "密码错误";
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public User updateUserById(User user) {
        //先看看这个Id是否存在
        userMapper.updateUser(user);
        return userMapper.findUserById(user.getId());
    }

    @Override
    public void updateUserPicById(String picURL) {
        //从threadLocal中获取用户的id
        Map<String ,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateUserPic(picURL,id);
    }

    @Override
    public String updateUserPwd(String oldPwd,String rePwd,String newPwd) {
        //拿到Id
        Map<String,Object> map =ThreadLocalUtil.get();
        Integer id =(Integer) map.get("id");//拿到id
        String username = (String)map.get("username");
        //获取原来的User对象
        User userById = userMapper.findUserById(id);
        //判断输入的密码和原密码是否相同
        //如果不相同
        if(!userById.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return "oldError";
        }
        //判断新密码和重复密码是否相同
        if(!rePwd.equals(newPwd)){
            return "reNotNew";
        }

        //修改密码
        userMapper.updateUserPwd(Md5Util.getMD5String(newPwd),id);

        //删除掉原来的token
        redisTemplate.opsForValue().getOperations().delete("token_"+id);
        //返回
        return "success";
    }
}
