package com.lsy.test.user.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogListener.class);

/*
    @Autowired
    HostGroupSynLogService hostGroupSynLogService;
    @Autowired
    HostGroupOprLogService hostGroupOprLogService;
    @Autowired
    DeviceUnitService deviceUnitService;
*/

   // @EventListener(value = {HostOprLogEvent.class, HostSynLogEvent.class})
    public void onApplicationEvent(ApplicationEvent event) {
       /* if (event instanceof HostOprLogEvent) {
            HostOprLogEvent hostOprLogEvent = (HostOprLogEvent) event;
            this.save(hostOprLogEvent);
        } else {
            HostSynLogEvent hostSynLogEvent = (HostSynLogEvent) event;
            this.save(hostSynLogEvent);
        }*/
    }

/*    private void save(HostOprLogEvent event) {
        HostGroupOprLogRequest request = new HostGroupOprLogRequest();
        request.setHostId(event.hostId);
        request.setStatus(event.status);
        request.setRemark(event.remark);
        hostGroupOprLogService.save(request);
    }*/

/*    private void save(HostSynLogEvent event) {
        LOGGER.info("[主机群同步日志]event:{}", event);
        HostGroupSynLogRequest request = new HostGroupSynLogRequest();
        request.setDeviceId(event.deviceId);
        request.setHostId(event.hostId);
        request.setSynType(event.synType);
        request.setSynValue(event.synValue);
        request.setSynStatus(event.synStatus);
        request.setFailReason(event.failReason);
        if (Objects.isNull(event.autoId)) {
            Response<DeviceUnit> deviceUnit = deviceUnitService.getDeviceUnit(event.deviceId);
            Response.assertSuccess(deviceUnit);
            if (deviceUnit.getData() != null) {
                request.setDeviceSeq(deviceUnit.getData().getDevseq());
            }
            hostGroupSynLogService.save(request);
        } else {
            request.setAutoId(event.autoId);
            hostGroupSynLogService.update(request);
        }
    }*/
}
