package com.nolookcoding.userservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {

    private Long userIdx;

    @Builder
    public UserRequestDto(Long userIdx) {
        this.userIdx = userIdx;
    }
}
