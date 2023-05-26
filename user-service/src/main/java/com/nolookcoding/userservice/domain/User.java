package com.nolookcoding.userservice.domain;

import com.nolookcoding.userservice.dto.UserProfileDto;
import com.nolookcoding.userservice.dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USER_TB")
@NoArgsConstructor
public class User {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;
    @Column(nullable = false)
    private String dataState;
    @Column(nullable = false, name = "USER_ID")
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String createdAt;
    @Column
    private String updatedAt;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private Integer age;
    @Column
    private String sex;
    @Column
    private String email;

    @Builder
    public User(String dataState, String userId, String password, String createdAt, String updatedAt, String name, String address, String phone, Integer age, String sex, String email) {
        this.dataState = dataState;
        this.userId = userId;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.sex = sex;
        this.email = email;
    }

    public UserProfileDto toUserProfile() {
        return UserProfileDto.builder()
                .index(this.index)
                .userId(this.userId)
                .name(this.name)
                .address(this.address)
                .phone(this.phone)
                .age(this.age)
                .sex(this.sex)
                .email(this.email)
                .build();
    }

    public void updateInfo(UserUpdateDto request) {
        this.address = request.getAddress();
        this.phone = request.getPhone();

        if (request.getEmail() != null) {
            this.email = request.getEmail();
        }
    }
    public void updateAddress(String reqAddress) {
        this.address = reqAddress;
    }

    public void updateEmail(String reqEmail) {
        this.email = reqEmail;
    }

    public void updatePhone(String reqPhone) {
        this.phone = reqPhone;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
