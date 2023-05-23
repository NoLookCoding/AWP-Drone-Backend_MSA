package com.nolookcoding.userservice.controller;

import com.nolookcoding.userservice.dto.UserGetIdDto;
import com.nolookcoding.userservice.dto.UserJoinDto;
import com.nolookcoding.userservice.dto.UserUpdateDto;
import com.nolookcoding.userservice.service.UserService;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping({"/users"})
    public ResponseEntity<Objects> join(@ModelAttribute UserJoinDto request) {
        userService.join(request.toUserEntity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping({"/users/{userId}"})
    public ResponseEntity<Objects> userUpdate(@PathVariable Long userId, @ModelAttribute UserUpdateDto userRequest) {
        userService.update(userId, userRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping({"/users/{inputId}"})
    public ResponseEntity<Boolean> duplicateCheck(@PathVariable String inputId) {
        return new ResponseEntity(userService.duplicateCheck(inputId), HttpStatus.OK);
    }

    @PostMapping({"/users/id"})
    public ResponseEntity<String> getUserId(UserGetIdDto request) {
        return new ResponseEntity(userService.findUserId(request), HttpStatus.OK);
    }

    @DeleteMapping({"/users/{userId}"})
    public ResponseEntity<Objects> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
