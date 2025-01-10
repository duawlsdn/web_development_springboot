package me.jinwo.springbootdeveloper.dto;

import lombok.Getter;
import me.jinwo.springbootdeveloper.domain.Article;

@Getter
public class ArticleListViewResponse {
    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
    /*
        다 작성하신분들 controller 패키지에
        BlogViewController 파일을 만들 예정
     */
}
