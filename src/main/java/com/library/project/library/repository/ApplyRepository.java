package com.library.project.library.repository;

import com.library.project.library.entity.ApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<ApplyEntity, Long> {
    /**
     * JpaRepository를 상속받는 것만으로도 기본적인 CRUD가 가능합니다.
     * <ApplyEntity, Long> 은 <엔티티 클래스명, PK의 타입>을 의미합니다.
     * * - 저장: save(entity)
     * - 조회: findById(id)
     * - 전체 조회: findAll()
     * - 삭제: deleteById(id)
     */
}

/*
 * ========== ApplyRepository 설명 ==========
 * - 역할: ApplyEntity(시설 대관 신청)의 DB 접근을 담당하는 리포지토리
 * - 쓰이는 곳: ApplyServiceImpl에서 사용
 * - JpaRepository 상속만으로 기본 CRUD (save, findById, findAll, deleteById) 사용 가능
 */

