package com.nolookcoding.userservice.controller;

import com.nolookcoding.userservice.domain.User;
import com.nolookcoding.userservice.dto.LoginDto;
import com.nolookcoding.userservice.dto.UserGetIdDto;
import com.nolookcoding.userservice.dto.UserJoinDto;
import com.nolookcoding.userservice.dto.UserUpdateDto;
import com.nolookcoding.userservice.service.UserService;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Boolean> duplicateIdCheck(@PathVariable String inputId) {
        return new ResponseEntity(userService.duplicateIdCheck(inputId), HttpStatus.OK);
    }

    @PostMapping({"/users/id"})
    public ResponseEntity<String> getUserId(@ModelAttribute UserGetIdDto request) {
        return new ResponseEntity(userService.findUserId(request), HttpStatus.OK);
    }

    @DeleteMapping({"/users/{userId}"})
    public ResponseEntity<Objects> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/users/password")
    public ResponseEntity<Objects> updatePassword(@RequestParam Long userId, @RequestParam String origin, @RequestParam String change) {
        userService.updatePassword(userId, origin, change);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/users/validation")
    public ResponseEntity<Boolean> inputValidation(@ModelAttribute UserJoinDto userInput) {
        return new ResponseEntity<Boolean>(userService.inputValidation(userInput), HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@ModelAttribute LoginDto loginInput) {
        User loginUser = userService.login(loginInput);
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }
}
