package com.example.asyncjobdemo.service.job.impl;

import com.example.asyncjobdemo.constants.Constants;
import com.example.asyncjobdemo.service.job.IAsyncJob;
import com.example.asyncjobdemo.vo.JobTwoVO;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

/**
 * @author Chupei Wang
 * @since 2021/11/11
 */
@Service("job_two")
public class JobTwoServiceImpl implements IAsyncJob<JobTwoVO> {

    @Override
    public boolean getLock(JobTwoVO jobTwoVO) {
        return true;
    }

    @Override
    public void execute(JobTwoVO jobTwoVO) {
        System.out.println("JobTwoVO." + Constants.TRACK_ID + ": " + MDC.get(Constants.TRACK_ID));
        System.out.println("execute jobTwoVO:" + jobTwoVO);
    }

    @Override
    public boolean getRetryLock(JobTwoVO jobTwoVO) {
        return false;
    }

    @Override
    public void reTryExecute(JobTwoVO jobTwoVO) {

    }
}
