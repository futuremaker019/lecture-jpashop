package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member);  // 중복 회원 검증
        memberRepository.save(member);

        // em.persist하면 영속성 컨텍스트에 key:value 형태로 값이 들어가는데 key로 id를 사용한다.
        // 동시에 Member 객체의 id 값이 GeneratedValue에 의해 id에 항상 값이 들어가게 된다.
        // 그래서 id에 값이 있다는 것이 항상 보장된다.
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
