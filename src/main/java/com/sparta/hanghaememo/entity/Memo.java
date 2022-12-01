package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;;
import javax.persistence.*;

@Getter //Class 내 모든 필드의 Getter method를 자동 생성한다.
@Entity //실제 DB의 테이블과 매칭될 Class임을 명시한다. 즉, 테이블과 링크될 클래스임을 나타낸다.
@NoArgsConstructor //Entity Class를 프로젝트 코드상에서 기본생성자로 생성하는 것은 금지하고, JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가한다.

public class Memo extends Timestamped {
    @Id //해당 테이블의 PK 필드를 나타낸다.
    @GeneratedValue(strategy = GenerationType.AUTO) //기본키(PK) 값에 대한 생성 전략을 제공
    private Long id;

    @Column(nullable = false) //@Column 어노테이션은 객체 필드와 DB 테이블 컬럼을 맵핑한다.
    private String username;

    @Column(nullable = false)
    private String contents;
    @JsonIgnore //필드 레벨에서 무시 될 수있는 속성을 표시하는 데 사용됩니다.
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String title;


    public Memo(@NotNull MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.password = requestDto.getPassword();
    }

    public void update(@NotNull MemoRequestDto memoRequestDto) {
        this.username = memoRequestDto.getUsername();
        this.contents = memoRequestDto.getContents();
        this.title = memoRequestDto.getTitle();
        this.password = memoRequestDto.getPassword();

    }
}