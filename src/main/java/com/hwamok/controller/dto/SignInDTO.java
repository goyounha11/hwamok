package com.hwamok.controller.dto;

public class SignInDTO {
  private String email;
  private String password;

  public SignInDTO(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
