package com.example.asyncjobdemo.vo;

import com.example.asyncjobdemo.service.job.IJobVO;
import lombok.Getter;

/**
 * @author Chupei Wang
 * @since 2021/12/20
 */
@Getter
public class DecorateJobVO implements IJobVO {

    private final IJobVO jobVO;
    private final String trackId;

    public DecorateJobVO(IJobVO jobVO, String trackId) {
        this.jobVO = jobVO;
        this.trackId = trackId;
    }

    @Override
    public String getJobService() {
        return jobVO.getJobService();
    }
}
