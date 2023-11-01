package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    //굳이 퍼블릭으로 안해도 된다. -> 다른데서 갖다 쓸게 아니라서
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //테스트 끝날때마다 리포지토리를 깔끔하게 지워주는 코드를 넣어서 테스트 실행이 메소드별로 진행할 수 있도록
    @AfterEach //메소드가 실행이 끝날 때마다 어떤 동작을 하도록
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //사실 get 으로 바로 꺼내는게 좋은 방법은 아닌데 테스트 코드 같은 데서 그냥 이렇게 한다.

        //1)
        //new 에서 한 거랑 내가 db 에서 꺼낸 거랑 똑같으면 true
        //System.out.println("result = " + (result == member));

        //2)
        //위와 같이 계속 true로 찍어볼 수 없으니 아래의 Assertions 방법 이용
        //Assertions.assertEquals(member, result); //(expected, actual) 순서이다.
        //Assertions.assertEquals(member, null);

        //3)
        //요즘에 많이 사용하는 방법
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //멤버1, 멤버2 그러니까 Spring1, Spring2라는 회원이 회원에 가입된것.

        Member result = repository.findByName("spring2").get();

        assertThat(result).isEqualTo(member2);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
        //assertThat(result.size()).isEqualTo(3);
    }
}
