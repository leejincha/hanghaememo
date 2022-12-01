package com.sparta.hanghaememo.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //해당 어노테이션은 객체의 입장에서 '생성 시간', '수정 시간' 같은 공통 매핑 정보가 필요할 때 사용
@EntityListeners(AuditingEntityListener.class) //EntityListeners란 JPA Entity에서 이벤트가 발생할 때마다 특정 로직을 실행시킬 수 있는 어노테이션입니다.
public class Timestamped {

    @CreatedDate  //Entity가 생성되어 저장될 때 시간이 자동으로 저장
    private LocalDateTime createdAt;

    @LastModifiedDate //조회한 Entity의 값을 변경할 때 시간이 자동으로 저장
    private LocalDateTime modifiedAt;
}