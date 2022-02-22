package com.lsy.test.user.service;

import com.distribution.management.system.common.constant.RedisConstant;
import com.lsy.test.user.entity.Auth;
import com.lsy.test.user.mapper.AuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class AuthService {

    @Resource
    AuthMapper authMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 这是每批处理的大小
     */
    private final static int BATCH_SIZE = 1000;

    private int size;

    /**
     * 存储每批数据的临时容器
     */
    private List<Auth> list;

    public AuthService(List<Auth> list) {
        this.list = list;
    }

    /**
     * 当指定查询数据过大时，我们一般使用分页查询的方式，一页一页的将数据放到内存处理。但有些情况不需要分页的方式查询数据，
     * 如果一下子将数据全部加载出来到内存中，很可能会发生OOM。这时我们可以使用流式查询解决问题
     * 源码说明:
     * protected boolean createStreamingResultSet() {
     * return ((this.query.getResultType() == Type.FORWARD_ONLY) && (this.resultSetConcurrency == java.sql.ResultSet.CONCUR_READ_ONLY)
     * && (this.query.getResultFetchSize() == Integer.MIN_VALUE));
     * }
     * resultSetConcurrency=ResultSet.CONCUR_READ_ONLY  设置只读结果集
     * resultSetType = ResultSetType.FORWARD_ONLY 设置结果集的游标只能向下滚动
     * fetchSize = Integer.MIN_VALUE 设置fetch size为int的最小值，这里和oracle/db2有区别.
     * Oracle/db2是从服务器一次取出fetch size 条记录放在客户端，客户端处理完成一个批次后再向服务器取下一个批次，直到所有数据处理完成。
     * mysql在执行ResultSet.next()方法时，会通过数据库连接一条一条的返回。MySQL按照自己的节奏不断的把buffer写回网络中。flush buffer的过程是阻塞式的，
     * 也就是说如果网络中发生了拥塞，send buffer被填满，会导致buffer一直flush不出去，那MySQL的处理线程会阻塞，从而避免数据把客户端内存撑爆
     * <p>
     * ResultSet数据返回的结果，对象有3种实现方式
     * ResultsetRowsStatic 静态结果集，默认的查询方式，普通查询
     * <p>
     * ResultsetRowsCursor 游标结果集，服务器端基于游标查询
     * <p>
     * ResultsetRowsStreaming 动态结果集，流式查询
     * <p>
     * 特别场景:
     * 1.在ShardingSphere的场景下,需要重写起策略方法,需要将物理表替换成逻辑表,比如按照订单order表(按月分表),就必须要替换成order_2022_01
     * 2.在多数据源的场景需要制定数据源信息
     *
     * @return
     */
    public void handlerDealData() {
        //先删除数据
        delete();
        final String sql = stringRedisTemplate.opsForValue().get(RedisConstant.REDIS_DEAL_SQL);
        authMapper.handlerDealData(sql, new ResultHandler<Auth>() {
            @Override
            public void handleResult(ResultContext<? extends Auth> resultContext) {
                final Auth auth = resultContext.getResultObject();
                //流式查询里返回的单条结果
                list.add(auth);
                size++;
                if (size == BATCH_SIZE) {
                    handle();
                }
            }
        });
        //用来完成最后一批数据处理
        handle();
    }
    /**
     * 数据处理
     */
    private void handle() {
        try {
            // 在这里可以对你获取到的批量结果数据进行需要的业务处理

        } catch (Exception e) {
            log.error("异常业务日志打印:e:[{}]", e);
        } finally {
            // 处理完每批数据后后将临时清空
            size = 0;
            list.clear();
        }
    }
    private void delete(){
        //删除迁移库中数据
    }
}
