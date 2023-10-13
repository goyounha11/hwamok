package com.hwamok.repository;

import com.hwamok.entity.Notice;
import com.hwamok.entity.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
  private JPAQueryFactory jpaQueryFactory;

  // QClass = Proxy객체 - querydsl의 거지같은 점이 나옴
  // Qclass가 없으면 querydsl 사용이 불가능함
  // Qclass가 가끔 안만들어질때가 있음

  private static final QNotice notice = QNotice.notice;

  public NoticeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Page<Notice> getNotices(String keyword, Pageable pageable) {
    // 조건을 구현하는 방법
    // BooleanBuilder (o)
    // Where 직접 구현 (x)
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    // 메소드로 불리언 빌더 구현
    // and ==> and
    // or ==> or

    //querydsl, jpa = N + 1문제

    booleanBuilder.and(eqKeyword(keyword));

    return new PageImpl<>(
            jpaQueryFactory
                    .selectFrom(notice)
                    .where(booleanBuilder)
                    .orderBy(notice.id.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .stream().collect(Collectors.toList()),
            pageable,
            jpaQueryFactory.select(notice.id).from(notice).where(booleanBuilder).stream().count()
    );
  }
  // ?1 = null
  // 네이밍하는 방법이 where email = ?1 and name = ?2
  // where name = ?1
  // dynamic query => 동적 쿼리 <-- querydsl 쓰는 이유
  // 동적쿼리는 보통 불리언 빌더 => 메소드로 구현 => eq{parameterName}()
  private BooleanExpression eqKeyword(String keyword) {
    // keyword가 null이거나 빈값이면 => return null
    // keyword가 있다면 title like하면되고
    // content에서도 like
    if(Strings.isBlank(keyword)) {
      return null;
    }

    return notice.title.contains(keyword)
            .or(notice.content.contains(keyword));
  }
}
