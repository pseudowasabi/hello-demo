package com.psdwsb.hellodemo.repository;

import com.psdwsb.hellodemo.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // * Test 순서는 보장이 안 된다. 모든 Test는 순서랑 상관없이, method 별로 작동하도록 설계해야 함.
    @AfterEach
    public void afterEach() {
        // @AfterEach는 각 method가 끝날 때마다 실행함
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();

        member.setName("springnewbee");
        repository.save(member);

        Member result = repository.findById(member.getId())
                                    .get();
        // Optional<> 타입에서 객체 가져올 때 : .get() - 권장하는 방법은 아님.
        /*System.out.println("compare = " + (result == member));*/

        // 방법 1 : import org.junit.jupiter.api.Assertions;
        /*Assertions.assertEquals(member, result);
        Assertions.assertEquals(member, null);*/

        // 방법 2 : import org.assertj.core.api.Assertions;
        assertThat(member).isEqualTo(result);
        //assertThat(member).isEqualTo(null);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("newbee1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("newbee2");
        repository.save(member2);

        Member result = repository.findByName("newbee1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        /*member1.setName("oldbee1");
        // 여기에서 "newbee1"으로 하고, findAll()에 대한 Test만 하면 문제가 없지만,
        // Class 전체에 대한 Test 실행 시 문제가 발생한다. (같은 repository에 save하기 때문)
        // 따라서 다른 이름인 "oldbee1"으로 변경해준다.
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("oldbee2");*/

        // * 아래 그대로 실행하고, @AfterEach를 통해 clearStore() 실행해주면 해결 (!)
        member1.setName("newbee1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("newbee2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
        /*assertThat(result.size()).isEqualTo(3);     // 오류 케이스 */

    }

}
