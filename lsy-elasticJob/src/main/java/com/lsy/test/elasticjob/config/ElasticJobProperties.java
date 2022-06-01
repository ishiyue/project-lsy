package com.lsy.test.elasticjob.config;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 示例代码：
 * <pre class="code">
 *   {@code
 *      elastic-job:
 *        zookeeper:
 *          serverLists: localhost:2181
 *          namespace: elecwatt
 *        jobs:
 *          - name: demo-simple-job:
 *            cron: 0 0 0 * * * ?
 *          - name: demo-simple-job2:
 *            cron: 0 0 0 * * * ?
 *   }
 * </pre>
 *
 * @author liu.yj
 * @see ElasticJobConf
 * @see com.lsy.test.elasticjob.config.ElasticJobRegister
 * @since 2022/1/11 21:49
 */
@Data
@ConfigurationProperties(prefix = "elastic-job")
public class ElasticJobProperties {

    private ZookeeperProperties zookeeper;

    /**
     * Use ${spring.application.name} as default.
     */
    private String group;
    private List<JobConf> jobs;

    @Data
    public static class ZookeeperProperties {

        /**
         * 连接Zookeeper服务器的列表. 包括IP地址和端口号. 多个地址用逗号分隔. 如: host1:2181,host2:2181
         */
        private String serverLists;

        /**
         * 命名空间.
         */
        private String namespace;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobConf {

        private String name;

        /**
         * 自定义执行开关
         */
        private Boolean open = true;

        /**
         * 文字描述
         */
        private String description;

        /**
         * 表达式，如：0 0 0 * * ?
         */
        private String cron;

        /**
         * 是否持久化执行日志
         */
        private Boolean persistTrace = false;

        /**
         * 分片总数
         */
        private Integer shardingTotalCount;

        /**
         * 设置分片序列号和个性化参数对照表. 分片序列号和参数用等号分隔, 多个键值对用逗号分隔. 类似map. 分片序列号从0开始, 不可大于或等于作业分片总数. 如: 0=a,1=b,2=c
         */
        private String shardingItemParameters;

        /**
         * 执行参数：如业务id、查询时间等
         */
        private String jobParameter;
    }
}
