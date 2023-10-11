package com.hwamok.repository;

import com.hwamok.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignRepository extends JpaRepository<User, Long> {
  // T (parameter generic) --> Entity를 파라미터로써 전달
  // E (entity generic)

  // email로 조회를 하는 기능을 만들려고함
  // SELECT * FROM User WHERE User.email = ?
  // SELECT * FROM User WHERE User.email IN (?,?,?)
  // SELECT * FROM User WHERE User.email = ?
  // Order by User.id desc(내림), (asc 오름)

  // JPA에서 쿼리대신 QueryMethod라는 것을 많이씀
  // 조회 : find
  // 조건이 붙을땐 By(조건컬럼)
  // and를 붙일땐 And
  // or를 붙일땐 Or
  // In을 붙일땐 In
  // Order by ?? desc 붙힐땐 OrderBy(조건컬럼)Desc

  // 가독성을 해칠 수 있음
  // 조회성능이 극혐이라 조건이 많이 붙는 조회시에는
  // QueryDsl(저희가 쓸것), JOOQ
  // findByEmail 우리가 찾고자 하는게 뭘까?
  // User 객체가 필요
  // User가 없을 수도있지 않을까?
  // 없을 수도있다 ==> null일 수 있다.
  // Java에서는 Nullable을 Optional<T>


  // SELECT * FROM User WHERE User.email = ?
  Optional<User> findByEmail(String email);
}
