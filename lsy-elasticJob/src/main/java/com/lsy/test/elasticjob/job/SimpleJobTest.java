package com.lsy.test.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lsy.test.elasticjob.config.ElasticJobConf;

/**
 * @author lsy
 * @since 2022/5/31 10:32:46
 */
@ElasticJobConf(name = "license-job", cron = "0 55 17 * * ?" )
public class SimpleJobTest implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {

        System.out.println(1);
        shardingContext.getJobParameter();
    }
}
