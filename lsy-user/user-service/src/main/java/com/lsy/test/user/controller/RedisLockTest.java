package com.lsy.test.user.controller;

import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisLockTest {

    @Autowired
    Redisson redisson;

   public void get(){
       redisson.getLock("test");
   }

}
