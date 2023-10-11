package com.hwamok.service;

import com.hwamok.controller.dto.NoticeCreateDTO;
import com.hwamok.entity.Notice;
import com.hwamok.entity.User;
import org.springframework.data.domain.Page;

public interface NoticeService {
  void createNotice(NoticeCreateDTO dto, User user);

  Page<Notice> getNotices(String keyword, int curPage, int pageSize);

  Notice getOneNotice(Long id);

  Notice changeNotice(Long id, String title, String content);

  void deleteNotice(Long id);
}
