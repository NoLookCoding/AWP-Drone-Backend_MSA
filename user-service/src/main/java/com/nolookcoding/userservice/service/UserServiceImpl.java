package com.nolookcoding.userservice.service;

import com.nolookcoding.userservice.domain.User;
import com.nolookcoding.userservice.dto.UserGetIdDto;
import com.nolookcoding.userservice.dto.UserUpdateDto;
import com.nolookcoding.userservice.repository.UserRepository;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public void join(User user) {
        System.out.println(user.getAddress());
        this.userRepository.save(user);
    }

    public void update(Long id, UserUpdateDto request) {
        User user = this.findOne(id);
        if (request.getAddress() != null) {
            user.updateAddress(request.getAddress());
        }

        if (request.getEmail() != null) {
            user.updateEmail(request.getEmail());
        }

        if (request.getPhone() != null) {
            user.updatePhone(request.getPhone());
        }

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

    public Boolean duplicateCheck(String inputId) {
        Optional<User> user = userRepository.findByUserId(inputId);
        return user.isPresent();
    }

}
