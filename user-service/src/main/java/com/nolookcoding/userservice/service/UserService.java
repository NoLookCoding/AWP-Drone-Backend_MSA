package com.nolookcoding.userservice.service;

import com.nolookcoding.userservice.domain.User;
import com.nolookcoding.userservice.dto.LoginDto;
import com.nolookcoding.userservice.dto.UserGetIdDto;
import com.nolookcoding.userservice.dto.UserJoinDto;
import com.nolookcoding.userservice.dto.UserUpdateDto;

public interface UserService {
    void join(User user);

    void update(Long userId, UserUpdateDto request);

    User findOne(Long id);

    User findByUserId(String userId);

    void delete(Long userId);

    String findUserId(UserGetIdDto userRequest);

    Boolean duplicateIdCheck(String inputId);

    void updatePassword(Long id, String origin, String change);

    Boolean inputValidation(UserJoinDto userInput);

    User login(LoginDto loginInput);
}
