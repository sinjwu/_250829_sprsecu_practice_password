package com.example._250829_sprsecu_practice_password.service;

import com.example._250829_sprsecu_practice_password.model.User;
import com.example._250829_sprsecu_practice_password.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User registerUser(String username, String rawPassword, String email) {
        if (userRepository.existByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + username);
        }
        if (userRepository.existByEmail(email)) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다: " + email);
        }
    }
    private void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("패스워드는 최소 8자 이상어야 합니다");
        }
        if(!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("패스워드는 대소문자, 숫자, 특수문자를 모두 포함해야 합니다");
        }
    }
}
