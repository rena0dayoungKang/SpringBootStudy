package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
//@Component
public class MemberController {

    //private final MemberService memberService = new MemberService();
    /*
    위처럼 new로 해서 생성할 수 있지만, Spring 컨테이너에 등록을 하고 관리를 하게 된다면 Spring컨테이너에서 받아서 쓸 수 있도록 바꿔야 한다.
    객체를 new로 해서 사용한다면 여러 컨트롤러에서 멤버 서비스를 가져다 쓸 수 있음.
    그래서 아래 방법이 더 낫다.
     */

    //@Autowired
    private final MemberService memberService;


    //alt+insert 로 setter만들기
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    //@controller가 뜨면서 생성자를 호출하는데 생성자에 @Autowired되어있으면
    //memberService를 spring이 컨테이너에 있는 memberService를 가져다가 연결을 시켜준다.  @Autowired -> Dependency Injection 의존관계 주입

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        //System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/";
        //회원가입이 끝나면 홈 화면으로 보내는 것
    }

    @GetMapping("/members")
    public String list(Model model) {
        //ctrl+alt+v
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";

    }

}
