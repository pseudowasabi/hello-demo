package com.psdwsb.hellodemo.repository;

import com.psdwsb.hellodemo.domain.Member;
import org.junit.jupiter.api.AfterEach;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;
    // * 현업에서는 동시성 문제 발생 가능, 공유되는 변수인 경우 ConcurrentHashMap, AtomicLong 사용을 권장 (!)

    public void clearStore() {
        store.clear();
    }

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // Optional.ofNullable() 하면 null이어도 감싸서 반환하고, 이를 client에서 별도 처리 가능
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // .findAny() : 하나라도 찾으면 반환
                            // 끝까지 돌려서 없으면 Optional<>에 null 감싸서 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
