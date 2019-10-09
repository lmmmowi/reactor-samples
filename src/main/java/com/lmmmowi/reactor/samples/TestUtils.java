package com.lmmmowi.reactor.samples;

/**
 * @Author: 11102942
 * @Date: 2019/10/9
 * @Description:
 */
public class TestUtils {

    public static <T> void print(T o) {
        System.out.println(String.format("Thread[%s]: ", Thread.currentThread().getName()) + o);
    }

    public static void print(Throwable throwable) {
        String msg = throwable.getClass().getName() + ": " + throwable.getMessage();
        System.err.println(String.format("Thread[%s]: ", Thread.currentThread().getName()) + msg);
    }

    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
