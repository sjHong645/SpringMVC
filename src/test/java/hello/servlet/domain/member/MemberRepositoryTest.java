package hello.servlet.domain.member;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {

        // given
        // Member 객체 하나를 만든다
        Member member = new Member("hello", 20);

        // when
        // 만든 Member 객체를 memberRepository에 저장한다.
        Member savedMember = memberRepository.save(member);

        // then
        // 제대로 저장되었는지 확인한다
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        // given
        // 2개의 Member 객체를 생성해 저장한다.
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        // 전체를 조회해서
        List<Member> result = memberRepository.findAll();

        // then
        // 2개가 저장되어 있고 앞서 저장한 member1, member2가 저장되어 있는지
        // 확인한다
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2);
    }

}