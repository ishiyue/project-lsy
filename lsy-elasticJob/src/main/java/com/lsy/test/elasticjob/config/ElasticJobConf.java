package com.lsy.test.elasticjob.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * Job 配置类注解，程序启动时初始化并注册到 elastic job，代码示例：
 * <pre>
 * &#064;Slf4j
 * &#064;ElasticJobConf(name = "demo-job", cron = "*&frasl;10 * * * * ?", shardingTotalCount = 5)
 * public class DemoJob implements SimpleJob {
 *
 *     &#064;Override
 *     public void execute(ShardingContext shardingContext) {
 *         switch (shardingContext.getShardingItem()) {
 *             case 0:
 *             case 2:
 *             case 4:
 *                 log.info("Job1 case1: {}", shardingContext);
 *                 break;
 *             case 1:
 *             case 3:
 *                 log.info("Job1 case2: {}", shardingContext);
 *                 break;
 *         }
 *     }
 * }
 * </pre>
 *
 * @author liu.yj
 * @see com.dangdang.ddframe.job.api.simple.SimpleJob
 * @since 2022/1/11 21:40
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ElasticJobConf {

    /**
     * 任务名，默认类名，注册到zookeeper时会带上应用名，如：DEMO-APP@SimpleJob
     * <p></p>
     * 注：确保作业名的全局唯一性
     */
    String name() default "";

    /**
     * 文字描述
     */
    String description() default "";

    /**
     * like "0 0 0 * * ? 2022"
     */
    String cron();

    /**
     * 是否持久化执行日志
     */
    boolean persistTrace() default false;

    /**
     * 分片总数
     */
    int shardingTotalCount() default 1;

    String shardingItemParameters() default "";

    String jobParameter() default "";
}
