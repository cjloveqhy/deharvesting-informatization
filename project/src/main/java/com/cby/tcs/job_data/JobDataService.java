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

    public Map<String, Object> getInfo(JobType type, Set<String> ids) {
        return this.supports.get(type).getInfo(ids);
    }
}
