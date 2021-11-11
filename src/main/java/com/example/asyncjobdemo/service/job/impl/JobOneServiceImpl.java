package com.example.asyncjobdemo.service.job.impl;

import com.example.asyncjobdemo.service.job.IAsyncJob;
import com.example.asyncjobdemo.vo.JobOneVO;
import org.springframework.stereotype.Service;


/**
 * @author Chupei Wang
 * @since 2021/11/11
 */
@Service("job_one")
public class JobOneServiceImpl implements IAsyncJob<JobOneVO> {

    @Override
    public boolean getLock(JobOneVO jobOneVO) {
        return true;
    }

    @Override
    public void execute(JobOneVO jobOneVO) {
        System.out.println("execute jobOneVO:" + jobOneVO);
    }

    @Override
    public boolean getRetryLock(JobOneVO jobOneVO) {
        return false;
    }

    @Override
    public void reTryExecute(JobOneVO jobOneVO) {

    }

}
