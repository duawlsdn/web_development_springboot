package me.jinwo.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.jinwo.springbootdeveloper.domain.Article;
import me.jinwo.springbootdeveloper.dto.AddArticleRequest;
import me.jinwo.springbootdeveloper.dto.UpdateArticleRequest;
import me.jinwo.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Article> findAll(){
        return blogRepository.findAll();
    }
    /*
        JPA 지원 메서드인 findALL()을 호출해  article 테이블에 저장돼 있는 모든 데이터를 조회 -> Test 에서 이미 해봄

        응답을 위한 DTO 생성 -> dto 패키지에 -> ArticleResponse.java 생성

        이제 BlogApiController.java 로 이동
     */

    // id 로 특정 글 조회
    public Article findById(long id){                   // 이 경우 결과값은 하나밖에 없기 때문에 리턴 타입이 Article 이어야만 합니다. -> id 가 PK라서
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id)); // 람다식
    }
    // BlogApiController.java 이동

    // 삭제 메서드 정의
    public void delete(long id){
        blogRepository.deleteById(id);
    }

    // 컨트롤러로 가면 됩니다. /api/articles/{id}

    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found : " + id));

        // domain -> Article 에서 정의한 update() 를 사용
        article.update(request.getTitle(), request.getContent());
        return article;
    }
    /*
        트랜잭션 - 데이터베이스에서 데이터를 바꾸기 위한 작업 단위를 의미함.
            예를 들어 계좌 이체를 할 때 이런 과정을 거친다고 가정합니다.
                1) A 계좌에서 출금
                2) B 계좌에 입금
             그런데 1) 은 성공했는데, 도중 2) 가 실패하면 어떻게 되나요? 고객 입장에서는 출금은 됐는데,
             입금이 안된 상황이 발생
             이런 상황이 발생하지 않으려면 출금과 입금을 하나의 작업 단위로 묶어서,
             즉 트랜잭션이라는 단위로 묶어서 두 작업을 한 단위로 실행하면 됩니다.
             만약에 중간에 실패한다면 트랜잭션의 처음 상태로 모두 되돌리면 됩니다.(ROLLBACK)
     */
}