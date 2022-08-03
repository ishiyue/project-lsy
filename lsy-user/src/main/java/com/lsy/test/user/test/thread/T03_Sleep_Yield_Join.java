package com.lsy.test.user.test.thread;

public class T03_Sleep_Yield_Join {
    public static void main(String[] args) {
        testJoin();
    }
    static void testSleep(){
        new Thread(()->{
            for (int i=0;i<100;i++){
                System.out.println("A"+i);
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 返回到就绪状态
     */
    static void testYield(){
        new Thread(()->{
            for (int i=0;i<100;i++){
                System.out.println("A"+i);
                if(i%10==0){
                    Thread.yield();
                }
            }
        }).start();
        new Thread(()->{
            for (int i=0;i<100;i++){
                System.out.println("B"+i);
                if(i%10==0){
                    Thread.yield();
                }
            }
        }).start();
    }

    /**
     * 把另外一个线程加入到线程运行
     */
    static void testJoin(){
        Thread t1=new Thread(()->{
            for (int i=0;i<100;i++){
                System.out.println("A"+i);
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        Thread t2=new Thread(()->{
                try {
                    t1.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

        });
    }
}
