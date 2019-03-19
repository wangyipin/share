package com.ty.share.websocket;

import java.util.HashMap;
import java.util.Hashtable;
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

    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(20, 20,
            30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    // todo 1.演示线程安全 map
    private static Map<String, String> map3 = new HashMap<>();
    private static Map<String, String> map2 = new ConcurrentHashMap<>();
    private static Map<String, String> map = new Hashtable<>();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3000000; i++) {
            String s = i + "";
            poolExecutor.execute(() -> {
                map.put(s, s);
                map.get("1000");
            });
        }
        try {
            poolExecutor.shutdown();
            while (!poolExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("线程池仍未关闭");
            }
            System.out.println("耗时: "+(System.currentTimeMillis()-start));
            System.out.println("size: " + map.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
