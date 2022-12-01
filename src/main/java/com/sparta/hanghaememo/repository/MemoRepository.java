package com.sparta.hanghaememo.repository;


import com.sparta.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//데이터베이스와 연결되는 것이 리포지토리! <>안에 있는 테이블이랑 연결한다고 생각하면 된다.
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();
    //Spring Data JPA Query Methods
    //findAll(전체를 가져와라)By(대신 조건이 있음)OrderBy(정렬을 할건데)ModifiedAt(수정 날짜 기준)Desc(내림차순으로)

}