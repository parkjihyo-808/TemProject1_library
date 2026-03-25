package com.library.project.library.controller;

import com.library.project.library.dto.EventApplyDTO;
import com.library.project.library.service.EventApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class MemberEventApplyController {

    private final EventApplyService applyService;

    @GetMapping("/apply-list")
    public String myApplyList(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        log.info("--- [신청 내역 조회] 접속 시도 ---");

        // 1. 로그인 정보 확인
        if (userDetails == null) {
            log.warn("인증 정보가 없습니다. 로그인 페이지로 이동합니다.");
            return "redirect:/member/login";
        }

        // 2. 로그인한 유저의 아이디(mid) 추출
        // MemberServiceImpl에서 username에 mid를 넣었으니까 getUsername()이 곧 mid야!
        String mid = userDetails.getUsername();
        log.info("조회 요청 회원 아이디(mid): " + mid);

        try {
            // 3. 서비스 호출 (아이디 기준으로 리스트 가져오기)
            // ★ EventApplyService의 getMyList도 mid를 받는지 꼭 확인!
            List<EventApplyDTO> applyList = applyService.getMyList(mid);

            // 4. 데이터 전달
            model.addAttribute("applyList", applyList);
            log.info("신청 내역 조회 성공 (데이터 개수: " + applyList.size() + ")");

        } catch (Exception e) {
            log.error("신청 내역 조회 중 오류 발생: " + e.getMessage());
            model.addAttribute("error", "데이터를 불러오는 중 문제가 발생했습니다.");
        }

        // 5. 뷰 호출
        return "member/applyList";
    }
}