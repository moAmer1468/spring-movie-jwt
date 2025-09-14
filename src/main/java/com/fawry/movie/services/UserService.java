package com.fawry.movie.services;

import com.fawry.movie.entities.User;
import com.fawry.movie.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // تأكد إنك عرفّت bean ليه في SecurityConfig

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // جلب كل اليوزرز
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // جلب يوزر بالـ id
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // جلب يوزر بالـ username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // إضافة مستخدم جديد (نتأكد من عدم تكرار username و نعمل تشفير للـ password)
    public User addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        // here you could add the password to the database without  encryption
        // تشفير الباسورد قبل الحفظ
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // تحديث مستخدم (مصحح — من غير email لأن مش موجود)
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    // لو عايز تغيّر الـ username — نتأكد إنه مش متكرر على يوزر تاني
                    String newUsername = updatedUser.getUsername();
                    if (newUsername != null && !newUsername.equals(user.getUsername())) {
                        userRepository.findByUsername(newUsername).ifPresent(u -> {
                            throw new RuntimeException("Username already exists");
                        });
                        user.setUsername(newUsername);
                    }

                    // لو فيه باسورد جديدة — نشفرها ونخزن
                    String newPassword = updatedUser.getPassword();
                    if (newPassword != null && !newPassword.isBlank()) {
                        user.setPassword(passwordEncoder.encode(newPassword));
                    }

                    // تحديث الـ role لو موجود
                    if (updatedUser.getRole() != null) {
                        user.setRole(updatedUser.getRole());
                    }

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    // حذف يوزر
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }
}
