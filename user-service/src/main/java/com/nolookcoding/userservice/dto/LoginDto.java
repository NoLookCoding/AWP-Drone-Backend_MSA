package com.nolookcoding.userservice.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String userId;
    private String userPassword;
}
