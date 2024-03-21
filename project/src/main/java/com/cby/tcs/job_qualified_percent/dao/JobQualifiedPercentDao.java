package com.cby.tcs.job_qualified_percent.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cby.tcs.job_qualified_percent.entity.po.JobQualifiedPercent;
import com.freedom.cloud.options.Option;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 作业合格率总览表 Mapper 接口
 * </p>
 */
@Mapper
public interface JobQualifiedPercentDao extends BaseMapper<JobQualifiedPercent> {

    /**
     * 获取无人机合格率排行TOP3的数据
     * @return {@link List}<{@link Option}<{@link Float}>>
     */
    List<Option<Float>> getUavPassRateRanking();
}
