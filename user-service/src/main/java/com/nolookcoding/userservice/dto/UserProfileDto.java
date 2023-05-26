package com.nolookcoding.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDto {
    private Long index;
    private String userId;
    private String name;
    private String address;
    private String phone;
    private Integer age;
    private String sex;
    private String email;
}
