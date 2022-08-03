package com.lsy.test.user.service;

import com.test.lsy.user.api.UserServiceClient;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceClient {
    @Override
    public void addUser(){
        System.out.println("......");
    }
}
