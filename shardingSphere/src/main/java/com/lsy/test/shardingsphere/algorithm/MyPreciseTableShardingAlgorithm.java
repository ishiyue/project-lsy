package com.lsy.test.shardingsphere.algorithm;

import java.math.BigInteger;
import java.util.Collection;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

public class MyPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        BigInteger bigInteger = BigInteger.valueOf(preciseShardingValue.getValue());
        BigInteger res = (bigInteger.mod(new BigInteger("2"))).add(new BigInteger("1"));
        String key = preciseShardingValue.getLogicTableName() + "_" + res;
        if(collection.contains(key)){
            return key;
        }
        throw new UnsupportedOperationException("route"+key+" is not supported,please check your config");
    }
}
