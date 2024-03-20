package com.cby.tcs.job_qualified_percent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.job_qualified_percent.dao.JobQualifiedPercentDao;
import com.cby.tcs.job_qualified_percent.entity.po.JobQualifiedPercent;
import com.cby.tcs.job_qualified_percent.service.JobQualifiedPercentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作业合格率总览表 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class JobQualifiedPercentServiceImpl extends ServiceImpl<JobQualifiedPercentDao, JobQualifiedPercent> implements JobQualifiedPercentService {

  private final JobQualifiedPercentDao job_qualified_percentDao;
}
