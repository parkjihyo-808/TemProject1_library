package com.library.project.library.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 내부 PK

    @Column(length = 50, nullable = false, unique = true)
    private String mid; // 로그인 아이디

    @Column(length = 100, nullable = false)
    private String mpw; // 비밀번호

    @Column(length = 50, nullable = false)
    private String mname; // 이름

    private String email;
    private String region;

    @Enumerated(EnumType.STRING)
    private Role role; // [USER, ADMIN]

    public enum Role {
        USER, ADMIN
    }

    // 눈에 보이지 않지만, BaseEntity를 이용해서, regDate, modDate 도 추가가 될 예정.

    // 정보 수정 메서드 (내 서재/마이페이지용)
    public void change(String mname, String email, String region, String mpw) {
        this.mname = mname;
        this.email = email;
        this.region = region;
        this.mpw = mpw;
    }




}

/*
 * ========== Member 엔티티 설명 ==========
 * - 역할: 도서관 회원 정보를 저장하는 엔티티
 * - 쓰이는 곳: MemberRepository, MemberServiceImpl, MemberController, RentalService에서 사용
 *
 * [주요 필드]
 * - id: DB 자동생성 PK (내부 관리용)
 * - mid: 로그인 아이디 (unique, 중복 불가)
 * - mpw: 비밀번호
 * - mname: 회원 이름
 * - email: 이메일 주소
 * - region: 지역
 * - role: 권한 (USER / ADMIN)
 * - regDate, modDate: BaseEntity 상속 (생성일, 수정일)
 *
 * [메서드]
 * - change(): 회원 정보 수정 (마이페이지에서 이름/이메일/지역/비밀번호 변경 시 호출)
 *
 * [내부 enum]
 * - Role: USER(일반 회원), ADMIN(관리자) 구분
 */