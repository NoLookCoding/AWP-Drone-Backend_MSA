package com.nolookcoding.userservice.service;

import com.nolookcoding.userservice.domain.User;
import com.nolookcoding.userservice.dto.LoginDto;
import com.nolookcoding.userservice.dto.UserGetIdDto;
import com.nolookcoding.userservice.dto.UserJoinDto;
import com.nolookcoding.userservice.dto.UserUpdateDto;
import com.nolookcoding.userservice.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public void join(User user) {
        System.out.println(user.getAddress());
        System.out.println(user.getName() + " " + user.getUserId() + " " + user.getEmail());
        this.userRepository.save(user);
    }

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

    public void delete(Long userId) {
        User user = this.findOne(userId);
        this.userRepository.delete(user);
    }

    public String findUserId(UserGetIdDto userRequest) {
        User user = userRepository.findByUserInput(userRequest.getName(), userRequest.getPhone(), userRequest.getEmail());
        return user.getUserId();
    }

    public Boolean duplicateIdCheck(String inputId) {
        Optional<User> user = userRepository.findByUserId(inputId);
        return user.isPresent();
    }

    @Override
    public void updatePassword(Long id, String origin, String change) {
        if (origin.equals(change)) {
            throw new IllegalArgumentException();
        }

        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
        user.updatePassword(change);
        userRepository.save(user);
    }

    @Override
    public Boolean inputValidation(UserJoinDto userInput) {
        String phoneRegex = "^\\d{2,3}\\d{3,4}\\d{4}$";
        String userIdRegex = "^[a-zA-Z0-9]*$";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$)$";
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

        if (!user.getPassword().equals(loginInput.getUserPassword())) {
            throw new IllegalArgumentException();
        }
        return user;
    }

}
