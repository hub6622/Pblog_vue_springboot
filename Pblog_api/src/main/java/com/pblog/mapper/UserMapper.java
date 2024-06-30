package com.pblog.mapper;


import com.pblog.pojo.ArticleComment;
import com.pblog.pojo.CommentReply;
import com.pblog.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into t_user(name,password) values (#{name},#{password})")
    boolean register(User user);

    User getUser(String name);

    @Select("select * from t_comments where author_id=#{userId}")
    List<ArticleComment> getComments(Integer userId);

    @Select("select * from t_comment_reply where author_id=#{userId}")
    List<CommentReply> getReply(Integer userId);

    @Delete("delete from t_comments where id=#{commentId}")
    void delCommentById(Integer commentId);
    @Delete("delete from t_comment_reply where id=#{replyId}")
    void delReplyById(Integer replyId);
    @Delete("delete from t_article_comment_reply where comment = #{commentId}")
    void delArticleCommentReplyByCommentId(Integer commentId);

    @Delete("delete from t_article_comment_reply where reply = #{replyId}")
    void delArticleCommentReplyByReplyId(Integer replyId);
    @Select("select reply from t_article_comment_reply where comment = #{commentId}")
    List<Integer> findReplyIdByCommentId(Integer commentId);

    @Select("select password from t_user where name=#{userName}")
    String getPwd(String userName);

    @Update("update t_user set password=#{password} where id=#{id}")
    void updateUserPwd(User user);
    @Update("update t_user set email=#{email} where id=#{id}")
    void updateUserEmail(User user);

    @Update("update t_user set avatar=#{avatarUrl} where id=#{userId}")
    void updateAvatar(String avatarUrl,Integer userId);
}
