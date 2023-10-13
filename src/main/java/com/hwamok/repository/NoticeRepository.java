package com.hwamok.repository;

import com.hwamok.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
  // querydsl은 사용방법이 정해져있음 - 이름까지 정해져있음
  // 이름 내 맘대로 지으면 동작안함

  // {모체이름}Custom : interface
  // {모체이름}CustomImpl : class

  // 모체에서는 {모체이름}Custom이 interface를 extends 받아야함

  Page<Notice> findByTitleContains(String keyword, Pageable pageable);
}
