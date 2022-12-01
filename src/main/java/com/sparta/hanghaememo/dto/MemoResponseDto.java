package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class MemoResponseDto { //DB에서 꺼내온 데이터를 가공해 클라이언트에게 보여지게 되는 데이터들.

    private String username;
    private String contents;
    private String title;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Long id;


    public MemoResponseDto(Memo memo) {
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.title = memo.getTitle();
        this.createdAt = memo.getCreatedAt();
        this. modifiedAt = memo.getModifiedAt();
        this.id = memo.getId();

    }
}
