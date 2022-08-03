package com.lsy.test.user.test.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.math.BigDecimal;

public class T {
    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + "m1 start");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m1 end");
    }

    public void m2() {
        try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m2");
    }

    public static void main(String[] args) {
        T t = new T();
        //new Thread(t::m1,"t1").start();
        // new Thread(t::m2,"t2").start();
        String s = "{\"CURRENT_FLOW\":\"7.00\"}";
        JSONObject jsonObject = JSON.parseObject(s);
        BigDecimal dayFreezeFlow = jsonObject.getBigDecimal("CURRENT_FLOW");
        System.out.println(dayFreezeFlow);
        JSON.toJSONString(Lists.newArrayList());
        Boolean linkage = true;
        Boolean control = true;
        if (linkage == null || !linkage || !control) {
            return;
        }
        System.out.println(1);
    }


}
