package com.lsy.test.user.test;

public class T {
    private int count = 10;
    private Object o = new Object();

    public void m() {
        synchronized (o) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count=" + count);
        }
    }

    /**
     * n方法和n1方法等同
     */
    public synchronized void n() {
        count--;
    }

    public void n1() {
        synchronized (this) {
            count--;
        }
    }

    /**
     * O方法和o1方法等同
     */
    public synchronized static void o() {

    }
    public void o1(){
        synchronized (T.class){

        }
    }
}
