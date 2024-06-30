package com.pblog.controller;

import com.pblog.pojo.Article;
import com.pblog.pojo.Result;
import com.pblog.service.ArticleService;
import com.pblog.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    ArticleService articleService;
    @Resource
    UserService userService;
    @GetMapping("/list")
    public Result getAll(){
        System.out.println("get all");
        return new Result(200, "success", articleService.findAll());
    }
    @GetMapping("/getArticleById/{id}")
    public Result getArticleById(@PathVariable Integer id){
        System.out.println("getArticleById");
        return new Result(200, "success", articleService.getById(id));
    }
    @GetMapping("/getArticleByCategory/{category}")
    public Result getArticleByCategory(@PathVariable String category){
        System.out.println("getArticleByCategory"+articleService.findByCategory(category));
        return new Result(200, "success", articleService.findByCategory(category));
    }
    @GetMapping("/getCategory")
    public Result getCategory(){
        System.out.println("getArticleById");
        return new Result(200, "success", articleService.findCategory());
    }
    @GetMapping("/commentsByArticleId/{id}")
    public Result getCommentsByArticleId(@PathVariable Integer id){
        System.out.println("getCommentsByArticleId");
        return new Result(200, "success", articleService.getCommentsByArticleId(id));
    }
    @PostMapping("/addArticleComment")
    public Result addArticleComment(@RequestBody Map<String, Object> comment, @RequestHeader(name="Authorization") String token){
        System.out.println("addArticleComment");
        String content=comment.get("content").toString();
        Integer articleId=Integer.parseInt(comment.get("articleId").toString());
        articleService.updateArticleCommentCounts(articleId);
        String name = userService.getUserName(token);
        articleService.addComment(name, content, articleId);
        return new Result(200, "success", null);
    }
    @PostMapping("/addArticleCommentReply")
    public Result addArticleCommentReply(@RequestBody Map<String, Object> comment, @RequestHeader(name="Authorization") String token){
        System.out.println(comment.get("content")+"==="+comment.get("commentId"));
        String content=comment.get("content").toString();
        Integer commentId=Integer.parseInt(comment.get("commentId").toString());
        articleService.updateArticleCommentCounts(commentId);
        String name = userService.getUserName(token);
        articleService.addCommentReply(name, content, commentId);
        return new Result(200, "success", null);
    }
    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody Map<String, Object> article, @RequestHeader(name="Authorization") String token){
        System.out.println(article.get("content")+"==="+article.get("title")+"==="+article.get("category"));
        String name = userService.getUserName(token);
        articleService.addArticle(name, article);
        return new Result(200, "success", null);
    }
    @PostMapping("/updateArticleViewCounts/{articleId}")
    public Result updateArticleViewCounts(@PathVariable Integer articleId){
        System.out.println("updateArticle");
        articleService.updateArticleViewCounts(articleId);
        return new Result(200, "success", null);
    }
    @GetMapping("/hotArticle")
    public Result hotArticle(){
        System.out.println("hotArticle");
        return new Result(200, "success", articleService.findArticleByViews());
    }
    @GetMapping("/getArticleByUser")
    public Result getArticleByUser(@RequestHeader(name="Authorization") String token){
        System.out.println("getArticleByUser");
        Integer userId = userService.getUserId(token);
        return new Result(200, "success", articleService.findByUser(userId));
    }
    @PostMapping("/deleteArticle")
    public Result deleteArticle(@RequestBody Map<String, Object> article){
        Integer articleId = Integer.parseInt(article.get("articleId").toString());
        System.out.println("deleteArticle"+articleId);
        articleService.delArticle(articleId);
        return new Result(200, "success", null);
    }
}
