package com.ty.share.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 线程池演示
 * @author: 04637
 * @date: 2019/3/16
 **/
public class ThreadPool {
    // TODO: 2019/3/16 阻塞队列
    private static ExecutorService executor = Executors.newFixedThreadPool(20);
    private static Map<String, String> map = new HashMap<>();
    private static Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            String s = i + "";
            executor.execute(() -> map.put(s, s));
            System.out.println(i);
        }
        try {
            executor.shutdown();
            while(!executor.awaitTermination(1, TimeUnit.MINUTES)){
                System.out.println("线程池仍未关闭");
            }
            System.out.println("size: " + map.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
