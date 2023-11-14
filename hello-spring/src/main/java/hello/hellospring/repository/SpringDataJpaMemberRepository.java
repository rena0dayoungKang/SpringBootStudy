package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //interface는 implements가 아니고 extends
    //interface는 다중상속도 가능 MemberRepository


    //JPQL : select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);



}
