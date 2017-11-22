package com.websocket.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * 这个注解用于标注某个方法或某个类里面的所有方法都是需要异步处理的。
 * 被注解的方法被调用的时候，会在新线程中执行，而调用它的方法会在原来的线程中执行。
 * 这样可以避免阻塞、以及保证任务的实时性。适用于处理log、发送邮件、短信……等
 */
@Service
public class AsyncTaskService {

    private static Logger logger = LoggerFactory.getLogger(AsyncTaskService.class);

    @Async
    public void task1()throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(1000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task1任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

    @Async
    public void task2() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(2000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task2任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

    @Async
    public void task3() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(3000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task3任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

//////////////////////////////////////////////////////
//自定义Executor线程池使用, 使用Future回调,需要注意的是如果任务正常终止、异常或取消，都将返回true
//////////////////////////////////////////////////////


    @Async("mySimpleAsync1")
    public Future<String> doTask1() throws InterruptedException{
        logger.info("Task1 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        long end = System.currentTimeMillis();

        logger.info("Task1 finished, time elapsed: {} ms.", end-start);

        return new AsyncResult<String>("Task1 accomplished!");
    }

    @Async(value = "mySimpleAsync2")
    public Future<String> doTask2() throws InterruptedException{
        logger.info("Task2 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();

        logger.info("Task2 finished, time elapsed: {} ms.", end-start);

        return new AsyncResult<>("Task2 accomplished!");
    }
}
