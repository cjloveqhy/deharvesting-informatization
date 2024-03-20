package com.cby.tcs.job_evaluation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cby.tcs.job_evaluation.entity.po.JobEvaluation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 作业评价表 Mapper 接口
 * </p>
 */
@Mapper
public interface JobEvaluationDao extends BaseMapper<JobEvaluation> {

}
