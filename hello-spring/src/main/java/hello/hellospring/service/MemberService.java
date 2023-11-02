package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    //보통 서비스 클래스는 되게 비즈니스에 가까운 용어를 써야 한다. (레포지토리는 그냥 findAll, save, 이런식인데)

    private final MemberRepository memberRepository;
    //memberRepository 변수를 통해 MemoryMemberRepository의 메서드를 호출하거나 데이터를 저장할 수 있음.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //memberRepository를 직접 new해서 final로 생성하는것이 아니라 construct를 따로 만들 수 있다. 외부에서 넣어주도록.

    /*
        회원 가입
        1) 비즈니스 로직 : 같은 이름의 회원가입은 안된다.
         */
    public Long join(Member member) {
        //1) 같은 이름이 있는 중복 회원X
        //(1)Optional<Member> result = memberRepository.findByName(member.getName());
        /*
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        */
        //null일 가능성이 있으면 Optinoal이라는걸 한번 감싸서 반환을 해주고 감싼덕분에 ifPresent 쓸수 있다.
        //Optional을 바로 반환하는것이 좋지는 않음.

        //(2)
        /*memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });*/

        //(3) 메소드 따로 extract
        validateDuplicateMember(member);  //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
        //회원가입 시 아이디만 반환
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    //중복회원인 경우 Exception이 발생하는지 검증해보자 -> Test Case활용



    /*
    전체 회원 조회
    */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}
