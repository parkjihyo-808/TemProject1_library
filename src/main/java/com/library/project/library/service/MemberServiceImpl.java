package com.library.project.library.service;

import com.library.project.library.dto.MemberDTO;
import com.library.project.library.entity.Member;
import com.library.project.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List; // 추가
import java.util.Optional;
import java.util.stream.Collectors; // 추가

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * [시큐리티 필수 메서드] 로그인 시 아이디(mid)로 사용자 정보를 가져오는 역할
     */
    @Override
    public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {
        log.info("--- Security Login Attempt mid: " + mid + " ---");

        Optional<Member> result = memberRepository.findByMid(mid);
        Member member = result.orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다: " + mid));

        log.info("사용자 확인 성공: " + member);

        return new User(
                member.getMid(),
                member.getMpw(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()))
        );
    }

    @Override
    public Long register(MemberDTO memberDTO) {
        log.info("MemberServiceImpl - register: " + memberDTO);
        Member member = modelMapper.map(memberDTO, Member.class);

        String encodedPw = passwordEncoder.encode(memberDTO.getMpw());
        member.change(memberDTO.getMname(), memberDTO.getEmail(), memberDTO.getRegion(), encodedPw);

        return memberRepository.save(member).getId();
    }

    @Override
    public void updatePassword(String mid, String newPw) {
        Member member = memberRepository.findByMid(mid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String encodedPw = passwordEncoder.encode(newPw);
        member.change(member.getMname(), member.getEmail(), member.getRegion(), encodedPw);
    }

    @Override
    public void modify(MemberDTO memberDTO) {
        log.info("MemberServiceImpl - modify: " + memberDTO);
        Optional<Member> result = memberRepository.findByMid(memberDTO.getMid());
        Member member = result.orElseThrow();

        String encodedPw = passwordEncoder.encode(memberDTO.getMpw());
        member.change(memberDTO.getMname(), memberDTO.getEmail(), memberDTO.getRegion(), encodedPw);

        memberRepository.save(member);
    }

    @Override
    public MemberDTO readOne(String mid) {
        Optional<Member> result = memberRepository.findByMid(mid);
        Member member = result.orElseThrow();
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        memberDTO.setRole(member.getRole().name());
        return memberDTO;
    }

    @Override
    public void remove(String mid) {
        Optional<Member> result = memberRepository.findByMid(mid);
        Member member = result.orElseThrow();
        memberRepository.deleteById(member.getId());
    }

    @Override
    public boolean checkId(String mid) {
        return memberRepository.existsByMid(mid);
    }

    @Override
    public boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public String findId(String mname, String email) {
        return memberRepository.findByMnameAndEmail(mname, email)
                .map(Member::getMid)
                .orElse(null);
    }

    @Override
    public boolean checkMemberForPw(String mid, String email) {
        return memberRepository.findByMidAndEmail(mid, email).isPresent();
    }

    /**
     * [추가된 메서드] 담당자가 인터페이스에 추가한 검색 기능을 여기서 구현합니다.
     */
    @Override
    public List<MemberDTO> searchMembers(String keyword) {
        log.info("MemberServiceImpl - searchMembers: " + keyword);

        // 에러를 없애기 위해 일단 빈 리스트를 반환합니다.
        // 나중에 실제 검색 로직이 필요하면 리포지토리와 연결하세요!
        return Collections.emptyList();
    }
}