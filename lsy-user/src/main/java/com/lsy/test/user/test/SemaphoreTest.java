package com.lsy.test.user.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * @author lsy
 * @since 2022/7/18 20:04:27
 */
public class SemaphoreTest {

    public static void main(String[] args) throws InterruptedException {
        //初始化5车位
        Semaphore semaphore=new Semaphore(5);
        //八辆车
        CountDownLatch countDownLatch = new CountDownLatch(8);
        for (int i=0;i<8;i++){
            int finalI=i;
            if(i==5){
                Thread.sleep(1000);
                new Thread(()->{
                    stopCarNotWait(semaphore,finalI);
                    countDownLatch.countDown();
                }).start();
                continue;
            }
            new Thread(() -> {
                stopCarWait(semaphore, finalI);
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("总共剩余车位"+semaphore.availablePermits());
    }

    private static void stopCarWait(Semaphore semaphore, int finalI) {
        String format = String.format("车牌号%d", finalI);
        try {
            semaphore.acquire(1);
            System.out.println(format+"找到车位，停车");
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            semaphore.release(1);
            System.out.println(format+"车开走了");
        }
    }

    private static void stopCarNotWait(Semaphore semaphore, int finalI) {
        String format = String.format("车牌号%d", finalI);
        try {
            if(semaphore.tryAcquire()){
                System.out.println("找到车位了，去停车了"+format);
                Thread.sleep(1000);
                System.out.println("车开走了"+format);
                semaphore.release();
            }else {
                System.out.println("没有车位"+format);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
