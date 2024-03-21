package com.cby.tcs.job_data;

import com.cby.tcs.job_evaluation.entity.enums.JobType;
import com.freedom.cloud.options.Option;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JobData {

    /**
     * 查找到支持的的实现类
     * @param type {@link JobType}
     * @return
     */
    boolean supports(JobType type);

    /**
     * 获取基础信息
     * @param ids 工作id编号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getInfo(Set<String> ids);

    /**
     * 获取合格率排行TOP3的数据
     * @return {@link List}<{@link Option}<{@link Float}>>
     */
    List<Option<Float>> passRateRanking();

}
