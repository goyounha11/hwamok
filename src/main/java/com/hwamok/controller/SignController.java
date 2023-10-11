package com.hwamok.controller;

import com.hwamok.controller.dto.SignInDTO;
import com.hwamok.controller.dto.UserCreateDTO;
import com.hwamok.entity.User;
import com.hwamok.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

@Controller
public class SignController {

  private SignService signService;

  public SignController(SignService signService) {
    this.signService = signService;
  }

  @PostMapping(value = "/sign-up")
  public String signUp(UserCreateDTO dto) {
    // Spring은 Message Converter가 있어서 기본적으로 앞에 패스 붙히고 뒤에 .html
    // JSON으로 요청 응답을 주고받고함 --> Jackson Message Converter로 바꿔줘야 함
    // Spring의 모든 요청과 응답은 기본적으로 동기통신
    // Json으로 보내는 요청은 비동기 통신
    signService.signUp(dto);

    return "sign-up";
  }

  // 로그인 구현
  // 로그인은 조회 생성 수정 삭제
  // HTTP Method : POST
  @PostMapping("/sign-in")
  public String signIn(SignInDTO dto, HttpSession session) {
    System.out.println(dto.getEmail());
    System.out.println(dto.getPassword());
    // @ReuqestParam => QueryString을 받겠다
    // email, password
    User user = signService.signIn(dto.getEmail(), dto.getPassword());

    session.setAttribute("user", user);
//    session.setMaxInactiveInterval(60);// 세션 유효시간(초)

    // SOLID원칙 (OOP의 설계 원칙)
    // OOP로 좋은 설계를 한다 ==> SOLID원칙을 잘 지켰다.

    // 객체는 한가지의 일만 수행할 책임이 있습니다.
    // 단일 책임의 원칙 (Single Reponsibility Principal) - S
    // 개방 폐쇄의 원칙 (Open Close Principal) - O
    // 변경에는 폐쇄적이고 사용하는 곳에서는 유연하게 사용해야한다. - Interface

    // 디미터의 법칙 ==> 최소한의 정보만을 알아야한다.

    // 사이드이펙트 ==> 수정한 기능과 관계없는 다른 기능에서 오류나는 현상

    // if는 Lazy연산 ==> 게으른연산
    // Lazy => 게으르다

    // 로그인을 유지하는데는 크게 3가지 방법이 있음
    // 세션 ==> 서버에 있는 가상의 공간 서버가 죽으면 세션도 사라짐 접근이 불가능 서버 혹은 템플릿엔진만 접근 가능
    // 쿠키 ==> 사용자컴터에 저장해둠 보안상 취약 => 로그인 정보 기억하기
    // jwt ==> 나중에

    return "index";
  }

  @GetMapping("/sign-out")
  public String signOut(HttpSession session) {
    session.invalidate(); // 세션 자체를 무효화

    return "index";
  }
}
