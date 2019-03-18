package com.ty.share.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @description: 线程池演示
 * @author: 04637
 * @date: 2019/3/16
 **/
public class WhyConcurrent {

    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 20,
            30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private static Map<String, String> map2 = new HashMap<>();
    private static Map<String, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            String s = i + "";
            poolExecutor.execute(() ->
                    map.put(s, s)
            );
        }
        try {
            poolExecutor.shutdown();
            while (!poolExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("线程池仍未关闭");
            }
            System.out.println("size: " + map.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
