package com.hwamok.controller;

import com.hwamok.controller.dto.ChangeProfileDTO;
import com.hwamok.entity.User;
import com.hwamok.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/change-profile")
  public String changeProfile(HttpSession session, ChangeProfileDTO dto) {
    // 명시적 형변환
    User user = (User) session.getAttribute("user");

    User changedUser = userService.changeProfile(user.getEmail(), dto.getName(), dto.getPassword());

    session.setAttribute("user", changedUser);

    return "redirect:/my-page";
  }

  @GetMapping("/withdraw")
  public String withdraw(HttpSession session) {
    User user = (User) session.getAttribute("user");

    userService.withdraw(user.getEmail());

    session.invalidate();

    return "index";
  }

  // SignController , UserController

  // SignContoller : 회원가입(Insert), 로그인(Select), 로그아웃(Session)
  // UserController : 정보수정(Update), 탈퇴(Delete)
  // C(create) R(read) U D
  // 499만 --> 롤백 --> 0건
  // 회원등록 ==> 기존에는 엑셀정리(500만명) --> 엑셀을 업로드하면 그안 데이터 회원가입 시킴
  // 공지사항 게시판
  // C : 글쓰기(공지사항등록) --> 여러개 한번에 등록
  // R : 게시판 글 읽기
  // U : 게시판 수정 --> 한번에 수정
  // D : 게시판 삭제 --> 한번에 삭제

  // R은 대충 3가지로 나뉨
  // 단건조회, 리스트조회 List Collection, 페이징조회 Pageable(JPA)


}
