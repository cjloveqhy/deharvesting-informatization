package com.cby.tcs.job_evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.job_evaluation.dao.JobEvaluationDao;
import com.cby.tcs.job_evaluation.entity.po.JobEvaluation;
import com.cby.tcs.job_evaluation.service.JobEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作业评价表 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class JobEvaluationServiceImpl extends ServiceImpl<JobEvaluationDao, JobEvaluation> implements JobEvaluationService {

  private final JobEvaluationDao job_evaluationDao;
}
