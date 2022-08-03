package com.lsy.test.user.controller;

public class ThreadLocalTest {
    static ThreadLocal<String> th=new ThreadLocal<>();
    public static void set(){
        th.set(Thread.currentThread().getName());
    }
    public static String get(){
        return th.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程中尝试获取值："+get());
        set();
        System.out.println("主线程中再次尝试获取值："+get());
        Thread thread=new Thread(new Th1(),"child");
        thread.start();
        thread.join();
        System.out.println("等待子线程执行完毕，主线程中再次尝试获取值:" + get());
    }
    static class Th1 implements Runnable {
        @Override
        public void run() {
            System.out.println("子线程中尝试获取值:" + get());
            //子线程中设置值，值为线程名字
            set();
            System.out.println("子线程中再次尝试获取值:" + get());
        }
    }
}
