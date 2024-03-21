package com.cby.tcs.job_qualified_percent.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.job_data.JobDataService;
import com.cby.tcs.job_qualified_percent.dao.JobQualifiedPercentDao;
import com.cby.tcs.job_qualified_percent.entity.fo.JobQualifiedPercentPageFo;
import com.cby.tcs.job_qualified_percent.entity.po.JobQualifiedPercent;
import com.cby.tcs.job_qualified_percent.entity.vo.JobQualifiedPercentPageVo;
import com.cby.tcs.job_qualified_percent.service.JobQualifiedPercentService;
import com.freedom.cloud.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 作业合格率总览表 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class JobQualifiedPercentServiceImpl extends ServiceImpl<JobQualifiedPercentDao, JobQualifiedPercent> implements JobQualifiedPercentService {

  private final JobQualifiedPercentDao jobQualifiedPercentDao;

  private final JobDataService jobDataService;

  @Override
  public Page<JobQualifiedPercentPageVo> getPageData(JobQualifiedPercentPageFo entity) {
    LambdaQueryWrapper<JobQualifiedPercent> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(JobQualifiedPercent::getJobType, entity.getJobType());
    Page<JobQualifiedPercent> jobQualifiedPercentPage = page(PageUtils.getPage(entity), wrapper);
    Page<JobQualifiedPercentPageVo> voPage = new Page<>();
    if (jobQualifiedPercentPage.getRecords().isEmpty()) return voPage;
    BeanUtil.copyProperties(jobQualifiedPercentPage, voPage, "records");
    Set<String> ids = jobQualifiedPercentPage.getRecords()
            .stream()
            .map(JobQualifiedPercent::getJobId)
            .collect(Collectors.toSet());
    Map<String, Object> infoMap = jobDataService.getInfo(entity.getJobType(), ids);
    voPage.setRecords(new ArrayList<>());
    for (JobQualifiedPercent jobQualifiedPercent : jobQualifiedPercentPage.getRecords()) {
      JobQualifiedPercentPageVo vo = BeanUtil.copyProperties(jobQualifiedPercent, JobQualifiedPercentPageVo.class);
      vo.setInfo(infoMap.get(jobQualifiedPercent.getJobId()));
      voPage.getRecords().add(vo);
    }
    return voPage;
  }
}
