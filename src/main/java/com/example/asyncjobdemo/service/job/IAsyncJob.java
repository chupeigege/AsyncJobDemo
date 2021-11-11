package com.example.asyncjobdemo.service.job;

/**
 * @author Chupei Wang
 * @since 2021/11/11
 */
public interface IAsyncJob<T extends IJobVO> {

    /**
     * 获取锁，确保线程安全
     *
     * @param t
     * @return
     */
    boolean getLock(T t);

    /**
     * 执行具体任务
     *
     * @param t
     */
    void execute(T t);

    /**
     * 获取重试锁，确保线程安全
     *
     * @param t
     * @return
     */
    boolean getRetryLock(T t);

    /**
     * 重试执行任务
     *
     * @param t
     */
    void reTryExecute(T t);
}
