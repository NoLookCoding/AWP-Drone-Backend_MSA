package com.nolookcoding.userservice.controller;

import com.nolookcoding.userservice.domain.User;
import com.nolookcoding.userservice.dto.*;
import com.nolookcoding.userservice.service.UserService;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Long idx = userRequest.getId();
        if (idx == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.update(idx, userRequest);
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
    public ResponseEntity<UserProfileDto> getUserProfile(@RequestBody Map<String, Long> map) {
        Long idx = map.get("userIdx");
        if (idx == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findOne(idx);
        return new ResponseEntity<>(user.toUserProfile(), HttpStatus.OK);
    }

    @PostMapping({"/users/id"})
    public ResponseEntity<String> getUserId(@RequestBody UserGetIdDto request) {
        return new ResponseEntity<>(userService.findUserId(request), HttpStatus.OK);
    }

    @DeleteMapping({"/users/delete"})
    public ResponseEntity<Object> delete(@RequestBody Map<String, Long> map) {
        Long idx = map.get("userIdx");
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
    public ResponseEntity<Map> login(@RequestBody LoginDto loginInput) {
        User loginUser = userService.login(loginInput);
        if (loginUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Map<String, Long> map = new HashMap<>();
        map.put("userIdx", loginUser.getId());
        return new ResponseEntity<>(map, HttpStatus.OK);
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
