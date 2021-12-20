package com.example.asyncjobdemo.controller;

import com.example.asyncjobdemo.service.job.AsyncJobDataService;
import com.example.asyncjobdemo.vo.DecorateJobVO;
import com.example.asyncjobdemo.vo.JobOneVO;
import com.example.asyncjobdemo.vo.JobTwoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Chupei Wang
 * @since 2021/11/11
 */
@RestController
public class JobController {
    private final AsyncJobDataService asyncJobDataService;

    public JobController(AsyncJobDataService asyncJobDataService) {
        this.asyncJobDataService = asyncJobDataService;
    }

    @GetMapping(value = "/addJobOne")
    public Object addJobOne() {
        JobOneVO jobOneVO = new JobOneVO().setId(UUID.randomUUID().toString()).setOneStr("one");

        //trackId 可以直接配置一个拦截器自动生成  注:MDC底层是ThreadLocal，值只能在当前线程传递
        String trackId = UUID.randomUUID().toString().replaceAll("-", "");
        asyncJobDataService.addData(new DecorateJobVO(jobOneVO, trackId));
        return "jobOneVO";
    }

    @GetMapping(value = "/addJobTwo")
    public Object addJobTwo() {
        JobTwoVO jobTwoVO = new JobTwoVO().setId(UUID.randomUUID().toString()).setTwoStr("two");

        String trackId = UUID.randomUUID().toString().replaceAll("-", "");
        asyncJobDataService.addData(new DecorateJobVO(jobTwoVO, trackId));
        return "jobTwoVO";
    }

}
