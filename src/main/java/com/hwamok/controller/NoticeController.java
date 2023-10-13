package com.hwamok.controller;

import com.hwamok.controller.dto.NoticeChangeDTO;
import com.hwamok.controller.dto.NoticeCreateDTO;
import com.hwamok.entity.Notice;
import com.hwamok.entity.User;
import com.hwamok.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class NoticeController {

  private NoticeService noticeService;

  public NoticeController(NoticeService noticeService) {
    this.noticeService = noticeService;
  }

  @GetMapping("/notices")
  @ResponseBody
  public Page<Notice> getNotices(@RequestParam(required = false) String keyword,
                                 @RequestParam(defaultValue = "1") int curPage,
                                 @RequestParam(defaultValue = "2") int pageSize) {
    return noticeService.getNotices(keyword, curPage, pageSize);
  }


  @PostMapping("/notice")
  public void createNotice(NoticeCreateDTO dto, HttpSession session) {
    User user = (User) session.getAttribute("user");

    noticeService.createNotice(dto, user);
  }

  // 하나의 노티스를 가져오는 방법이
  // GET을 써야할 것 같음
  // 키 값 (id)을 알아야함
  // 주소에 notice의 id가 1번인 노티스 가져다줘
  // QueryString => ?noticeId=1
  // {id} => PathVariable
  @GetMapping("/notice/{id}")
  public String getOneNotice(@PathVariable Long id, Model model) {
    Notice notice = noticeService.getOneNotice(id);
    model.addAttribute("notice", notice);
    return "noticeDetail";
  }

  @PostMapping("/change-notice")
  public String changeNotice(NoticeChangeDTO dto, HttpSession session) {
//    User user = (User) session.getAttribute("user");

    Notice notice = noticeService.changeNotice(dto.getId(), dto.getTitle(), dto.getContent());
    return "noticeDetail";
  }

  @GetMapping("/delete-notice/{id}")
  public String deleteNotice(@PathVariable Long id) {
    noticeService.deleteNotice(id);
    return "notice";
  }

  // 숙제로 수정 삭제
  // 마이페이지 수정하면 그대로인거 숙제

  // 수정을 하기 위해서는 1개를 먼저 조회


  // 수정하기위해서 필요한게 무엇일까?
  // 메인 들어갈때 List로 노티스 출력하기
  // 주소쳐서들어가기 (0)
  // List로 일단 출력 후 수정 클릭하기 (v)
  // 하드 코딩으로 넣어버리기
}
