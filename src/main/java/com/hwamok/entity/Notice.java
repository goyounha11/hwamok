package com.hwamok.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;
  private Long userId;

  public Notice() {
  }

  public Notice(Long id, String title, String content, Long userId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.userId = userId;
  }

  public Notice(String title, String content, Long userId) {
    this.title = title;
    this.content = content;
    this.userId = userId;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public Long getUserId() {
    return userId;
  }

  public void change(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
