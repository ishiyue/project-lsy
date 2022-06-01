package com.test.lsy;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.PutParams;
import com.ecwid.consul.v1.session.model.NewSession;

import java.time.LocalDateTime;
import java.util.Random;

public class Lock {
    private static final String prefix="lock";
    private ConsulClient consulClient;
    private String sessionName;
    private String sessionId=null;
    private String lockKey;

    /**
     *
     * @param consulClient
     * @param sessionName   同步锁的session名称
     * @param lockKey       同步锁在consul的KV存储中的Key路径，会自动增加prefix前缀，方便归类查询
     */
    public Lock(ConsulClient consulClient,String sessionName,String lockKey){
        this.consulClient=consulClient;
        this.sessionName=sessionName;
        this.lockKey=lockKey;
    }
    /**
     * 获取同步锁
     *
     * @param block     是否阻塞，直到获取到锁为止
     * @return
     */
    public Boolean lock(boolean block){
        if(sessionId!=null){
            throw new RuntimeException(sessionId+"-Alread locked!");
        }
        sessionId=createSession(sessionName);
        while (true){
            PutParams putParams=new PutParams();
            putParams.setAcquireSession(sessionId);
            if(consulClient.setKVValue(lockKey,"lock:"+ LocalDateTime.now(),putParams).getValue()){
                return true;
            }else if(block){
                continue;
            }else {
                return false;
            }
        }
    }
    /**
     * 释放同步锁
     *
     * @return
     */
    public Boolean unlock(){
        PutParams putParams=new PutParams();
        putParams.setReleaseSession(sessionId);
        Boolean result = consulClient.setKVValue(lockKey, "unlock:" + LocalDateTime.now(), putParams).getValue();
        consulClient.sessionDestroy(sessionId,null);
        return result;
    }
    /**
     * 创建session
     * @param sessionName
     * @return
     */
    private String createSession(String sessionName){
        NewSession newSession=new NewSession();
        newSession.setName(sessionName);
        return consulClient.sessionCreate(newSession,null).getValue();
    }
    static class LockRunner implements Runnable {
        //private Logger logger = Logger.getLogger(getClass());
        private int flag;
        public LockRunner(int flag) {
            this.flag = flag;
        }
        @Override
        public void run() {
            Lock lock = new Lock(new ConsulClient(), "lock-session", "lock-key");
            try {
                if (lock.lock(true)) {
                    //logger.info("Thread " + flag + " start!");
                    Thread.sleep(new Random().nextInt(3000));
                    //logger.info("Thread " + flag + " end!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new LockRunner(1)).start();
        new Thread(new LockRunner(2)).start();
        new Thread(new LockRunner(3)).start();
        new Thread(new LockRunner(4)).start();
        new Thread(new LockRunner(5)).start();
        Thread.sleep(200000L);
    }
}
