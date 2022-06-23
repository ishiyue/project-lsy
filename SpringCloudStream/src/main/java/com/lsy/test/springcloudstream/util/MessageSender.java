package com.lsy.test.springcloudstream.util;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author lsy
 * @since 2022/6/23 11:00:19
 */
@Component
public class MessageSender {



    private final Source source;

    public MessageSender(Source source) {
        this.source = source;
    }

    public void sendMessage(Object message) {
        MessageBuilder<Object> builder = MessageBuilder.withPayload(message);
        source.output().send(builder.build());
    }
}
