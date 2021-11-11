package com.example.asyncjobdemo.service.job;

/**
 * @author Chupei Wang
 * @since 2021/11/11
 */
public interface IJobVO {
    /**
     * 获取执行具体任务的Service name
     * @return
     */
    String getJobService();
}
