package com.nolookcoding.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserUpdateDto {
    private Long id;
    private String address;
    private String phone;
    private String email;
}
