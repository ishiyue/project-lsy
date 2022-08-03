package com.lsy.test.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PushProcessServiceImpl implements PushProcess {

    private final static Logger logger = LoggerFactory.getLogger(PushProcessServiceImpl.class);

    private static final Integer LIMIT = 5000;

    private static final Integer CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    private static final Integer MAXIMUM_POOL_SIZE = CORE_POOL_SIZE;

    ThreadPoolExecutor pool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE * 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));

    @Override
    public void pushData() throws ExecutionException, InterruptedException {

        int count = 0;
        Integer total = 1000;
        logger.info("推送数据总条数：{}", total);
        int num = total / (LIMIT * CORE_POOL_SIZE) + 1;
        int totalSuccessCount = 0;
        for (int i = 0; i < num; i++) {
            List<Future<Integer>> futureList = new ArrayList<>(32);
            for (int j = 0; j < CORE_POOL_SIZE; j++) {
                synchronized (PushProcessServiceImpl.class) {
                    int start = count * LIMIT;
                    count++;
                    Future<Integer> future = pool.submit(new PushDataTask(start, LIMIT, start));
                    futureList.add(future);
                }
            }
            for (Future f : futureList) {
                totalSuccessCount = totalSuccessCount + (int) f.get();
            }
        }
        //**mapper.update(1);

    }

    class PushDataTask implements Callable<Integer> {
        int start;
        int limit;

        PushDataTask(int start, int limit, int threadNo) {
            this.start = start;
            this.limit = limit;
        }

        @Override
        public Integer call() throws Exception {
            Thread.currentThread().setName("Thread" + start);
            int count = 0;
            //推送数据
            //List<*> list=**Mapper.find**(0,start,limit);
           /* if(CollectionUtils.isEmpty(list)){
                count;
            }*/
           /* for (T t:list){
                //推送方法 返回isSuccess
                if(isSuccess){
                    //更新数据库数据推送成功的标识
                    count++;
                }else{
                    //更新数据库数据推送失败的标识
                }
            }*/
            return count;
        }
    }
}
