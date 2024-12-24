package me.jinwo.springbootdeveloper;
/*
    모든 프로젝트에는 main 에 해당하는 클래스가 존재 -> 실행용 클래스
    이제 이 class 를 main 클래스로 활용할 예정
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
/*
    처음으로 SpringBootDeveloperApplication 파일을 실행시키면 whiteLabel error page 가 뜹니다.
    현재 요청에 해당하는 페이지가 존재하지 않기 때문에 생겨난 문제
    -> 하지만 스프링 애플리케이션은 실행됨

    그래서 error 페이지가 기분 나쁘니까 기본적으로 실행될 때의 default 페이지를 하나 생성

    20241223 Mon.
        1. 인텔리제이 커뮤니티 버전 설치 -> bin.PATH 체크라고 나머지는 전부 디폴트
        2. git 설치 -> 설치창 중간에 main 칸만 체크
        3. github 연동 -> web_development_springboot -> 현재 문제 있음
        4. 인텔리제이에 Gradle 및 SpringBoot 프로젝트 생성
        5. POSTMAN 설치
 */
