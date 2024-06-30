package com.pblog.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.pblog.utils.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JwtToken jwtToken = new JwtToken();
        String token = request.getHeader("Authorization");
        try{
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String redisToken = ops.get(token);
            if(redisToken == null){
                throw new RuntimeException();
            }
            Map<String, Claim> claims = jwtToken.verify(token);
            return true;
        }catch (Exception e){
            System.out.println("触发了401");
            response.setStatus(401);
            return false;
        }

    }
}
