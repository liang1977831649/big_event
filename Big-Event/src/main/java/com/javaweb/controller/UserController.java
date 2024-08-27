package com.javaweb.controller;


import com.javaweb.entity.Result;
import com.javaweb.entity.User;
import com.javaweb.service.UserService;
import com.javaweb.utils.JwtUtil;
import com.javaweb.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@Validated//注解用于校验
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    //正则表达式用于匹配Pattern、regexp
    public Result registerUser(@Pattern(regexp = "\\S{6,10}") String password,@Pattern(regexp = "\\S{4,16}")String username){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Boolean b = userService.registerUser(user);
        return  b?Result.success():Result.error("该用户已存在，注册失败！");
    }

    @RequestMapping("/findUserById")
    public Result findUserById(Integer id){
        System.out.println("id="+id);
        User user = userService.findUserById(id);
        return Result.success(user);
    }

    @PostMapping(value = "/login")
    public Result login(User user){
        String info = userService.login(user);
        if("用户名错误".equals(info)||"密码错误".equals(info)){
            return Result.error(info);
        }
//        将令牌存入搭配reds中

        return Result.success(info);
    }

    @GetMapping(value = "/userInfo")
    public Result userInfo(){
        //从token里面获取对象的username
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        System.out.println(map);
        HashMap<String ,Object> map = ThreadLocalUtil.get();
        String username =(String) map.get("username");//获取到对应的username的value值
        User user = userService.findUserByUsername(username);
//        System.out.printf("user="+user);
        return Result.success(user);
    }
    @PutMapping(value = "/update")
    public Result updateInfo(@RequestBody @Validated User user){
        User updateUser = userService.updateUserById(user);
        return Result.success(updateUser);
    }
    @PatchMapping(value = "/updateAvatar")
    public Result updatePic(@RequestParam @URL String avatarUrl){
//        System.out.println(picURL);
        System.out.println("avatarUrl="+avatarUrl);
        userService.updateUserPicById(avatarUrl);
        return Result.success();
    }

    @PatchMapping(value = "/updatePwd")
    public Result updatePwd(@RequestBody Map<String ,String> map){
        String newPwd = map.get("new_pwd");
        String oldPwd = map.get("old_pwd");
        String rePwd = map.get("re_pwd");
        //判断是否都为空
//        System.out.println("newPwd="+newPwd+"|oldPwd="+oldPwd+"|rePwd="+rePwd);
        if(newPwd==null|| newPwd.isEmpty()||oldPwd==null||oldPwd.isEmpty()||rePwd==null||rePwd.isEmpty()){
            return Result.error("请完善密码信息");
        }
        String result = userService.updateUserPwd(oldPwd, rePwd, newPwd);
        if(result.equals("oldError")){
            return Result.error("原密码错误");
        }else if(result.equals("reNotNew")){
            return Result.error("两次密码不一致");
        }else{
            //将得到的新密码和账号获取到新的token
            return Result.success("修改成功");
        }

    }
}
