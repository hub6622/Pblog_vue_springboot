//package com.pblog;
//
//import com.auth0.jwt.interfaces.Claim;
//import com.pblog.mapper.ArticleMapper;
//import com.pblog.mapper.UserMapper;
//import com.pblog.pojo.User;
//import com.pblog.service.ArticleService;
//import com.pblog.service.UserService;
//import com.pblog.utils.EncodeUtil;
//import com.pblog.utils.JwtToken;
//import jakarta.annotation.Resource;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import java.util.Map;
//
//@SpringBootTest
//class PblogApiApplicationTests {
//    @Resource
//    private ArticleService articleService;
//    @Resource
//    private UserMapper userMapper;
//    @Resource
//    private UserService userService;
//    @Resource
//    private ArticleMapper articleMapper;
//    EncodeUtil encodeUtil = new EncodeUtil();
//    @Test
//    void contextLoads() {
//        User user = new User();
//        user.setPassword("222");
//        user.setName("ppg");
//        System.out.println(userService.login(user));
//    }
//    @Test
//    void test() {
//        System.out.println(encodeUtil.Md5Encode("11111111"));
//    }
//    @Test
//    void test2() {
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIsImV4cCI6MTcxOTIxODgzNiwidXNlcm5hbWUiOiJhZG1pbiJ9.NmzbQoBkpFf_69O9eV8GAz2P2zytvxtqCBqY4tXs1bQ";
//        JwtToken jwtToken=new JwtToken();
//        Map<String, Claim> map=jwtToken.verify(token);
//        System.out.println(map);
//        String name=map.get("username").toString();
//    }
//
//}
