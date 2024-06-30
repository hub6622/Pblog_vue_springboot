package com.pblog.service;


import com.pblog.pojo.ArticleComment;
import com.pblog.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean register(User user);

    boolean login(User user);
    User getInfo(String name);
    String getUserName(String token);

    Integer getUserId(String token);

    List<ArticleComment> getUserComment(Integer userId);

    void delComment(Integer commentId);
    void delReply(Integer replyId);

    Boolean confirmPwd(String pwd, String token);

    String updateUserInfo(Map<String, Object> userInfo);

    void updateAvatar(String avatarUrl,Integer userId);
}
