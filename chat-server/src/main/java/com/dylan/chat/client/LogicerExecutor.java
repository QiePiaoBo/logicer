package com.dylan.chat.client;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname LogicerConnectionExecutor
 * @Description LogicerConnectionExecutor
 * @Date 4/27/2023 1:57 PM
 */
@Configuration
public class LogicerExecutor {

    private final MyLogger logger = MyLoggerFactory.getLogger(LogicerExecutor.class);

    private static final AtomicInteger threadNum = new AtomicInteger(0);

    @Primary
    @Bean("nettyClientExecutor")
    public ThreadPoolExecutor nettyClientExecutor(){
        return new ThreadPoolExecutor(
                // 核心线程数
                4,
                // 最大线程数
                10,
                // 空闲线程最大存活时间
                60L,
                // 空闲线程最大存活时间单位
                TimeUnit.SECONDS,
                // 等待队列及大小
                new ArrayBlockingQueue<>(1),
                // 创建新线程时使用的工厂
                r -> {
                    String threadName = "thread" + threadNum.incrementAndGet();
                    logger.info("new thread created. name: {}", threadName);
                    return new Thread(r, threadName);
                },
                (r, executor) -> {
                    // Log the rejection message when the rejection policy is triggered
                    logger.info("Thread pool rejected execution: " + r.toString());
                    if (!executor.isShutdown()) {
                        r.run();
                    }
                }
                // 当线程池达到最大时的处理策略
//                new ThreadPoolExecutor.AbortPolicy()          // 抛出RejectedExecutionHandler异常
//                new ThreadPoolExecutor.CallerRunsPolicy()       // 交由调用者的线程执行
//                new ThreadPoolExecutor.DiscardOldestPolicy()  // 丢掉最早未处理的任务
//                new ThreadPoolExecutor.DiscardPolicy()        // 丢掉新提交的任务
        );
    }

    @Bean("msgCollectExecutor")
    public ThreadPoolExecutor msgCollectExecutor(){
        return new ThreadPoolExecutor(
                // 核心线程数
                4,
                // 最大线程数
                10,
                // 空闲线程最大存活时间
                60L,
                // 空闲线程最大存活时间单位
                TimeUnit.SECONDS,
                // 等待队列及大小
                new ArrayBlockingQueue<>(1),
                // 创建新线程时使用的工厂
                r -> {
                    String threadName = "thread" + threadNum.incrementAndGet();
                    logger.info("new thread created. name: {}", threadName);
                    return new Thread(r, threadName);
                },
                (r, executor) -> {
                    // Log the rejection message when the rejection policy is triggered
                    logger.info("Thread pool rejected execution: " + r.toString());
                    if (!executor.isShutdown()) {
                        r.run();
                    }
                }
                // 当线程池达到最大时的处理策略
//                new ThreadPoolExecutor.AbortPolicy()          // 抛出RejectedExecutionHandler异常
//                new ThreadPoolExecutor.CallerRunsPolicy()       // 交由调用者的线程执行
//                new ThreadPoolExecutor.DiscardOldestPolicy()  // 丢掉最早未处理的任务
//                new ThreadPoolExecutor.DiscardPolicy()        // 丢掉新提交的任务
        );
    }

}
