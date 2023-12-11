package com.example.backend.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileTypeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.constant.Status;
import com.example.backend.exception.BaseException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.ApiResult;
import com.example.backend.pojo.User;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    File pathTest = new File(System.getProperty("user.dir") + "/backend/src/main/resources/static/avatar/");
//        File pathTest = new File(System.getProperty("user.dir") + "/static/avatar/");

    private User getUserByEmailOrPhone(String emailOrPhone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", emailOrPhone).or().eq("phone_number", emailOrPhone);
        return userMapper.selectOne(queryWrapper);
    }

    public ApiResult registerUser(boolean useEmail, String emailOrPhone, String username, String password, String code) {
        // TODO check code
        if (getUserByEmailOrPhone(emailOrPhone) != null) {
            throw new BaseException(Status.USER_EXISTS);
        }
        password = DigestUtils.sha256Hex(password);
        User user = new User(null, username, useEmail ? emailOrPhone : null, useEmail ? null : emailOrPhone, password, null, null);

        if (userMapper.insert(user) > 0) {
            StpUtil.login(user.getId());
            return ApiResult.ofSuccess(userMapper.selectById(user.getId()));
        }
        throw new BaseException(Status.UNKNOWN_ERROR);
    }

    public ApiResult loginWithPassword(String emailOrPhone, String password) {
        if (StpUtil.isLogin()) {
            throw new BaseException(Status.ALREADY_LOGGED_IN);
        }

        User user = getUserByEmailOrPhone(emailOrPhone);
        if (user == null) {
            throw new BaseException(Status.USER_NOT_EXISTS);
        }
        if (user.getPassword().equals(DigestUtils.sha256Hex(password))) {
            StpUtil.login(user.getId());
            return ApiResult.ofSuccess(userMapper.selectById(user.getId()));
        }
        throw new BaseException(Status.WRONG_PASSWORD);
    }

    public ApiResult logout() {
        StpUtil.logout();
        return ApiResult.ofStatus(Status.OK);
    }

    public ApiResult getUserProfile() {
        User user = userMapper.selectById(StpUtil.getLoginIdAsLong());
        if (user.getAvatarUrl() != null) {
            user.setAvatarUrl("http://localhost:8080/api/user/avatar/" + user.getAvatarUrl());
        }
        return ApiResult.ofSuccess(user);
    }

    public ApiResult updateUsername(String username) {
        username = username.replace(" ", "");
        if (username.isEmpty()) {
            throw new BaseException(Status.USERNAME_EMPTY);
        } else if (username.length() > 20) {
            throw new BaseException(Status.USERNAME_TOO_LONG);
        }

        long id = StpUtil.getLoginIdAsLong();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("username", username)
                .eq("id", id);
        userMapper.update(null, updateWrapper);
        return ApiResult.ofSuccess(userMapper.selectById(id));
    }

    public ApiResult uploadAvatar(MultipartFile file, HttpServletResponse httpServletResponse) throws IOException {
        if (file.isEmpty()) {
            httpServletResponse.setStatus(500);
            throw new BaseException(Status.UNKNOWN_ERROR);
        }

        String fileType = FileTypeUtil.getType(file.getInputStream());
        if (!fileType.equalsIgnoreCase("jpg") &&
                !fileType.equalsIgnoreCase("png") &&
                !fileType.equalsIgnoreCase("gif")) {
            httpServletResponse.setStatus(500);
            throw new BaseException(Status.UNKNOWN_ERROR);
        }

        User user = userMapper.selectById(StpUtil.getLoginIdAsLong());

        byte[] bytes = file.getBytes();
        String fileName = DigestUtils.md5Hex(bytes) + user.getId().toString() + "." + fileType;

        file.transferTo(new File(Path.of(pathTest.getAbsolutePath(), fileName).toString()));

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("avatar_url", fileName)
                .eq("id", user.getId());
        userMapper.update(null, updateWrapper);

        if (!user.getAvatarUrl().equals(fileName)) {
            new File(Path.of(pathTest.getAbsolutePath(), user.getAvatarUrl()).toString()).delete();
        }

        return ApiResult.ofStatus(Status.OK);
    }

    public void getAvatar(String fileName, HttpServletResponse httpServletResponse) {
        try (InputStream inputStream = new FileInputStream(Path.of(pathTest.getAbsolutePath(), fileName).toString())) {
            httpServletResponse.setContentType("image/jpg");

            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException exception) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
        }
    }

}
