package me.jinwo.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.jinwo.springbootdeveloper.domain.Article;
import me.jinwo.springbootdeveloper.dto.AddArticleRequest;
import me.jinwo.springbootdeveloper.dto.ArticleResponse;
import me.jinwo.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    // HTTP 메서드가 POST 일 때 전달 받은 URL 과 동일하면 지금 정의하는 메서드와 매핑
    @PostMapping("/api/articles")
    // @RequestBody 로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }
    /*
        1. @RestController : 클래스에 붙이면 HTTP 응답으로 객체 데이터를 "JSON" 형식으로 반환
        2. @PostMapping() : HTTP 메서드가 POST 일 때 요청 받은 URL 과 동일한 메서드와 매핑
            지금의 경우 api/articles 는 addArticle() 메서드와 매핑.
        3. @RequestBody : HTTP 요청을 할 때, 응답에 해당하는 값을 @RequestBody 애너테이션이 붙은
            대상 객체은 AddArticleRequest 에 매핑
        4. ResponseEntity.status().body() 는 응답 코드 201, 즉 Created 를 응답하고,
            테이블에 저장된 객체를 반환

        200 OK : 요청이 성공적으로 수행되었음
        201 Created : 요청이 성공적으로 수행되었고, 새로운 리소스가 생성되었음
        400 Bad Request : 요청 값이 잘못되어 요청에 실패했음
        403 Forbidden : 권한이 없어 요청에 실패했음
        404 Not Found : 요청 값으로 찾은 리소스가 없어 요청에 실패했음
        500 Internal Server Error : 서버 상에 문제가 있어 요청에 실패했음


        API 가 잘 작동하는지 하나 테스트를 해볼 예정

            H2 콘솔 활성화

            application.yml 로 이동

            Postman 실행
            HTTP 메서드 : POST
            URL : http://localhost:8080/api/articles
            BODY : rew -> JSON
            그리고 요청 창에
            {
                "title" : "제목",
                "content" : "내용"
            }
            으로 작성 후에 Send 버튼 눌러 요청

            결과값이 Body 에 pretty 모드로 결과를 보여줌
            -> 여기까지 성공했다면 스프링 부트 서버에 저장된 것을 의미

            여기까지가 HTTP 메서드 POST 로 서버에 요청을 한 후에 값을 저장하는 과정에 해당.

            이제 크롬 켜기 -> 주소창에
            localhost:8080/h2-console

            JDBC URL : jdbc:h2:mem:testdb 로 변경

            SQL satatement 입력창에(SQL 편집기 모양인데에)
            select * from article
            안된 분 혹시 ARTICLE
            그리고 run 눌러서 쿼리 실행
            h2 데이터베이스에 저장된 데이터를 확인 할 수 있습니다.
            애플리케이션을 실행하면 자동으로 생성한 엔티티 내용을 바탕으로 테이블이 생성되고,
            우리가 요청한 POST 요청에 의해 INSERT 문이 실행되어
            실제로 데이터가 저장된 겁니다.

            내일은 반복작업을 줄이는 테스트코드 작성 - H2 들어가는게 번거로워서 test 를 이용할 예정

            test 폴더에 BlogApiCOntrollerTest.java 를 만들기 위한 방법
            alt + enter -> create test 클릭
     */

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok().body(articles);
    }
    /*
        /api/articles GET 요청이 들어오몀ㄴ 글 전체를 조회하는 findAll() 메서드를 호출
        -> 다음 응답용 객체인 ArticleResponse 로 파싱해서 body 에 담아
        클라이언트에게 전송 -> 해당 코드에서는 stream 을 적용

        * stream : 여러 데이터가 모여 있는 컬렉션을 간편하게 처리하기 위해서 사용하는 기능 (자바 8에 추가)


     */

    @GetMapping("/api/articles/{id}")
    // URL 경로에서 값을 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        // URL 에서 {id} 에 해당하는 값이 id로 들어옴
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }
    /*
        @PathVariable : URL 에서 값을 가져오는 애너테이션.
            /api/articles/3 GET 요청을 받으면 id 에 3이 argument 로 들어오게 됩니다.
            그리고 이 값은 바로 전에 만든 서비스 클래스의 findById() 메서드로 넘어가서 3번 블로그
            글을 찾아옵니다. 그리고 그 글을 찾으면 3번 글의 정보(제목/내용)를 body 담아서
            웹브라우저 가지고 옵니다.
     */

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
    /*
        @PathVariable 통해서 {id}에 해당하는 값이 들어옵니다.

        POSTMAN

        GET http://localhost:8080/api/articles

        조회했을 때 저희가 작성한 data.sql 이 적용된
        제목 1부터 내용3까지의 JSON 데이터가 있는지 확인하고,
        거기서 특정 아이디의 데이터를 삭제

        조회 성공하셨으면
        DELETE로 HTTP 메서드를 바꿔주고

        http://localhost:8080/api/articles/1 하고 send 버튼을 눌러

        GET 으로 HTTP 메서드 바꿔주고
        http://localhost:8080/api/articles/1 -> 이건 지워졌기 때문에 조회 X

        http://localhost:8080/api/articles -> 얘로 조회해서 전체 글 목록이 줄었는지 확인

        다 확인 끝났으면 BlogApiControllerTest.java 가서 테스트 형태의 메서드로 삭제 확인
     */
}
