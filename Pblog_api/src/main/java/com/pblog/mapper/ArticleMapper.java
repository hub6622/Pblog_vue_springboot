package com.pblog.mapper;

import com.pblog.pojo.Article;
import com.pblog.pojo.ArticleComment;
import com.pblog.pojo.CommentReply;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {

    List<Article> findAll();


    List<Article> getArticleByCategory(String category);

    Article getArticleById(Integer id);

    List<ArticleComment> getComments(Integer id);

    List<CommentReply> getReply(Integer id);

    Integer addComment(ArticleComment articleComment);

    Integer addCommentReply(CommentReply commentReply);

    @Insert("insert into t_article_comment_reply(article,comment,reply) values (#{articleId},#{commentId},#{replyId})")
    void addArticleCommentReply(Integer articleId, Integer commentId, Integer replyId);

    void addArticle(Article article);

    @Update("update t_article set comment_counts = comment_counts + 1 where id = #{articleId}")
    void updateArticleCommentCounts(Integer articleId);

    @Select("select article from t_article_comment_reply where comment = #{commentId}")
    List<Integer> findArticleIdByCommentId(Integer commentId);
    @Select("select distinct comment from t_article_comment_reply where article = #{articleId}")
    List<Integer> findCommentIdByArticleId(Integer articleId);
    @Select("select distinct category from t_article")
    List<String> findAllCategory();

    @Update("update t_article set view_counts = view_counts + 1 where id = #{articleId}")
    void updateViewCounts(Integer articleId);

    @Select("select * from t_article order by view_counts desc limit 0,5")
    List<Article> findArticleByViews();

    List<Article> findByUserName(Integer userId);

    @Delete("delete from t_article where id = #{articleId}")
    void delArtilce(Integer articleId);

    @Delete("delete from t_article_comment_reply where article = #{articleId}")
    void delArticleCommentReplyByArticleId(Integer articleId);


}
