package org.spring.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableAsync
public class BeanConfig {

    @Bean(name="taskExecutor")
    public TaskExecutor taskExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);        // 线程池中最少存在的线程个数；线程池创建时，会立即创建5个线程，并且在运行过程中，即使没有任务线程被使用到，线程池中也始终保持5个线程；
        executor.setMaxPoolSize(10);        // 当某段时间，异步任务较多时，当5个常驻线程被使用完后，当请程序继续调用线程池的get方法获取线程时，将继续创建线程，但线程池中的线程总个数不超过10个；
        /*
        * 当 核心线程 被使用完后，且 maxPoolSize 大于 corePoolSize 时，线程池不会立即创建新的线程来执行 新异步任务；而是将请求放入到缓存队列中；
        * 然后，有缓存池根据新线程创建算法，来在合适的时候创建新线程，然后，在从缓存队列中取出请求，执行请求的异步任务
        * */
        executor.setQueueCapacity(20);
        /*
        * keepAliveTime：表示线程没有任务执行时最多保持多久时间会终止，单位为秒。默认情况下，只有当线程池中的线程数大于corePoolSize时，
        * keepAliveTime才会起作用，直到线程池中的线程数不大于corePoolSize，即当线程池中的线程数大于corePoolSize时，
        * 如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。
        * 但是如果调用了allowCoreThreadTimeOut(boolean)方法，在线程池中的线程数不大于corePoolSize时，keepAliveTime参数也会起作用，
        * 直到线程池中的线程数为0
        * */
        executor.setKeepAliveSeconds(60);   // 当异步任务执行完了，线程别逐个归还时，除了5个核心线程，多出来的5个线程，最多只保存60秒，若60秒内没有请求，则多出来的5个线程将被释放；线程池中始终只维持核心个数（此处为5）个线程的状态
        executor.setThreadNamePrefix("sgcc-");  // 线程名前缀
        /*
        * 当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略，通常有以下四种策略
        * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
        * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
        * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
        * ThreadPoolExecutor.CallerRunsPolicy：由调用线程(调用线程池的get方法获取请求新线程的方法所在线程)处理该任务
        * */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        /*
        * 等待所有线程池中的线程都执行完后，才释放线程池
        * */
        executor.setWaitForTasksToCompleteOnShutdown(true);

        return executor;
    }

    @Bean(name ="SGCC_threadPoolExecutor")
    ThreadPoolExecutor threadPoolExecutor(){
        int corePoolSize = 5;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,2*corePoolSize,60, TimeUnit.SECONDS
                ,new LinkedBlockingQueue<Runnable>(20));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }


}
