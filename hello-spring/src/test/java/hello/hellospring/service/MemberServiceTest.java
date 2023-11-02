package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach //메소드 동작하기 전에 실행
    public void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }
    //테스트 실행할 때 마다 각각 객체 생성 -> 같은 memoryMemberRepository가 사용된다.
    //memberService 입장에서 내가 직접 new 하지 않는다. 외부에서 리포지토리를 넣어준다. 이것을 Dependency Injection이라 함

    //테스트 끝날때마다 리포지토리를 깔끔하게 지워주는 코드를 넣어서 테스트 실행이 메소드별로 진행할 수 있도록
    @AfterEach //메소드가 실행이 끝날 때마다 어떤 동작을 하도록
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }

    //테스트는 한글로도 많이 적기도 한다.
    //테스트는 정상플로우도 중요한데, 예외플로우가 훨씬 더 중요
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when 검증하고 싶은 부분
        Long saveId = memberService.join(member);

        //then 검증하는 부분
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when

        /*
        (1)방법
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다. 123");
        }
        */

        /*
        (2)방법
        */
        memberService.join(member1);
        //assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //assertThrows(NullPointerException.class, () -> memberService.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}