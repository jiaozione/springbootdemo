package com.dz.springboard.service.impl;

import com.dz.springboard.entity.User;
import com.dz.springboard.mapper.UserMapper;
import com.dz.springboard.service.IUserService;
import com.dz.springboard.service.ex.InsertException;
import com.dz.springboard.service.ex.PasswordNotMatchException;
import com.dz.springboard.service.ex.UsernameDuplicateException;
import com.dz.springboard.service.ex.UsernameNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**用户模块业务层的实现类*/
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;


    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if (result != null) {
            throw new UsernameDuplicateException("尝试注册的用户名[" + username + "]已经被占用");
        }
        String password = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPassword = encoder.encode(password);
        user.setUsername(username);
        user.setPassword(newPassword);

        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        Integer rows = userMapper.insert(user);
        if (rows != 1){
            throw new InsertException("添加用户数据出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        if (result == null){
            throw new UsernameNotFoundException("UserNameNull");
        }

        String oldPassword = result.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(password, oldPassword);
        if (!encoder.matches(password, oldPassword)){
            throw new PasswordNotMatchException("PasswordNotMatch");
        }
        if (result.getIsDelete() == 1){
            throw new UsernameNotFoundException("UserNameDel");
        }
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }
}


















