package com.pblog.service.impl;

import com.pblog.mapper.ArticleMapper;
import com.pblog.mapper.UserMapper;
import com.pblog.pojo.Article;
import com.pblog.pojo.ArticleComment;
import com.pblog.pojo.CommentReply;
import com.pblog.pojo.User;
import com.pblog.service.ArticleService;
import com.pblog.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    ArticleMapper articleMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    UserService userService;

    @Override
    public List<Article> findAll() {
        return articleMapper.findAll();
    }

    @Override
    public List<Article> findByCategory(String category) {
        return articleMapper.getArticleByCategory(category);
    }

    @Override
    public Article getById(Integer id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public List<ArticleComment> getCommentsByArticleId(Integer id) {
        List<ArticleComment> articleComment = articleMapper.getComments(id);
        for (ArticleComment ac : articleComment) {
            ac.setCommentReply(articleMapper.getReply(ac.getId()));
        }
        return articleComment;
    }

    @Override
    public void addComment(String name, String content, Integer articleId) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentAuthor(new User(userMapper.getUser(name).getId()));
        articleComment.setContent(content);

        articleMapper.addComment(articleComment);//返回的是受影响的行数
        Integer commentId = articleComment.getId();//返回的是插入后自增的id
        articleMapper.addArticleCommentReply(articleId, commentId, null);
    }

    @Override
    public void addCommentReply(String name, String content, Integer commentId) {
        CommentReply commentReply = new CommentReply();
        commentReply.setReplyAuthor(new User(userMapper.getUser(name).getId()));
        commentReply.setContent(content);
        articleMapper.addCommentReply(commentReply);//返回的是受影响的行数
        Integer replyId = commentReply.getId();//返回的是插入后自增的id
        articleMapper.addArticleCommentReply(null, commentId, replyId);
    }

    @Override
    public void addArticle(String name, Map<String, Object> article) {
        Article article1 = new Article();
        article1.setTitle((String) article.get("title"));
        article1.setContent((String) article.get("content"));
        article1.setCategory((String) article.get("category"));
        article1.setAuthor(new User(userMapper.getUser(name).getId()));
        System.out.println("article1++++" + article1);
        articleMapper.addArticle(article1);
    }

    @Override
    public void updateArticleCommentCounts(Integer id) {
        Integer articleId = id;
        try {
            List<Integer> l = articleMapper.findArticleIdByCommentId(articleId);
            articleId = l.get(0);
            System.out.println("articleId++++" + articleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        articleMapper.updateArticleCommentCounts(articleId);
    }

    @Override
    public List<String> findCategory() {
        return articleMapper.findAllCategory();
    }

    @Override
    public void updateArticleViewCounts(Integer articleId) {
        articleMapper.updateViewCounts(articleId);
    }

    @Override
    public List<Article> findArticleByViews() {
        return articleMapper.findArticleByViews();
    }

    @Override
    public List<Article> findByUser(Integer userId) {
        return articleMapper.findByUserName(userId);
    }

    @Override
    public void delArticle(Integer articleId) {
        articleMapper.delArtilce(articleId);
        List<Integer> commentIds = articleMapper.findCommentIdByArticleId(articleId);
        for (Integer id:commentIds) {
            userService.delComment(id);
        }
        articleMapper.delArticleCommentReplyByArticleId(articleId);
    }
}
