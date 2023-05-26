package com.nolookcoding.userservice.dto;

import lombok.Data;

@Data
public class PasswordUpdateDto {
    private String origin;
    private String change;
}
