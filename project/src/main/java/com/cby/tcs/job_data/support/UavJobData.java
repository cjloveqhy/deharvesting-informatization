package com.cby.tcs.job_data.support;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cby.tcs.job_data.JobData;
import com.cby.tcs.job_evaluation.entity.enums.JobType;
import com.cby.tcs.job_qualified_percent.dao.JobQualifiedPercentDao;
import com.cby.tcs.job_qualified_percent.entity.po.JobQualifiedPercent;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.service.UserService;
import com.freedom.cloud.options.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UavJobData implements JobData {

    private final UserService userService;

    private final JobQualifiedPercentDao jobQualifiedPercentDao;

    @Override
    public boolean supports(JobType type) {
        return JobType.Uav.equals(type);
    }

    @Override
    public Map<String, Object> getInfo(Set<String> ids) {
        List<UserInfo> userInfoList = userService.getUserInfoList(ids.stream().toList());
        return new HashMap<>(userInfoList.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity())));
    }

    @Override
    public List<Option<Double>> passRateRanking() {
        LambdaQueryWrapper<JobQualifiedPercent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(JobQualifiedPercent::getJobType, JobType.Uav)
                .orderByDesc(JobQualifiedPercent::getPercentPass).last("limit 3");
        List<JobQualifiedPercent> jobQualifiedPercents = jobQualifiedPercentDao.selectList(wrapper);
        List<Option<Double>> options = new LinkedList<>();
        for (JobQualifiedPercent percent : jobQualifiedPercents) {
            options.add(new Option<Double>().setLabel("").setValue(percent.getPercentPass()));
        }
        return options;
    }

}
