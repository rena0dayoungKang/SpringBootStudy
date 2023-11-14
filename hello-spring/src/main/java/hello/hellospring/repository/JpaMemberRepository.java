package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //JPA는 EntityManager라는 걸로 모든게 동작한다. 일단 JPA를 쓰려면 EntityManager를 주입받아야 된다
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }   //이렇게 하면 JPA가 인서트 쿼리를 다 만들어서 DB에 집어놓고 id까지 set 까지 다 해줌


    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }   //em.find에서 조회할 타입이랑 식별자 pk값 넣어주면 조회가 된다.

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }   //find by name 같은 경우에는 jpql 이라는 객체지향 쿼리언어를 써야한다.

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)   //jpql 쿼리언어 : 객체를 대상으로 쿼리날린다.
                .getResultList();      //inline Variable해서 합쳐준다.
    }
    // pk 기반이 아닌 나머지들은 jpql이라는 걸 작성해줘야 된다.
}
