package com.hwamok.controller;

import com.hwamok.entity.Notice;
import com.hwamok.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RouteController {

  private NoticeService noticeService;

  public RouteController(NoticeService noticeService) {
    this.noticeService = noticeService;
  }

  @GetMapping("/ui-buttons")
  public String uiButtonsPage() {
    return "ui-buttons";
  }

  @GetMapping("/ui-alerts")
  public String uiAlertsPage() {
    return "ui-alerts";
  }

  @GetMapping("/ui-card")
  public String uiCardPage() {
    return "ui-card";
  }

  @GetMapping("/ui-forms")
  public String uiFormsPage() {
    return "ui-forms";
  }

  @GetMapping("/ui-typography")
  public String uiTypographyPage() {
    return "ui-typography";
  }

  @GetMapping("/notice")
  public String getNoticePage() {
    // 현재 내가 보고있는 페이지 (CurrentPage)
    // 한 페이지에 몇개 보여줄건지 (ItemPerPage)
    // 노티스의 총 갯수 (Total Count)
    return "notice";
  }
  @GetMapping("/notice/write")
  public String uiNoticeWritePage() {
    return "noticeWrite";
  }

  @GetMapping("/sign-in")
  public String signInPage() {
    return "sign-in";
  }

  @GetMapping("/sign-up")
  public String signUpPage() {
    return "sign-up";
  }

  @GetMapping("/my-page")
  public String myPage() {
    return "ui-mypage";
  }
}
