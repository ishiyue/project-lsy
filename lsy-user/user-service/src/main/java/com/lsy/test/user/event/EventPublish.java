package com.elecwatt.biz.solutions.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author lsy
 */
@Component
public class EventPublish {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 事件发布
     */
    public void eventPublish(Object source, String hostId, Integer status, String remark) {
        applicationEventPublisher.publishEvent(new HostOprLogEvent(source, hostId, status, remark));
    }
/*    *//**
     * 事件发布
     *//*
    public void eventPublish(Object source, Long autoId,String hostId, String deviceId, Integer synType, Integer synValue, Integer synStatus, String failReason) {
        applicationEventPublisher.publishEvent(new HostSynLogEvent(source,autoId, hostId, deviceId,synType, synValue, synStatus, failReason));
    }*/

}
