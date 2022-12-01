package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto { //메모 테이블이 만들어 진 것, 클라이언트에서 받아온 데이터들이다.

    private String username;
    private String contents;
    private String password;
    private String title;

}

