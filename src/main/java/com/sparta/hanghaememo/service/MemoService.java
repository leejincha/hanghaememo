package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.DelResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
//해당 클래스가 비즈니스 로직을 담은 Service 클래스임을 명시합니다. @Component 어노테이션을 사용해도 상관 없지만 @Sevice 어노테이션에 @Component 어노테이션의 기능이 포함되어 있고 @Service를 사용함으로써 해당 클래스가 Service의 역할을 하는 것을 명확하게 알 수 있습니다.
@RequiredArgsConstructor
//이 어노테이션은 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줍니다. 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용되곤 합니다.
public class MemoService {

    private final MemoRepository memoRepository; //데이터베이스와 연결하기 위해 repository와 연결하는 부분

    @Transactional //@Transactional이 붙은 메서드는 메서드가 포함하고 있는 작업 중에 하나라도 실패할 경우 전체 작업을 취소
    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto); //이 부분에서 저장할 테이블 한 줄 row를 생성한다고 생각하면 된다.
        memoRepository.save(memo);       //리파지토리에 생성한 메모를 .save()로 저장
        return new MemoResponseDto(memo); //클라이언트로의 반환은 ResponseDto 타입이다.
    }

    @Transactional(readOnly = true)
    public List<MemoResponseDto> getListMemos() { //전체 조회이기 때문에 List형식을 사용하였다.
        List<Memo> memoList = memoRepository.findAllByOrderByModifiedAtDesc(); //리파지토리에 있는 데이터를 List<Memo>인 memoList에 넣어주고
        List<MemoResponseDto> memoResponseDto = new ArrayList<>(); //새로운 ArrayList<>를 생성해 준다.
        for (Memo memo : memoList) { //memoList 하나하나의 값을 for each문을 통해 돌려주면서
            MemoResponseDto memoDto = new MemoResponseDto(memo); // MemoResponseDto인 memoDto에 담아주고
            memoResponseDto.add(memoDto); //담긴 데이터를 .add() 를 통해 아까 만들어준 ArrayList<>에 넣어 준다.
        }
        return memoResponseDto; //그리고 그 ArrayList를 반환하면 전체 게시글을 볼 수 있다.
    }

    @Transactional(readOnly = true)
    public MemoResponseDto getMemo(Long id) { //PK인 id를 이용해 선택된 게시글만 조회하는 메소드이다.
        Memo memo = memoRepository.findById(id).orElseThrow( //예외 처리를 통해 id가 일치하면 리파지토리에 있는 memo를 반환하고
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.") //일치하지 않는 경우 예외처리를 해준다.
        );
        return new MemoResponseDto(memo); //그리고 find된 데이터를 MemoResponseDto에 담아 반환해 준다.
    }

    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto) { //id와 requestDto 매개값을 이용ㅎ여
        Memo memo = memoRepository.findById(id).orElseThrow( //id가 일치하는 memo를 리포지토리에서 찾아주고
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.") //일치하지 않는 경우 예외처리를 해주고
        );
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo); //새로운 MemoResponseDto를 생성하여

        if (requestDto.getPassword().equals(memo.getPassword())) { //입력한 비밀번호와 DB에 저장된 비밀번호가 같을 경우
            memo.update(requestDto);     //.update()를 통해 새로 입력된 글로 다시 저장해준다.
            return memoResponseDto;
        } else {
            return memoResponseDto; //비밀번호가 일치하지 않다면, 원래의 게시글 그대로 보여준다.
        }

    }
    @Transactional
    public DelResponseDto deleteMemo(Long id, String password) { //매개값으로 id와 password만 사용 하였고 반환타입을 Dto로 바꿔주었다.
        Memo memo = memoRepository.findById(id).orElseThrow( //이 부분은 업데이트와 동일하다.
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );

        DelResponseDto result = new DelResponseDto(); //DelResponseDto 를 생성해주고,
        if (memo.getPassword().equals(password)) {     //비밀번호 일치여부를 조건문으로 확인한 뒤
            memoRepository.deleteById(id);                  // 비밀번호가 일치하면 해당 아이디 게시글을 삭제해준다.
            result.setResult("success", "게시물이 삭제되었습니다."); //성공하면 다음과 같은 반환값을 준다.
            return result;
        } else {
            result.setResult("failed", "비밀번호가 일치하지 않습니다"); //비밀번호가 일치하지 않으면 다음과 같은 반환값을 준다.
            return result;
        }
    }

//    @Transactional
//    public Map<String, Object> deleteMemo(Long id, MemoRequestDto requestDto) { //업데이트와 마찬가지의 매개값을 이용하였고, 반환타입은 'key : value' 형태인 Map<>을 사용하였다.
//        Memo memo = memoRepository.findById(id).orElseThrow( //이 부분은 업데이트와 동일하다.
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        Map<String, Object> response = new HashMap<>(); // response라는 HashMap<>을 생성해주고
//        if (requestDto.getPassword().equals(memo.getPassword())) { //위와 마찬가지로 조건문을 통해 비밀번호 일치여부를 판단하여
//            memoRepository.deleteById(id); //비밀번호가 일치하면 .deleteById()를 통해 해당 아이디 게시글을 삭제해 준다.
//            response.put("success",true);  //그리고 반환으로 해쉬맵의 .put()을 사용하여 키는 "success" 밸류는 "ture"가 되도록 처리한다.
//            return response;
//        } else {
//            response.put("success",false); //비밀번호가 일치하지 않으면 마찬가지로 .put()을 사용하여 값을 반환해 준다.
//            return response;
//        }
//    }
}
