package com.example.asyncjobdemo.vo;

import com.example.asyncjobdemo.service.job.IJobVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Chupei Wang
 * @since 2021/11/11
 */
@Data
@Accessors(chain = true)
public class JobOneVO implements IJobVO {
    private String id;
    private String oneStr;

    @Override
    public String getJobService() {
        return "job_one";
    }
}
