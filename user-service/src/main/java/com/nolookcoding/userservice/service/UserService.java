package com.nolookcoding.userservice.service;

import com.nolookcoding.userservice.domain.User;
import com.nolookcoding.userservice.dto.*;

public interface UserService {
    void join(User user);

    void update(Long userId, UserUpdateDto request);

    User findOne(Long id);

    User findByUserId(String userId);

    void delete(Long userId);

    String findUserId(UserGetIdDto userRequest);

    Boolean isDuplicateId(String inputId);

    void updatePassword(Long id, PasswordUpdateDto request);

    Boolean inputValidation(UserJoinDto userInput);

    User login(LoginDto loginInput);

    Long validateSession(String value);
}
