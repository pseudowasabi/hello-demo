package com.psdwsb.hellodemo.repository;

import com.psdwsb.hellodemo.domain.Member;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;
    // ** JPA에서 가장 중요한 EntityManager !!!
    // DataSource 대신에 사용하는 듯

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // pk를 이용한 select는 Entity class와 column 명시만으로 가능
        return Optional.ofNullable((Member) em.find(Member.class, id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // pk가 아닌 걸로 조회할 때에는, JPQL이라는 객체지향 쿼리 언어를 이용해야 함
        // 참고 : Spring Data JPA 이용하면 아래의 JPQL도 작성할 필요가 없다.
        List<Member> result = em.createQuery("select m from Member m where m.name = :name",
                                                Member.class)
                                                .setParameter("name", name)
                                                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
