package com.afonddream.jsvc;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemindJob {

    @Scheduled(cron = "0/20 * * * * ?")
    public void printMsg() {
        System.out.println("printMsg");
        System.out.println("启动java服务测试");
        System.out.println("JSVC制作Linux服务");
    }
}


