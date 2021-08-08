package com.huahun.basic.common.utils;

/**
 * @ClassName SleepUtils
 * @Description TODO
 * @Author zzh
 * @Date 2021/8/6 17:30
 * @Version 1.0
 */
public class SleepUtils {
    public static void sleep(Integer second) {
        try {
            Thread.sleep(1000 * second);
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
        }
    }
}
