package com.nolookcoding.userservice.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String address;
    private String phone;
    private String email;

}
