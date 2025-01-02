package springbootdeveloper;

import me.jinwo.springbootdeveloper.Member;
import me.jinwo.springbootdeveloper.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers(){
        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @Test
    void getMembersById(){
        Member member = memberRepository.findById(2L).get();

        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberByName(){
        Member member = memberRepository.findByName("C").get();

        assertThat(member.getId()).isEqualTo(3);
    }

    @Test
    void saveMember(){
        Member member = new Member(1L,"A");

        memberRepository.save(member);

        assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("A");
    }

    @Test
    void saveMembers(){
        List<Member> members = List.of(new Member(2L,"안근수"), new Member(3L, "안근순"));

        memberRepository.saveAll(members);

        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @Test
    void deleteMemberById(){
        memberRepository.deleteById(2L);

        assertThat(memberRepository.findById(2L).isEmpty()).isTrue();
    }

    @Sql("/insert-members.sql")
    @Test
    void update(){
        Member member = memberRepository.findById(2L).get();

        member.changName("BC");

        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("BC");
    }
}