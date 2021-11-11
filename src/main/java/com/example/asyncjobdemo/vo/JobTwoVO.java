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
public class JobTwoVO implements IJobVO {
    private String id;
    private String twoStr;

    @Override
    public String getJobService() {
        return "job_two";
    }
}
