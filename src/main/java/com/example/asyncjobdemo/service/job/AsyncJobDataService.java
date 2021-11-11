package com.example.asyncjobdemo.service.job;

/**
 * @author Chupei Wang
 * @since 2021/11/11
 */
public interface AsyncJobDataService<T extends IJobVO> {
    /**
     * 添加数据到Job队列
     *
     * @param t
     */
    void addData(T t);

    /**
     * 添加数据到Job重试队列
     *
     * @param t
     */
    void addRetryData(T t);
}
