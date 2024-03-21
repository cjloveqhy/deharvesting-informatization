package com.cby.tcs.job_data.support;

import com.cby.tcs.job_data.JobData;
import com.cby.tcs.job_evaluation.entity.enums.JobType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CottonPickerJobData implements JobData {

    @Override
    public boolean supports(JobType type) {
        return JobType.Cotton_Picker.equals(type);
    }

    @Override
    public Map<String, Object> getInfo(Set<String> ids) {
        Map<String, Object> infoMap = new HashMap<>();
        ids.forEach(id -> infoMap.put(id, id));
        return infoMap;
    }

}
