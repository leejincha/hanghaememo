package com.sparta.hanghaememo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //스프링 부트의 Entry 포인트인 실행 클래스에 @EnableJpaAuditing 어노테이션을 적용하여 JPA Auditing을 활성화 해야하는 것
@SpringBootApplication //@SpringBootApplication 어노테이션은 스프링 부트의 가장 기본적인 설정을 선언해 줍니다.
public class HanghaememoApplication {

    public static void main(String[] args) {

        SpringApplication.run(HanghaememoApplication.class, args);
    }

}
