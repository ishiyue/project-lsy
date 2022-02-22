package com.distribution.management.system.common.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @author admin
 */
@Component
@Slf4j
public class RedisLockUtils {

    @Resource
    private RedisLockProperties redisLockProperties;

    @Resource
    protected RedisLockRegistry redisLockRegistry;

    /***
     * 加锁
     * @param lockKey
     * @return
     */
    @SneakyThrows
    public <T> Boolean lock(T lockKey, Long expire) {
        return redisLockRegistry.obtain(lockKey).tryLock(expire, TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    public <T> Boolean reyTrylock(T lockKey, Long expire) {
        Boolean lockStatus = lock(lockKey, expire);
        if (ObjectUtils.isNotEmpty(lockStatus) && lockStatus) {
            return Boolean.TRUE;
        }
        for (int i = 0; i < redisLockProperties.getRetryNum(); i++) {
            lockStatus = lock(lockKey, expire);
            if (ObjectUtils.isNotEmpty(lockStatus) && lockStatus) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @SneakyThrows
    public <T> Boolean reyTrylock(T lockKey) {
        return reyTrylock(lockKey, redisLockProperties.getExpire());
    }


    /***
     * 加锁
     * @param lockKey
     * @return
     */
    @SneakyThrows
    public <T> Boolean lock(T lockKey) {
        return lock(lockKey, redisLockProperties.getExpire());
    }


    /***
     * 解锁
     * @param lockKey
     * @return
     */
    public <T> void unlock(T lockKey) {
        redisLockRegistry.obtain(lockKey).unlock();
    }

}
