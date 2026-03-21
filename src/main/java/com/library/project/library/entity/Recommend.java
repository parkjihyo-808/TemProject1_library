package com.library.project.library.entity;

import jakarta.persistence.*;
import lombok.*;

// 추천 테이블
// - 추천하기 버튼 클릭 시 row 추가, 추천 해제 시 row 삭제
// - 추천 수는 book_id로 COUNT 해서 집계
// - User 연결 후 book_id + user_id unique 제약으로 중복 추천 방지 예정
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Recommend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // TODO: User 연결 시 아래 주석 해제 및 unique 제약 추가 (중복 추천 방지)
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id", nullable = false)
    // private User user;

    // TODO: User 연결 시 unique 제약 추가 (한 유저가 같은 책 중복 추천 방지)
    // @Table(uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "user_id"}))
}

/*
 * ========== Recommend 엔티티 설명 ==========
 * - 역할: 도서 추천 기록을 저장하는 엔티티 (추천 시 row 추가, 해제 시 row 삭제)
 * - 쓰이는 곳: RecommendRepository, BookServiceImpl에서 사용
 *
 * [주요 필드]
 * - id: 추천 기록 PK
 * - book: 추천된 도서 (ManyToOne 연관관계)
 *
 * [추천 수 집계 방식]
 * - book_id로 COUNT 해서 추천 수 계산
 * - 추천하기: BookServiceImpl.recommend() → Recommend row 추가
 * - 추천 해제: BookServiceImpl.unrecommend() → Recommend row 삭제
 *
 * [TODO]
 * - User 연결 후 book_id + user_id unique 제약으로 중복 추천 방지 예정
 */