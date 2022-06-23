package com.lsy.test.springcloudstream.controller;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lsy
 * @since 2022/6/23 11:04:54
 */
@RestController
public class SendMessageController {

    private final Source source;

    public SendMessageController(Source source) {
        this.source = source;
    }

    @GetMapping("/send")
    public Object send(String message) {
        MessageBuilder<String> messageBuilder = MessageBuilder.withPayload(message);
        source.output().send(messageBuilder.build());
        return "message sended : "+message;
    }

    @GetMapping("/sendBatch")
    public Object sendbatch() {
        for(int i = 0 ; i < 10 ; i ++) {
            MessageBuilder<String> messageBuilder = MessageBuilder.withPayload("这是第"+i+"条消息");
            source.output().send(messageBuilder.build());
        }
        return "message batch sended";
    }
}
