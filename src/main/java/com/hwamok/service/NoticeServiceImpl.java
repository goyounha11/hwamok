package com.hwamok.service;

import com.hwamok.controller.dto.NoticeCreateDTO;
import com.hwamok.entity.Notice;
import com.hwamok.entity.User;
import com.hwamok.repository.NoticeRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {
  private NoticeRepository noticeRepository;

  public NoticeServiceImpl(NoticeRepository noticeRepository) {
    this.noticeRepository = noticeRepository;
  }

  @Override
  public void createNotice(NoticeCreateDTO dto, User user) {
    noticeRepository.save(new Notice(dto.getTitle(), dto.getContent(), user.getId()));
  }

  @Override
  public Page<Notice> getNotices(String keyword, int curPage, int pageSize) {
    // 페이징이라는 것은 구조가 정해져있음
    // 페이징은 필수요소가 3가지 있음
    // 현재 내가 보고있는 페이지 (CurrentPage)
    // 한 페이지에 몇개 보여줄건지 (ItemPerPage)
    // 노티스의 총 갯수 (Total Count)

    // 페이징 쿼리가 어떻게 동작하나?
    // DB들마다 구현 방법이 조금씩 틀림 (Mysql, MariaDB, Postgresql 동일) , Mssql(윈도우 게임회사), Oracle(공공기관)(조금 틀림)

    // limit = 제한 - size
    // offset = 거리(distance), 간격 - page

    // 키워드 있을 때는 title과 본문에서 포함되는 모든 게시물을 검색해와야함
    // 키워드가 없을 때는 전부 다 가져오면댐

    PageRequest pageRequest = PageRequest.of(curPage - 1, pageSize);

    return noticeRepository.getNotices(keyword, pageRequest);
    // 페이징 처리가 된 객체는 List가 아니라 Page로 반환이 됨
  }

  @Override
  public Notice getOneNotice(Long id) {
    return noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("not found notice"));
  }

  @Override
  public Notice changeNotice(Long id, String title, String content) {
    Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("not found notice"));

    notice.change(title, content);

    return notice;
  }

  @Override
  public void deleteNotice(Long id) {
    Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("not found notice"));

    noticeRepository.delete(notice);
  }
}
