package com.ty.share.mailutil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 触发异常
 * @author: 04637@163.com
 * @date: 2019/3/18
 */
@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/exception.do")
    public void exception() {
        throw new RuntimeException();
    }

    @RequestMapping("/myException.do")
    public void myException(){
        throw new MyException();
    }
}
