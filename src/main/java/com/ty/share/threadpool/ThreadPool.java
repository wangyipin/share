package com.ty.share.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @description: 线程池演示
 * @author: 04637
 * @date: 2019/3/16
 **/
public class ThreadPool {
    // todo 1.为什么不使用 Executors.newFixedThreadPool(10);
    /**
     * 不推荐使用此方法创建线程池
     *
     * @see java.util.concurrent.Executors#newFixedThreadPool(int)
     */
    ExecutorService executorService = Executors.newFixedThreadPool(10);


    // todo 2.解释各个参数的含义，阻塞队列
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 20,
            30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private static Map<String, String> map = new HashMap<>();
    private static Map<String, String> map2 = new ConcurrentHashMap<>();

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
