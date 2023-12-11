package com.example.backend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.example.backend.pojo.ApiResult;
import com.example.backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@SaCheckLogin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @SaIgnore
    @PostMapping("/register")
    public ApiResult registerUser(@RequestParam boolean useEmail,
                                  @RequestParam String emailOrPhone,
                                  @RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String code) {
        return userService.registerUser(useEmail, emailOrPhone, username, password, code);
    }

    @SaIgnore
    @PostMapping("/login")
    public ApiResult loginWithPassword(@RequestParam String emailOrPhone,
                                       @RequestParam String password) {
        return userService.loginWithPassword(emailOrPhone, password);
    }

    @PostMapping("/logout")
    public ApiResult logout() {
        return userService.logout();
    }

    @GetMapping("/profile")
    public ApiResult getUserProfile() {
        return userService.getUserProfile();
    }

    @PostMapping("/username")
    public ApiResult updateUsername(@RequestParam String username) {
        return userService.updateUsername(username);
    }

    @PostMapping("/avatar")
    public ApiResult uploadAvatar(@RequestPart MultipartFile avatar, HttpServletResponse httpServletResponse) throws IOException {
        return userService.uploadAvatar(avatar, httpServletResponse);
    }

    @GetMapping("/avatar/{fileName}")
    public void getAvatar(@PathVariable String fileName, HttpServletResponse httpServletResponse) {
        userService.getAvatar(fileName, httpServletResponse);
    }

}
