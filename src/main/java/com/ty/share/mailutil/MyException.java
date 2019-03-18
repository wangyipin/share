package com.ty.share.mailutil;

/**
 * @description: 自定义异常
 * @author: 04637@163.com
 * @date: 2019/3/18
 */
public class MyException extends RuntimeException{

    public MyException(String msg){
        super(msg);
    }

    public MyException(){}
}
