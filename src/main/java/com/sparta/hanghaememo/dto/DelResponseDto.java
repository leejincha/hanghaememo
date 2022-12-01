package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class DelResponseDto {

    private String result;
    private String message;

    public void setResult(String result, String message){
        this.result = result;
        this.message = message;
    }
}
