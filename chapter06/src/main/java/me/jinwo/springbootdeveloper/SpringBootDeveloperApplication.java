package me.jinwo.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
    1. 사전 지식 : API 와 REST API
        API : 프로그램 간에 상호 작용하기 위한 매개체

        식당으로 알아보는 API
            여러분이 손님인데, 식당 들어갔어요 -> 점원에게 요리를 주문합니다(주방으로 달려가서 요를 주문하는게 아니라)
            그리고 점원은 주방에 가서 요리를 만들어 달라고 요청합니다.
                 요청       요청
            손님 <---> 점원 <---> 주방
                 응답       응답

            의 형태를 띄고 있는데, 여기서 손님은 클라이언트(client), 주방에서 일하는 요리사를 서버(sever)라고 생각
            그리고 중간에 있는 점원을 API 라고 보시면 됩니다. -> '매개체'라고 한 점 기억!

            우리는 웹 사이트의 주소를 입력해서 '구글 메인 화면을 보여줘'라고 요청하면, API 는 이 요청을 받아서 서버에게 전달
            그러면 서버는 API 가 준 요청을 처리해 결과물을 구성해서 이것을 다시 API 로 전달하고, API 는 최종 결과물을 브라우저에
            보내주면 화면을 볼 수 있게 됩니다.
                이처럼 API 는 클라이언트의 요청을 서버에 잘 전달하고, 서버의 결과물을 클라이언트에게 잘 돌려주는 역할을 합니다.

                그렇다면 REST API 란?

            웹의 장점을 최대한 활용하는 형태라고 알려진 REST API
                Representational State Transfer 를 줄인 표현으로, 자원을 이름으로 구분해 자원의 상태를 주고 받는 API 방식

                URL 의 설계 방식

                특징 :
                    REST API 는 서버/클라이언트 구조, 무상태, 캐시 처리 기능, 계층화, 일관성과 같은 특징.

                장점 :
                    URL 만 보고도 무슨 행동을 하는 API 인지 명확하게 알 수 있음.
                    무상태 특징으로 인해 클라이언트와 서버으 ㅣ역할이 명확하게 분리됨.
                    HTTP 표준을 사용하는 모든 플랫폼에서 사용 가능

                단점 :
                    HTTP 메서드, 즉 GET, POST 와 같은 방식의 개수에 제한이 있음
                    설계를 위해 공식적으로 제공되는 표준 규약이 없음.

                장단점을 고려했을 때, 주소와 HTTP 메서드만 보고 요청의 내용을 파악할 수 있다는 장점으로
                REST 하게 디자인한 API 를 보고 RESTful API 라고 부르기도 하는 편.

                REST API 를 사용하는 방법
                    규칙 1. URL 에는 동사를 쓰지 말고, 자원을 표시한다.
                        *자원 : 가져오는 데이터를 의미. 예를 들어 학생 중에 id가 1인 학생ㅇ의 정보를 가져오는 URL은
                        1) 예시 : /students/1
                        2) 예시 : /get-student?student_id=1
                    과 같은 방식으로 설계 할 수 있는데,

                    이중 더 REST API에 맞는 형식은 1)예시에 해당함. 2) 예시의 경우 자원이 아닌 다른 표현을 섞어 사용했기 때문
                    (get).
                    동사를 사용해서 생기게 되는 추후의 문제점 예시 -> 데이터를 요청하는 URL 을 설계 할때
                        A 개발자는 get, B 개발자는 show 를 쓰면 get-student, show-data 등으로 협의가 이루어지지 않은 설계가
                        될 가능성이 있다.
                    '기능/행위'에 해당하지만 RESTful API 에서는 동사를 사용하지 않습니다.

                    규칙 2. 동사는 HTTP 메서드로
                        HTTP 메서드 : 서버에 요청을 하는 방법을 나누느 것. -> POST, GET, PUT, DELETE
                            만들고, 읽고, 업데이트하고, 삭제한다 Create / Read / Delete 라고 해서 CRUD 라고 합니다.
                        예를 들어 블로그에 글을 쓰는 설계를 한다고 가정했을 때

                            1) id가 1인 블로그 글을 '조회'하는 API    : GET/articles/1
                            2) 블로그 글을 '추가'하는 API            : POST/articles -> 추가 완료되기 잔이라 DB상 id가 없음
                            3) 블로그 글을 '수정'하는 API            : PUT/articles/1
                            4) 블로그 글을 '삭제'하는 API            : DELETE/articles/1

                       * GET/PSOT 등은 URL 에 입력하는 값이 아니라 내부적으로 처리하는 방식을 미리 정하는 것으로
                       실제로 HTTP 메서드는 내부에서 서로 다른 함수로 처리하는데, 대놓고 적는 일은 잘 없음.

                       이외에도 '/'는 계층 관계를 나타내는 데 사용하거나, 밑줄 대신 하이픈을 사용하거나, 자원의 종류가
                       컬렉션인지 도큐먼트인지에 따라 단수, 복수를 나누거나 하는 등의 규칙이 있지만 마찬가지로 추후 설명 예정

    2. 블로그 개발을 위한 엔티티 구성하기
        이제 프로젝트 시작할 예정인데,

        엔티티 구성하겠습니다.
        엔티티와 매핑되는 테이블 구조는 ↓
            +-------------------------------------------------------------+
            | 컬럼명  | 자료형        | null 허용 |  키   |       설명       |
            +-------------------------------------------------------------+
            | id     | BIGINT       |     N     | 기본키 | 일련번호, 기본 키 |
            ---------------------------------------------------------------
            | title  | VARCHAR(255) |     N     |       | 게시물의 제목     |
            ---------------------------------------------------------------
            | content| VARCHAR(255) |     N     |       | 내용             |
            +--------------------------------------------------------------+

    01 단계 - springbootdeveloper 패키지에 domain 패키지 생성, domain 패키지에 Article.java 파일을 작성
    02 단계 - repository package 생성 (domain 과 같은 라인에) -> BlogRepository.java 인터페이스 생성
    03 단계 - repository 패키지와 같은 라인 dto 패키지 생성 -> 컨트롤러에서 요청한 본문을 받을 객체인 AddArticleRequest.java 파일을 생성
    04 단계 - dto 와 동일한 service 패키지 생성 -> BlogService.java 생성
    05 단계 - service 와 동일한 라인에 controller 패키지 생성한 뒤에 BlogApiController.java 생성
    06 단계 - API 가 잘 작동하는지 하나 테스트를 해볼 예정,H2 콘솔 활성화,application.yml 로 이동, Postman 실행
 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
