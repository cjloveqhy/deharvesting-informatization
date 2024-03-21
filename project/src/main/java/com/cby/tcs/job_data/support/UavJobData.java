package com.cby.tcs.job_data.support;

import com.cby.tcs.job_data.JobData;
import com.cby.tcs.job_evaluation.entity.enums.JobType;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UavJobData implements JobData {

    private final UserService userService;

    @Override
    public boolean supports(JobType type) {
        return JobType.Uav.equals(type);
    }

    @Override
    public Map<String, Object> getInfo(Set<String> ids) {
        List<UserInfo> userInfoList = userService.getUserInfoList(ids.stream().toList());
        return new HashMap<>(userInfoList.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity())));
    }

}
