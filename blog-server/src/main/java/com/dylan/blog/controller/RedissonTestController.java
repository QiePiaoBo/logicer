package com.dylan.blog.controller;

import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * @Classname RedissonTestController
 * @Description RedissonTestController
 * @Date 9/13/2022 2:44 PM
 */
@RestController
@RequestMapping("redisson")
public class RedissonTestController {

    private static final MyLogger log = MyLoggerFactory.getLogger(RedissonTestController.class);

    @Resource
    private RedissonClient redissonClient;


    @RequestMapping("test")
    public HttpResult tryLockTest(){

        RLock tryLock_01 = redissonClient.getLock("TryLock_01");
        if (tryLock_01.tryLock()){
            log.info("加锁成功");
            new Thread(this::testAnotherThread).start();
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){
                log.error("e: {}", e.getMessage(), e);
            }
        }
        tryLock_01.unlock();
        return DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg())
                .data(Boolean.TRUE)
                .build();
    }

    public void testAnotherThread(){
        RLock tryLock_02 = redissonClient.getLock("TryLock_01");
        if (tryLock_02.tryLock()) {
            log.info("另一个线程获取锁成功");
        }else {
            log.info("另一个线程获取锁失败");
        }
    }

}
