package com.lsy.test.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import java.util.List;

/**
 * @author lsy
 * @since 2022/5/31 10:35:12
 */
public class DataflowTest implements DataflowJob {

    @Override
    public List fetchData(ShardingContext shardingContext) {
        //查询数据
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List list) {
    //处理数据
    }
}
