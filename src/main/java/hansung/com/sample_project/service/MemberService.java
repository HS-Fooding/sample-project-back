package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.Member;
import hansung.com.sample_project.repository.MemberRepository;
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
        // TODO : 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 단일 회원 조회
    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
