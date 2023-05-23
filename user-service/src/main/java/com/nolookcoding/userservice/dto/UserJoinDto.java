package com.nolookcoding.userservice.dto;

import com.nolookcoding.userservice.domain.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserJoinDto {
    private String userId;
    private String password;
    private String name;
    private String address;
    private String phone;
    private Integer age;
    private String email;
    private String sex;

    public User toUserEntity() {
        return User.builder()
                .dataState("VALID")
                .userId(this.userId)
                .password(this.password)
                .createdAt((new Timestamp(System.currentTimeMillis())).toString())
                .updatedAt(null)
                .name(this.name)
                .address(this.address)
                .phone(this.phone)
                .age(this.age)
                .sex(this.sex).email(this.email).build();
    }

    UserJoinDto(final String userId, final String password, final String name, final String address, final String phone, final Integer age, final String email, final String sex) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.email = email;
        this.sex = sex;
    }

}
