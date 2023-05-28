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

    @PostMapping({"/users"})
    public ResponseEntity<Object> join(@RequestBody UserJoinDto request) {
        userService.join(request.toUserEntity());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping({"/users/update"})
    public ResponseEntity<Object> userUpdate(@RequestBody UserUpdateDto userRequest) {
        Long id = userRequest.getId();
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.update(id, userRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping({"/users/validation/{inputId}"})
    public ResponseEntity<Object> isDuplicateId(@PathVariable String id) {
        if (userService.isDuplicateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/user-profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@RequestBody Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findOne(id);
        return new ResponseEntity<>(user.toUserProfile(), HttpStatus.OK);
    }

    @PostMapping({"/users/id"})
    public ResponseEntity<String> getUserId(@RequestBody UserGetIdDto request) {
        return new ResponseEntity<>(userService.findUserId(request), HttpStatus.OK);
    }

    @DeleteMapping({"/users/delete"})
    public ResponseEntity<Object> delete(@RequestBody Long idx) {
        if (idx == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.delete(idx);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/password")
    public ResponseEntity<Object> updatePassword(@RequestBody PasswordUpdateDto request) {
        Long id = request.getId();
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.updatePassword(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/validation")
    public ResponseEntity<Boolean> inputValidation(@RequestBody UserJoinDto userInput) {
        return new ResponseEntity<>(userService.inputValidation(userInput), HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ResponseEntity<Long> login(@RequestBody LoginDto loginInput) {
        User loginUser = userService.login(loginInput);
        if (loginUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(loginUser.getId(), HttpStatus.OK);
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

//    @PostMapping("/users/logout")
//    public ResponseEntity<Object> logout(@RequestBody Long idx) {
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @PostMapping("/users/session-check")
//    public ResponseEntity<Long> sessionCheck(@RequestHeader("JSESSIONID") String value) {
//        Long sessionRes = userService.validateSession(value);
//        // 세션 검증
//        if (sessionRes == null) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>(sessionRes, HttpStatus.OK);
//    }
}
