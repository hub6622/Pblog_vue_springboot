package com.pblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer id;
    private String category;
    private String title;
    private User author;
    private String createTime;
    private Integer viewCounts;
    private Integer commentCounts;
    private String content;
    private Integer commentId;
}
