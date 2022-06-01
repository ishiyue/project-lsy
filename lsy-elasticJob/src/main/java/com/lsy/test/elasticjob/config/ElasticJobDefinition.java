package com.lsy.test.elasticjob.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.lsy.test.elasticjob.config.ElasticJobProperties.JobConf;
import org.apache.commons.lang3.StringUtils;

/**
 * 任务定义
 *
 * @author liu.yj
 * @since 2022/1/14 20:24
 */
public interface ElasticJobDefinition {

    default String getFullJobName(String group, String jobName) {
        return joinAt(group.toUpperCase(), jobName);
    }

    default String getFullJobName(String group, String jobName, String uniqueId) {
        return joinAt(group.toUpperCase(), jobName, uniqueId);
    }

    /**
     * 定义Lite作业根配置、定义SIMPLE类型配置、定义作业核心配置
     * @param simpleJob
     * @param jobConf
     * @return
     */
    default LiteJobConfiguration getLiteJobConfiguration(SimpleJob simpleJob, JobConf jobConf) {
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobConf.getName(), jobConf.getCron(), jobConf.getShardingTotalCount())
                        .shardingItemParameters(jobConf.getShardingItemParameters())
                        .jobParameter(jobConf.getJobParameter())
                        .description(jobConf.getDescription())
                        .build(),
                simpleJob.getClass().getCanonicalName());
        return LiteJobConfiguration.newBuilder(simpleJobConfiguration)
                .overwrite(true).build();
    }

    /**
     * Join string with 'at' character '@'.
     *
     * @param args a, b, c
     * @return a@b@c
     */
    default String joinAt(Object... args) {
        return StringUtils.join(args, "@");
    }
}
