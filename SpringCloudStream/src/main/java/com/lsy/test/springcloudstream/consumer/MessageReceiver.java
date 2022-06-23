package com.lsy.test.springcloudstream.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * @author lsy
 * @since 2022/6/23 10:39:27
 */
@Component
@EnableBinding(Sink.class)
public class MessageReceiver {

    private Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    //	@EventListener
    @StreamListener(Sink.INPUT)
    public void process(Object message) {
        System.out.println("received message : " + message);
        logger.info("received message : {}", message);
    }
}
