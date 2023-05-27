package com.nolookcoding.userservice.controller;

import com.nolookcoding.userservice.domain.SessionConst;
import com.nolookcoding.userservice.domain.SessionManager;
import com.nolookcoding.userservice.domain.User;
import com.nolookcoding.userservice.dto.*;
import com.nolookcoding.userservice.service.UserService;
import java.util.Objects;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SessionManager sessionManager;

    @PostMapping({"/users"})
    public ResponseEntity<Object> join(@RequestBody UserJoinDto request) {
        userService.join(request.toUserEntity());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping({"/users/update"})
    public ResponseEntity<Object> userUpdate(@RequestHeader("JSESSIONID") String value, @RequestBody UserUpdateDto userRequest) {
        Long userIndex = sessionManager.getSession(value);
        if (userIndex == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.update(userIndex, userRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping({"/users/validation/{inputId}"})
    public ResponseEntity<Object> isDuplicateId(@PathVariable String inputId) {
        if (userService.isDuplicateId(inputId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/user-profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@RequestHeader("JSESSIONID") String value) {
        Long userIndex = sessionManager.getSession(value);
        if (userIndex == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findOne(userIndex);
        return new ResponseEntity<>(user.toUserProfile(), HttpStatus.OK);
    }

    @PostMapping({"/users/id"})
    public ResponseEntity<String> getUserId(@RequestBody UserGetIdDto request) {
        return new ResponseEntity<>(userService.findUserId(request), HttpStatus.OK);
    }

    @DeleteMapping({"/users/delete"})
    public ResponseEntity<Object> delete(@RequestHeader("JSESSIONID") String value) {
        Long userIndex = sessionManager.getSession(value);
        if (userIndex == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.delete(userIndex);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/password")
    public ResponseEntity<Object> updatePassword(@RequestHeader("JSESSIONID") String value,
                                                  @RequestBody PasswordUpdateDto request) {
        Long userIndex = sessionManager.getSession(value);
        if (userIndex == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.updatePassword(userIndex, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/validation")
    public ResponseEntity<Boolean> inputValidation(@RequestBody UserJoinDto userInput) {
        return new ResponseEntity<>(userService.inputValidation(userInput), HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginInput) {
        User loginUser = userService.login(loginInput);

        if (loginUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.SET_COOKIE, SessionConst.sessionId + "=" + sessionManager.createSession(loginUser));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

//    @GetMapping("/")
//    public ResponseEntity<User> home(HttpServletRequest request) {
//        String userId = sessionManager.getSession(request);
//
//        // 유효한 쿠키를 찾지 못하면 null 리턴
//        if (userId == null) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//
//        // findUser에서 터질 수 있음
//        User findUser = userService.findByUserId(userId);
//        if (findUser != null) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//    }

    @PostMapping("/users/logout")
    public ResponseEntity<Object> logout(@RequestHeader("JSESSIONID") String value) {
        sessionManager.sessionExpire(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/session-check")
    public ResponseEntity<Long> sessionCheck(@RequestHeader("JSESSIONID") String value) {
        Long sessionRes = userService.validateSession(value);
        // 세션 검증
        if (sessionRes == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(sessionRes, HttpStatus.OK);
    }
}
