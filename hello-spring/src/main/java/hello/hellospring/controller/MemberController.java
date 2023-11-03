package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    //private final MemberService memberService = new MemberService();
    /*
    위처럼 new로 해서 생성할 수 있지만, Spring 컨테이너에 등록을 하고 관리를 하게 된다면 Spring컨테이너에서 받아서 쓸 수 있도록 바꿔야 한다.
    객체를 new로 해서 사용한다면 여러 컨트롤러에서 멤버 서비스를 가져다 쓸 수 있음.
    그래서 아래 방법이 더 낫다.
     */

    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    //@controller가 뜨면서 생성자를 호출하는데 생성자에 @Autowired되어있으면
    //memberService를 spring이 컨테이너에 있는 memberService를 가져다가 연결을 시켜준다.
}
