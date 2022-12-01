package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.DelResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Map;

@RestController //@RestController는 @Controller에 @ResponseBody가 추가된 것입니다. 당연하게도 RestController의 주용도는 Json 형태로 객체 데이터를 반환하는 것입니다.
@RequiredArgsConstructor //이 어노테이션은 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줍니다. 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용되곤 합니다.
public class MemoController {
    private final MemoService memoService; //MemoService와 연결

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        return memoService.createMemo(requestDto); //MemoRequestDto 형식으로 받은 데이터를 저장하는 부분.  Service 클래스에서 creatMemo 라는 메소드를 이용해 처리를 한 값을 MemoResponseDto 타입으로 반환한다.
    }

    @GetMapping("/api/memos") //List 형식으로 저장된 데이터를 모두 Client에게 보여주는 부분. Service 클래스에서 getListMemos라는 메소드를 이용하여 데이터를 모두 보여주는데, 마찬가지로 DB에 저장된 값을 ResponseDto타입으로 반환하여 보여준다.
    public List<MemoResponseDto> getListMemos() {
        return memoService.getListMemos();
    }

    @GetMapping("/api/memos/{id}") //@PathVariable 어노테이션을 이용하여 URL에서 PK인 {id}값을 통하여 필요한 정보만 GET해준다. 역시나 클라이언트로 반환되는 타입은 Dto !
    public MemoResponseDto getMemos(@PathVariable Long id) {
        return memoService.getMemo(id);
    }

    @PutMapping("/api/memos/{id}") //Service클래스의 updateMemo라는 메소드를 id값과 requestDto를 이용하여 실행한다. 게시글을 수정하는 메소드와 클라이언트로부터 받은 id, requestDto를 연결 시켜주는 부분이라고 할 수 있다.
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.update(id, requestDto);
    }

    @DeleteMapping("/api/memos/{id}") // 반환타입을 새로 만든 DelResponseDto로 바꿔주어 봤다.
    public DelResponseDto deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.deleteMemo(id, requestDto.getPassword());//그리고 매개값도 id와 비밀번호만 넣어주었다.
    }

    //    @DeleteMapping("/api/delete/{id}") // 위와 비슷하지만 여기서 반환타입은 Map<>형식으로 한 것만 차이가 있다. 게시글을 삭제하는 Serviece 클래스 내의 메소드와 클라이언트가 입력하는 requestDto 를 연결하는 부분이라고 이해하면 될 것 같다.
//    public Map<String,Object> deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
//        return memoService.deleteMemo(id, requestDto);
//    }
}
