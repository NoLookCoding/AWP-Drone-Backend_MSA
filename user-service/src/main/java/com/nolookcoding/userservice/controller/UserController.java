package com.nolookcoding.userservice.controller;

import com.nolookcoding.userservice.domain.SessionConst;
import com.nolookcoding.userservice.domain.SessionManager;
import com.nolookcoding.userservice.domain.User;
import com.nolookcoding.userservice.dto.LoginDto;
import com.nolookcoding.userservice.dto.UserGetIdDto;
import com.nolookcoding.userservice.dto.UserJoinDto;
import com.nolookcoding.userservice.dto.UserUpdateDto;
import com.nolookcoding.userservice.service.UserService;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SessionManager sessionManager;

    @PostMapping({"/users"})
    public ResponseEntity<Objects> join(@RequestBody UserJoinDto request) {
        userService.join(request.toUserEntity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping({"/users/{userId}"})
    public ResponseEntity<Objects> userUpdate(@PathVariable Long userId, @RequestBody UserUpdateDto userRequest) {
        userService.update(userId, userRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping({"/users/{inputId}"})
    public ResponseEntity<Boolean> duplicateIdCheck(@PathVariable String inputId) {
        return new ResponseEntity(userService.duplicateIdCheck(inputId), HttpStatus.OK);
    }

    @PostMapping({"/users/id"})
    public ResponseEntity<String> getUserId(@RequestBody UserGetIdDto request) {
        return new ResponseEntity(userService.findUserId(request), HttpStatus.OK);
    }

    @DeleteMapping({"/users/{userId}"})
    public ResponseEntity<Objects> delete(@RequestParam Long userId) {
        userService.delete(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/users/password")
    public ResponseEntity<Objects> updatePassword(@RequestParam Long userId, @RequestParam String origin, @RequestParam String change) {
        userService.updatePassword(userId, origin, change);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/users/validation")
    public ResponseEntity<Boolean> inputValidation(@RequestBody UserJoinDto userInput) {
        return new ResponseEntity<Boolean>(userService.inputValidation(userInput), HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginInput, HttpServletRequest request) {
        User loginUser = userService.login(loginInput);

        if (loginUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.sessionId, loginUser.getUserId());
        }

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
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

    @PostMapping("logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        sessionManager.sessionExpire(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
