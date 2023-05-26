package com.nolookcoding.userservice.service;

import com.nolookcoding.userservice.domain.SessionManager;
import com.nolookcoding.userservice.domain.User;
import com.nolookcoding.userservice.dto.*;
import com.nolookcoding.userservice.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    @Transactional
    public void join(User user) {
        if (userRepository.findByUserId(user.getUserId()).isEmpty()) {
            this.userRepository.save(user);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void update(Long id, UserUpdateDto request) {
        User user = this.findOne(id);
        user.updateInfo(request);
        this.userRepository.save(user);
    }

    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new RuntimeException();
        });
    }

    @Transactional
    public void delete(Long userId) {
        User user = this.findOne(userId);
        this.userRepository.delete(user);
    }

    public String findUserId(UserGetIdDto userRequest) {
        User user = userRepository.findByUserInput(userRequest.getName(), userRequest.getPhone(), userRequest.getEmail());
        return user.getUserId();
    }

    public Boolean isDuplicateId(String inputId) {
        Optional<User> user = userRepository.findByUserId(inputId);
        return user.isPresent();
    }

    @Transactional
    @Override
    public void updatePassword(Long id, PasswordUpdateDto request) {
        if (request.getOrigin().equals(request.getChange())) {
            throw new IllegalArgumentException();
        }

        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
        user.updatePassword(request.getChange());
        userRepository.save(user);
    }

    @Override
    public Boolean inputValidation(UserJoinDto userInput) {
        String phoneRegex = "^\\d{2,3}\\d{3,4}\\d{4}$";
        String userIdRegex = "^[a-zA-Z0-9]*$";
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{1,}$";
        String emailRegex = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

        return userInput.getUserId().matches(userIdRegex) &&
                userInput.getPhone().matches(phoneRegex) &&
                userInput.getPassword().matches(passwordRegex) &&
                userInput.getEmail().matches(emailRegex);
    }

    @Override
    public User login(LoginDto loginInput) {
        User user = userRepository.findByUserId(loginInput.getUserId()).orElseThrow(() -> {
            throw new IllegalArgumentException();
        });

        if (!(user.getPassword().equals(loginInput.getUserPassword()))) {
            return null;
        }
        return user;
    }

    @Override
    public Long validateSession(String value) {
        return sessionManager.getSession(value);
    }

}
