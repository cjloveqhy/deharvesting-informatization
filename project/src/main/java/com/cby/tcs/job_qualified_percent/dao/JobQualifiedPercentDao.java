package com.cby.tcs.job_qualified_percent.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cby.tcs.job_qualified_percent.entity.po.JobQualifiedPercent;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 作业合格率总览表 Mapper 接口
 * </p>
 */
@Mapper
public interface JobQualifiedPercentDao extends BaseMapper<JobQualifiedPercent> {

}
