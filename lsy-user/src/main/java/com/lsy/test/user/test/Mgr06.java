package com.lsy.test.user.test;

import java.util.concurrent.ConcurrentHashMap;

public class Mgr06 {
    private static volatile Mgr06 INSTANCE;

    private Mgr06() {

    }

    ConcurrentHashMap chm = null;

    public static Mgr06 getInstance() {
        if (INSTANCE == null) {//Double Check Lock  (DCL)
            synchronized (Mgr06.class) {
                if (INSTANCE == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new Mgr06();
                }
            }
        }
        return INSTANCE;
    }
}
