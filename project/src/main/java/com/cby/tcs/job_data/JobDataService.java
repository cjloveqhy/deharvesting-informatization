package com.cby.tcs.job_data;

import com.cby.tcs.job_evaluation.entity.enums.JobType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class JobDataService {

    private Map<JobType, JobData> supports = new HashMap<>();

    JobDataService(List<JobData> supports) {
        for (JobData support : supports) {
            for (JobType type : JobType.values()) {
                if (support.supports(type)) {
                    this.supports.put(type, support);
                }
            }
        }
    }

    /**
     * 获取实例
     * @return {@link JobData} 实例对象
     */
    private JobData getInstance(JobType type) {
        return this.supports.get(type);
    }

    /**
     * 获取基础信息
     * @param type {@link JobType} 工作类型
     * @param ids 工作id编号
     * @return 基础信息体
     */
    public Map<String, Object> getInfo(JobType type, Set<String> ids) {
        return getInstance(type).getInfo(ids);
    }
}
