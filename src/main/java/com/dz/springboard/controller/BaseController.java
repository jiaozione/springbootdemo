package com.dz.springboard.controller;

import com.dz.springboard.controller.ex.*;
import com.dz.springboard.service.ex.*;
import com.dz.springboard.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**  */
public class BaseController {
    public static final int OK = 200;
    public static final int USERNAME_DUPLICATE = 4000;
    public static final int INSERT_FAIL = 5000;

    public static final String USERNAME_DUPLICATE_MSG = "用户名已经被占用";
    public static final String INSERT_FAIL_MSG = "注册失败，请联系系统管理员";


    @ExceptionHandler({ServiceException.class,FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicateException){
            result.setState(USERNAME_DUPLICATE);
            result.setMessage(USERNAME_DUPLICATE_MSG);
        } else if(e instanceof UsernameNotFoundException){
            result.setState(5001);
            result.setMessage("用户不存在");
        } else if(e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("密码错误");
        } else if(e instanceof InsertException){
            result.setState(INSERT_FAIL);
            result.setMessage(INSERT_FAIL_MSG);
        } else if(e instanceof UpdateException){
            result.setState(5001);
            result.setMessage("UpdateException");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }

    protected final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
