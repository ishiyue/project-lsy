/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lsy.test.elasticjob.config;

import com.dangdang.ddframe.job.lite.lifecycle.api.JobAPIFactory;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobOperateAPI;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobSettingsAPI;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobStatisticsAPI;
import com.dangdang.ddframe.job.lite.lifecycle.api.ShardingOperateAPI;
import com.dangdang.ddframe.job.lite.lifecycle.api.ShardingStatisticsAPI;
import com.google.common.base.Optional;
import com.lsy.test.elasticjob.config.ElasticJobProperties.ZookeeperProperties;
import lombok.RequiredArgsConstructor;

/**
 * Job API service.
 */
@RequiredArgsConstructor
public final class ElasticJobAPIService {

    private final ZookeeperProperties zookeeperProperties;

    public JobSettingsAPI getJobSettingsAPI() {
        return JobAPIFactory.createJobSettingsAPI(zookeeperProperties.getServerLists(),
                zookeeperProperties.getNamespace(), Optional.absent());
    }

    public JobOperateAPI getJobOperatorAPI() {
        return JobAPIFactory.createJobOperateAPI(zookeeperProperties.getServerLists(),
                zookeeperProperties.getNamespace(), Optional.absent());
    }

    public ShardingOperateAPI getShardingOperateAPI() {
        return JobAPIFactory.createShardingOperateAPI(zookeeperProperties.getServerLists(),
                zookeeperProperties.getNamespace(), Optional.absent());
    }

    public JobStatisticsAPI getJobStatisticsAPI() {
        return JobAPIFactory.createJobStatisticsAPI(zookeeperProperties.getServerLists(),
                zookeeperProperties.getNamespace(), Optional.absent());
    }

    public ShardingStatisticsAPI getShardingStatisticsAPI() {
        return JobAPIFactory.createShardingStatisticsAPI(zookeeperProperties.getServerLists(),
                zookeeperProperties.getNamespace(), Optional.absent());
    }
}
