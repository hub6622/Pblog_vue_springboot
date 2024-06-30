package com.pblog.service;

import com.pblog.pojo.Article;
import com.pblog.pojo.ArticleComment;
import java.util.List;
import java.util.Map;

public interface ArticleService {

    List<Article> findAll();

    List<Article> findByCategory(String category);

    Article getById(Integer id);

    List<ArticleComment> getCommentsByArticleId(Integer id);

    void addComment(String name, String content, Integer articleId);

    void addCommentReply(String name, String content, Integer articleId);

    void addArticle(String name, Map<String, Object> article);

    void updateArticleCommentCounts(Integer articleId);

    List<String> findCategory();

    void updateArticleViewCounts(Integer articleId);

    List<Article> findArticleByViews();

    List<Article> findByUser(Integer userId);

    void delArticle(Integer articleId);
}
