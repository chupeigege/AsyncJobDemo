package com.example.asyncjobdemo.service.job.impl;

import com.example.asyncjobdemo.service.job.AsyncJobDataService;
import com.example.asyncjobdemo.service.job.IJobVO;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Chupei Wang
 * @since 2021/11/11
 */
@Service
public class AsyncJobDataDataServiceImpl implements AsyncJobDataService<IJobVO> {

    private final static BlockingQueue<IJobVO> ASYNC_JOB_QUEUE = new LinkedBlockingQueue<>();
    private final static BlockingQueue<IJobVO> ASYNC_RETRY_JOB_QUEUE = new LinkedBlockingQueue<>();

    public AsyncJobDataDataServiceImpl() {
    }

    @Override
    public void addData(IJobVO iJobVO) {
        if (iJobVO == null) {
            throw new NullPointerException();
        }
        ASYNC_JOB_QUEUE.add(iJobVO);
    }

    @Override
    public void addRetryData(IJobVO iJobVO) {
        if (iJobVO == null) {
            throw new NullPointerException();
        }
        ASYNC_RETRY_JOB_QUEUE.add(iJobVO);
    }

    public static BlockingQueue<IJobVO> getJobQueue() {
        return ASYNC_JOB_QUEUE;
    }

    public static BlockingQueue<IJobVO> getRetryJobQueue() {
        return ASYNC_RETRY_JOB_QUEUE;
    }

}
