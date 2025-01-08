package me.jinwo.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jinwo.springbootdeveloper.domain.Article;
import me.jinwo.springbootdeveloper.dto.AddArticleRequest;
import me.jinwo.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor        // final 이 붙거나 @NotNull 이 붙은 필드의 생성자 추가
@Service                        // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }
    /*
        @Service : 해당 클래스를 빈으로 서블릿 컨테이너에 등록해줌
        save() : JpaRepository 에서 지원하는 저장 메서드 save() 로,
            AddArticleRequest 클래스에 저장된 값들을 article 데이터베이스에 저장

        -> 글을 생성하는 서비스 계층에서의 코드 작성이 완료

        -> 컨트롤러 메서드 코드 작성하기
            URL에 매핑하기 위한 컨트롤러 메서드를 추가 -> 컨트롤러 메서드 구현 자체는 한적있는데,
            컨트롤러 메서드에는 URL 매핑 애너테이션인
            @PostMapping
            @GETMapping
            @PutMapping
            @DeleteMapping 등을 사용할 수 있습니다.
            이름에서 유추할 수 있듯 애너테이션들은 HTTP 메서드와 대응

            /api/articles 에 POST 요청이 들어오면 @PostMapping 을 이용해서 요청을 매핑한 뒤,
            블로그 글을 생성하는 BlogService 의 save() 메서드를 호출
            -> 생성된 블로그 글을 반환하는 작업을 하는 addArticle() 메서드를 작성할 예정

            service 와 동일한 라인에 controller 패키지 생성한 뒤에 BlogApiController.java 생성
     */
}