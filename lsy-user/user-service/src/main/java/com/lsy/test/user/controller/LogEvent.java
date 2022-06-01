package com.lsy.test.user.controller;

import org.springframework.context.ApplicationEvent;

public class LogEvent extends ApplicationEvent {
    private String username;
    public LogEvent(Object source) {
        super(source);
    }
    public LogEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
