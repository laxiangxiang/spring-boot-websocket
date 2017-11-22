package com.websocket.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
//@EnableAsync
public class MyExecutorConfig {
    /** 最小的线程数，缺省：1 */
    private int corePoolSize = 10;
    /** 最大的线程数，缺省：Integer.MAX_VALUE */
    private int maxPoolSize = 200;
    /**
     *  当最小的线程数已经被占用满后，新的任务会被放进queue里面，
     *  当这个queue的capacity也被占满之后，pool里面会创建新线程处理这个任务，直到总线程数达到了max size，
     *  这时系统会拒绝这个任务并抛出TaskRejectedException异常
     *  （缺省配置的情况下，可以通过rejection-policy来决定如何处理这种情况）。
     *  缺省值为：Integer.MAX_VALUE
     */
    private int queueCapacity = 10;

    /**
     * 超过core size的那些线程，任务完成后，再经过这个时长（秒）会被结束掉
     */
    private int keepAliveSeconds=1;

    @Bean
    public Executor mySimpleAsync1(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("MySimpleExecutor1-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor mySimpleAsync2(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("MySimpleExecutor2-");
        /**
         * rejection-policy：当pool已经达到max size的时候，如何处理新任务
         * ABORT（缺省）：抛出TaskRejectedException异常，然后不执行
         * DISCARD：不执行，也不抛出异常
         * DISCARD_OLDEST：丢弃queue中最旧的那个任务
         * CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
