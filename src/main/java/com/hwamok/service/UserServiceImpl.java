package com.hwamok.service;

import com.hwamok.entity.User;
import com.hwamok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  private PlatformTransactionManager platformTransactionManager;

  public UserServiceImpl(UserRepository userRepository, PlatformTransactionManager platformTransactionManager) {
    this.userRepository = userRepository;
    this.platformTransactionManager = platformTransactionManager;
  }

  @Override
  public User changeProfile(String email, String name, String password) {
    TransactionStatus status = platformTransactionManager.getTransaction(new DefaultTransactionAttribute());
    // 이름을 바꾸는거로 수정해보자
    // 패스워드 바꾸기

    // 트랜잭션 거는법
    // 1. PSA가적용된 방식
    // 2. 직접 구현하기 v

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("user not found"));

    try {
      // 방어 로직 설계
      if(password.isBlank()) {
        throw new RuntimeException("invalidate password");
      }

      if(name.isBlank()) {
        throw new RuntimeException("invalidate name");
      }

      user.changeName(name);
      user.changePassword(password);

      platformTransactionManager.commit(status);
    }catch (RuntimeException e) {
      e.printStackTrace();
      platformTransactionManager.rollback(status);
    }
    return user;

    // update user set name = ? --> 성공
    // update user set password = ? --> 실패

    // 둘 다 실패되게
    // 하나의 트랜잭션으로 묶는다.

    // 트랜잭션이란?
    // 여러개의 작업을 하나의 작업으로 묶어서 처리
    // A B C 3가지의 작업
    // 3가지의 작업이 다 성공해야 정상적인 반영이됨 ==> 전부(작업) 성공 ==> Commit
    // 3가지 중 하나라도 실패한다면 전부(작업) 실패처리 => Rollback

    // update user set name = ? and password = ?
  }

  @Override
  public void withdraw(String email) {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("user not found"));

    userRepository.delete(user);
  }
}
