package com.ty.share.mailutil;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 * @description: 异常统一处理
 * @author: 04637@163.com
 * @date: 2019/3/18
 */

@RestControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){
        MailUtils.sendHtmlMail("04637@163.com", "Exception", Arrays.toString(e.getStackTrace()));
        return "handleException";
    }

    @ExceptionHandler(MyException.class)
    public String handleMyException(MyException e){
        MailUtils.sendHtmlMail("04637@163.com", "MyException", Arrays.toString(e.getStackTrace()));
        return "handleMyException";
    }
}
