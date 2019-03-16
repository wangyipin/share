package com.ty.share.logger;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


/**
 * @description: 日志级别演示
 * @author: 04637
 * @date: 2019/3/16
 **/
@Slf4j
@RestController
@RequestMapping("/logger")
public class LoggerTest {

    @RequestMapping("/all.do")
    public String all(){
        LoggerFactory.getLogger(LoggerTest.class);
        log.trace("!!!!!!! TRACE !!!!!!!");
        log.debug("!!!!!!! DEBUG !!!!!!!");
        log.info("!!!!!!! INFO !!!!!!!");
        log.warn("!!!!!!! WARN !!!!!!!");
        log.error("!!!!!!! ERROR !!!!!!!");
        return "all";
    }

    @RequestMapping("/debug.do")
    public String debug(){
        log.debug("!!!!!!! DEBUG !!!!!!!");
        log.debug("!!!!!!! DEBUG !!!!!!!");
        log.debug("!!!!!!! DEBUG !!!!!!!");
        return "debug";
    }

    @RequestMapping("/error.do")
    public String error(){
        log.error("!!!!!!! ERROR !!!!!!!");
        log.error("!!!!!!! ERROR !!!!!!!");
        log.error("!!!!!!! ERROR !!!!!!!");
        return "error";
    }

}
