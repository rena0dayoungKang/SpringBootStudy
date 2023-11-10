package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
        //MemberRepository는 인터페이스니까 구현체를 return
        //추후에 DB를 정하고 나면 구현체만 DbMemberRepository 이런식으로만 고쳐주면 코드의 다른 부분을 손대지 않고 DI할 수 있음
    }


}
