package com.lsy.test.user.service.impl;

import java.util.concurrent.ExecutionException;

public interface PushProcess {

    public void pushData() throws ExecutionException, InterruptedException;
}
