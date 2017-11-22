package com.websocket.demo.controller;

import com.websocket.demo.service.AsyncTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/async")
public class AsyncTaskController {

    private static Logger logger = LoggerFactory.getLogger(AsyncTaskController.class);
    @Resource
    private AsyncTaskService asyncTaskService;

    @RequestMapping("/doTask")
    public String doTask() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        asyncTaskService.task1();
        asyncTaskService.task2();
        asyncTaskService.task3();
        long currentTimeMillis1 = System.currentTimeMillis();
        return "task任务总耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms";
    }

    @RequestMapping("/doTask/myExecutor")
    public void AsyncTaskTestWithMyExecutor() throws InterruptedException,ExecutionException{
        Future<String> task1 = asyncTaskService.doTask1();
        Future<String> task2 = asyncTaskService.doTask2();

        while (true){
            if (task1.isDone() && task2.isDone()){
                logger.info("Task1 result: {}", task1.get());
                logger.info("Task2 result: {}", task2.get());
                break;
            }
            Thread.sleep(1000);
        }
        logger.info("All tasks finished.");
    }
}
