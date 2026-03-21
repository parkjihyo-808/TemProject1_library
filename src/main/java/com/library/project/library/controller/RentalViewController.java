package com.library.project.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RentalViewController {

    // 관리자용 대출 관리 화면
    // 주소: http://localhost:8080/rentals
    @GetMapping("/rentals")
    public String adminRentals() {
        return "rental/rentals";  // templates/rental/rentals.html
    }

    // 회원용 내 대출 현황 화면
    // 주소: http://localhost:8080/user_rentals
    @GetMapping("/user_rentals")
    public String userRentals() {
        return "rental/user_rentals";  // templates/rental/user_rentals.html
    }
}

/*
 * ========== RentalViewController 설명 ==========
 * - 역할: 대출 관련 화면(View) 이동만 담당하는 컨트롤러 (비즈니스 로직 없음)
 *
 * [메서드]
 * - adminRentals(): GET /rentals → 관리자용 대출 관리 화면 (rental/rentals.html)
 * - userRentals(): GET /user_rentals → 회원용 내 대출 현황 화면 (rental/user_rentals.html)
 */
