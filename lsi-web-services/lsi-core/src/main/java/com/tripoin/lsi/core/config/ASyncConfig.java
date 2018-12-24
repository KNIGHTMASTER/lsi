package com.tripoin.lsi.core.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created on 11/23/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@EnableAsync
@Configuration
public class ASyncConfig implements AsyncConfigurer {

    @Value("${thread.corepoolsize}")
    private int threadCorePoolSize;

    @Value("${thread.maxpoolsize}")
    private int threadMaxPoolSize;

    @Value("${thread.queuecapacity}")
    private int threadQueueCapacity;

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadCorePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(threadMaxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(threadQueueCapacity);
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AppAsyncUncaughtExceptionHandler();
    }

}
