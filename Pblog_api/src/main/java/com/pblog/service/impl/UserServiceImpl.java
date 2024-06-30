package com.pblog.service.impl;


import com.auth0.jwt.interfaces.Claim;
import com.pblog.mapper.ArticleMapper;
import com.pblog.mapper.UserMapper;
import com.pblog.pojo.ArticleComment;
import com.pblog.pojo.CommentReply;
import com.pblog.pojo.User;
import com.pblog.service.UserService;
import com.pblog.utils.EncodeUtil;
import com.pblog.utils.JwtToken;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    ArticleMapper articleMapper;
    EncodeUtil encodeUtil = new EncodeUtil();

    @Override
    public boolean register(User user) {
        user.setPassword(encodeUtil.Md5Encode(user.getPassword()));
        userMapper.register(user);
        return false;
    }

    @Override
    public boolean login(User user) {
        String Pwd = userMapper.getPwd(user.getName());
        try {
            if(Pwd.equals(encodeUtil.Md5Encode(user.getPassword()))) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getInfo(String name) {
        return userMapper.getUser(name);
    }

    @Override
    public String getUserName(String token) {
        JwtToken jwtToken = new JwtToken();
        Map<String, Claim> map = jwtToken.verify(token);
        String name = map.get("username").asString();
        return name;
    }

    @Override
    public Integer getUserId(String token) {
        JwtToken jwtToken = new JwtToken();
        Map<String, Claim> map = jwtToken.verify(token);
        return map.get("id").asInt();
    }

    @Override
    public List<ArticleComment> getUserComment(Integer userId) {
        List<ArticleComment> articleComment = userMapper.getComments(userId);
        List<CommentReply> commentReply = userMapper.getReply(userId);
        ArticleComment articleComment1 = articleComment.get(0);
        articleComment1.setCommentReply(commentReply);
        articleComment.set(0, articleComment1);
        return articleComment;
    }

    @Override
    public void delComment(Integer commentId) {
        userMapper.delCommentById(commentId);
        List<Integer> replyIds = userMapper.findReplyIdByCommentId(commentId);
        System.out.println("replyIds" + replyIds);
        for (Integer id : replyIds) {
            userMapper.delReplyById(id);
        }
        userMapper.delArticleCommentReplyByCommentId(commentId);
    }

    @Override
    public void delReply(Integer replyId) {
        userMapper.delReplyById(replyId);
        userMapper.delArticleCommentReplyByReplyId(replyId);
    }

    @Override
    public Boolean confirmPwd(String pwd, String token) {
        String realPwd = userMapper.getPwd(getUserName(token));
        if (realPwd.equals(encodeUtil.Md5Encode(pwd))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String updateUserInfo(Map<String, Object> userInfo) {
        System.out.println(userInfo);
        User user = new User();
        user.setId(Integer.parseInt(userInfo.get("id").toString()));
        if (userInfo.get("password") == null && userInfo.get("email") != null) {
            System.out.println(222);
            user.setEmail(userInfo.get("email").toString());
            userMapper.updateUserEmail(user);
            return "Email";
        } else if (userInfo.get("password") != null && userInfo.get("email") == null) {
            System.out.println(22);
            user.setPassword(encodeUtil.Md5Encode(userInfo.get("password").toString()));
            userMapper.updateUserPwd(user);
            return "Pwd";
        } else if (userInfo.get("password") != null && userInfo.get("email") != null && !userInfo.get("password").toString().isEmpty()) {
            System.out.println(2);
            user.setPassword(encodeUtil.Md5Encode(userInfo.get("password").toString()));
            user.setEmail(userInfo.get("email").toString());
            userMapper.updateUserEmail(user);
            userMapper.updateUserPwd(user);
            return "Pwd";
        }
        return "aac";
    }

    @Override
    public void updateAvatar(String avatarUrl,Integer userId) {
        userMapper.updateAvatar(avatarUrl,userId);
    }
}
