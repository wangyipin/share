package com.ty.share.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 定时任务演示
 * @author: 04637
 * @date: 2019/3/16
 **/
@Slf4j
@Component
public class ScheduledService {

    @Scheduled(cron = "0/5 * * * * *")
    public void scheduledFunc(){
        log.info("######## 定时任务 ########");
    }
}
