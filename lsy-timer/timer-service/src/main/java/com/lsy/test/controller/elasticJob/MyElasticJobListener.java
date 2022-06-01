package com.lsy.test.controller.elasticJob;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author lsy
 * @since 2022/5/25 09:45:44
 */
@Slf4j
public class MyElasticJobListener implements ElasticJobListener {

    private long beginTime = 0;

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();
        log.info("===>{} MyElasticJobListener BEGIN TIME: {} <===",shardingContexts.getJobName(),  DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        log.info("===>{} MyElasticJobListener END TIME: {},TOTAL CAST: {} <===",shardingContexts.getJobName(), DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), endTime - beginTime);

    }
}
