package com.lsy.test.user.event;

/**
 * @author lsy
 * @since 2022/5/23 13:48:42
 */
public class HostOprLogEvent {
    private static final long serialVersionUID = 1L;

    public String hostId;
    public Integer status;
    public String remark;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public HostOprLogEvent(Object source, String hostId, Integer status, String remark) {
        super(source);
        this.hostId = hostId;
        this.status = status;
        this.remark = remark;
    }
}
