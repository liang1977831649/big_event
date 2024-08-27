package com.javaweb.interceptor;

import com.javaweb.utils.JwtUtil;
import com.javaweb.utils.ThreadLocalUtil;
import io.micrometer.observation.annotation.Observed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        //检验
        try {
            Map<String, Object> map = JwtUtil.parseToken(authorization);

            //获取redis里面的token
            //因为有可能没有这个token_id这类数据，因此需要放在try里
            String token = redisTemplate.opsForValue().get("token_"+map.get("id"));

            //对比，如果新旧不一样，那么久直接返回false
            if(!authorization.equals(token)){
                return false;
            }

            //存放在ThreadLocal里面
            ThreadLocalUtil.set(map);
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
