package com.lsy.test.user.test;

public class M {
    /**
     * 对象回收会被调用
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable{
        System.out.println("finalize");
    }
}
