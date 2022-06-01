package com.lsy.test.elasticjob.config;

import com.dangdang.ddframe.job.lite.lifecycle.domain.JobBriefInfo;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobSettings;
import com.dangdang.ddframe.job.lite.lifecycle.domain.ShardingInfo;
import com.google.common.base.Optional;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

/**
 * Elastic job 管理
 *
 * @author liu.yj
 * @since 2022/4/22 14:06
 */
@RequiredArgsConstructor
public class ElasticJobService {

    private final ElasticJobAPIService jobAPIService;

    public int getJobsTotalCount() {
        return jobAPIService.getJobStatisticsAPI().getJobsTotalCount();
    }

    public Collection<JobBriefInfo> getAllJobsBriefInfo() {
        return jobAPIService.getJobStatisticsAPI().getAllJobsBriefInfo();
    }

    /**
     * Trigger job.
     *
     * @param jobName job name
     */
    public Boolean triggerJob(final String jobName) {
        jobAPIService.getJobOperatorAPI().trigger(Optional.of(jobName), Optional.absent());
        return Boolean.TRUE;
    }

    /**
     * Disable job.
     *
     * @param jobName job name
     */
    public Boolean disableJob(final String jobName) {
        jobAPIService.getJobOperatorAPI().disable(Optional.of(jobName), Optional.absent());
        return Boolean.TRUE;
    }

    /**
     * Enable job.
     *
     * @param jobName job name
     */
    public Boolean enableJob(final String jobName) {
        jobAPIService.getJobOperatorAPI().enable(Optional.of(jobName), Optional.absent());
        return Boolean.TRUE;
    }

    /**
     * Shutdown job.
     *
     * @param jobName job name
     */
    public Boolean shutdownJob(final String jobName) {
        jobAPIService.getJobOperatorAPI().shutdown(Optional.of(jobName), Optional.absent());
        return Boolean.TRUE;
    }

    /**
     * Get sharding info.
     *
     * @param jobName job name
     * @return sharding info
     */
    public Collection<ShardingInfo> getShardingInfo(final String jobName) {
        return jobAPIService.getShardingStatisticsAPI().getShardingInfo(jobName);
    }

    /**
     * Disable sharding.
     *
     * @param jobName job name
     * @param item    sharding item
     */
    public Boolean disableSharding(final String jobName, final String item) {
        jobAPIService.getShardingOperateAPI().disable(jobName, item);
        return Boolean.TRUE;
    }

    /**
     * Enable sharding.
     *
     * @param jobName job name
     * @param item    sharding item
     */
    public Boolean enableSharding(final String jobName, final String item) {
        jobAPIService.getShardingOperateAPI().enable(jobName, item);
        return Boolean.TRUE;
    }

    /**
     * Get job configuration.
     *
     * @param jobName job name
     * @return job configuration
     */
    public JobSettings getJobConfig(final String jobName) {
        return jobAPIService.getJobSettingsAPI().getJobSettings(jobName);
    }

    /**
     * Update job configuration.
     *
     * @param jobSettings job configuration
     */
    public Boolean updateJobConfig(final JobSettings jobSettings) {
        jobAPIService.getJobSettingsAPI().updateJobSettings(jobSettings);
        return Boolean.TRUE;
    }

    /**
     * Remove job configuration.
     *
     * @param jobName job name
     */
    public Boolean removeJob(final String jobName) {
        jobAPIService.getJobSettingsAPI().removeJobSettings(jobName);
        return Boolean.TRUE;
    }
}
