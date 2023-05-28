package com.nolookcoding.userservice.dto;

import lombok.Data;

@Data
public class PasswordUpdateDto {
    private Long idx;
    private String origin;
    private String change;
}
