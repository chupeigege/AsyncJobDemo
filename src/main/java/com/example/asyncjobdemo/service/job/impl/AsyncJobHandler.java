package com.example.asyncjobdemo.service.job.impl;

import com.example.asyncjobdemo.constants.Constants;
import com.example.asyncjobdemo.service.job.IAsyncJob;
import com.example.asyncjobdemo.service.job.IJobVO;
import com.example.asyncjobdemo.vo.DecorateJobVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Chupei Wang
 * @since 2021/11/11
 */
@Service
public class AsyncJobHandler {
    public static final int TIMEOUT = 60;
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncJobHandler.class);
    private final Map<String, IAsyncJob> asyncJobMap;

    private final static int ASYNC_JOB_THREAD_COUNT = Runtime.getRuntime().availableProcessors() / 2;
    private final static int ASYNC_RETRY_JOB_THREAD_COUNT = Runtime.getRuntime().availableProcessors() / 2;
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()
    );

    public AsyncJobHandler(Map<String, IAsyncJob> asyncJobMap) {
        this.asyncJobMap = asyncJobMap;
    }


    @PostConstruct
    private void job() {
        BlockingQueue<IJobVO> queue = AsyncJobDataDataServiceImpl.getJobQueue();
        for (int i = 0; i < ASYNC_JOB_THREAD_COUNT; i++) {
            EXECUTOR_SERVICE.submit(() -> {
                System.out.println("AsyncJobHandler!!!");
                while (true) {
                    try {
                        IJobVO data = queue.poll(TIMEOUT, TimeUnit.SECONDS);
                        if (data != null) {
                            if (data instanceof DecorateJobVO) {
                                DecorateJobVO decorateData = (DecorateJobVO) data;
                                System.out.println(Constants.TRACK_ID + ": " + decorateData.getTrackId());
                                MDC.put(Constants.TRACK_ID, decorateData.getTrackId());
                                execute(decorateData.getJobVO());
                            } else {
                                execute(data);
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("async job error {}", e.toString());
                    } finally {
                        MDC.clear();
                    }
                }
            });
        }
    }


    @PostConstruct
    private void retryJob() {
        BlockingQueue<IJobVO> queue = AsyncJobDataDataServiceImpl.getRetryJobQueue();
        for (int i = 0; i < ASYNC_RETRY_JOB_THREAD_COUNT; i++) {
            EXECUTOR_SERVICE.submit(() -> {
                while (true) {
                    try {
                        IJobVO data = queue.poll(TIMEOUT, TimeUnit.SECONDS);
                        if (data != null) {
                            retryExecute(data);
                        }
                    } catch (Exception e) {
                        LOGGER.error("async retry job error {}", e.toString());
                    }

                }
            });
        }
    }


    private void execute(IJobVO data) {
        IAsyncJob iAsyncJob = getAsyncJobService(data.getJobService());
        if (iAsyncJob.getLock(data)) {
            iAsyncJob.execute(data);
        }
    }

    private void retryExecute(IJobVO data) {
        IAsyncJob iAsyncJob = getAsyncJobService(data.getJobService());
        if (iAsyncJob.getRetryLock(data)) {
            iAsyncJob.reTryExecute(data);
        }
    }

    private IAsyncJob getAsyncJobService(String name) {
        if (asyncJobMap.containsKey(name)) {
            return asyncJobMap.get(name);
        } else {
            throw new RuntimeException("不存在的job实现类");
        }
    }
}
