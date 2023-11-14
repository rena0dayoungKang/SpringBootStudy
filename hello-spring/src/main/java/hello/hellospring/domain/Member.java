package hello.hellospring.domain;

import jakarta.persistence.*;

@Entity //JPA가 관리하는 entity라고 표현을 합니다.
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //DB에 인서트 하면 자동으로 아이디가 생성되는 것, 아이덴티티 전략
    private Long id;

    @Column(name = "username") //DB에 있는 컬럼명을 username으로 한다.
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
