package com.lsy.test.shardingsphere.algorithm;

import java.util.Arrays;
import java.util.Collection;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

public class MyRangTableShardingAlogorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        Long lowerEndpoint = rangeShardingValue.getValueRange().lowerEndpoint();
        Long upperEndpoint = rangeShardingValue.getValueRange().upperEndpoint();
        return Arrays.asList(rangeShardingValue.getLogicTableName()+"_1",rangeShardingValue.getLogicTableName()+"_2");
    }
}
